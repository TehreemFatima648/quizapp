package com.example.projject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
private EditText e1,e2,e3;
private Button b;
DatabaseHelper dbHelper = new DatabaseHelper(this);
    //DatabaseHelper dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //dh = new DatabaseHelper(this);

        e1 = (EditText)findViewById(R.id.em);
        e2 = (EditText)findViewById(R.id.pass);
        e3 = (EditText)findViewById(R.id.cpass);
        b = (Button)findViewById(R.id.Register);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;

                boolean ve,bt;
                String eml = e1.getText().toString();
                String pw = e2.getText().toString();
                String cpw = e3.getText().toString();
                com.example.projject.User user = new com.example.projject.User();
                user.setEmail(eml);
                user.setPassword(pw);
               /* if (dh.fillUsersTable(user))
                {
                    Intent intent = new Intent(Signup.this,MainActivity2.class);
                    startActivity(intent);
                }*/
                if(eml.equals("")||pw.equals("")||cpw.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please fill in the fields",Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    if(pw.equals(cpw)) {
                        if ((Patterns.EMAIL_ADDRESS.matcher(eml).matches()))
                        {
                            ve = dbHelper.validateEmail(eml);
                        if (ve == true) {
                            bt = dbHelper.fillUsersTable(user);
                            if (bt == true) {
                                Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Signup.this, MainActivity2.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "User Registered Unsuccessfully", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                        else{
                            Toast.makeText(getApplicationContext(), "Incorrect Email Format", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Passwords donot match",Toast.LENGTH_SHORT ).show();
                    }
                }
            }});
    }
}