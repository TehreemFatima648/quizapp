package com.example.projject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText e1,e2;
    private Button b1,b2;
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.email);
        e2 = (EditText)findViewById(R.id.password);
        b1 = (Button)findViewById(R.id.loginbtn);
        b2 = (Button)findViewById(R.id.signupbtn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ee,match;
                String t1 = e1.getText().toString();
                String t2 = e2.getText().toString();
                if(t1.equals("")||t2.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please fill in the fields",Toast.LENGTH_SHORT ).show();
                }
                else{
                    ee = dbHelper.existEmail(t1);
                    if(ee) {
                          match = dbHelper.matchEmailPass(t1,t2);
                          if(match)
                          {
                              Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT ).show();
                              Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                              startActivity(intent);
                          }
                          else
                          {
                              Toast.makeText(getApplicationContext(),"Incorrect email and password. Please try again",Toast.LENGTH_SHORT ).show();
                          }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Email doesnot exist",Toast.LENGTH_SHORT ).show();
                    }
                    }
                }
            });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(MainActivity.this, Signup.class);
               startActivity(intent);

            }});

    }
}