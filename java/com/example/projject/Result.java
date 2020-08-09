package com.example.projject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    private TextView e1,e2,e3;
    private Button b;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        e1 = (TextView) findViewById(R.id.congo);
        e2 = (TextView) findViewById(R.id.sc);
        e2 = (TextView) findViewById(R.id.fscore);
        b = (Button)findViewById(R.id.button);
        score = getIntent().getIntExtra("extraScore",0);
        e2.setText(score + " / 10");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, MainActivity2.class);
                intent.putExtra("extraScore",score);
                startActivity(intent);
            }
        });
    }
}