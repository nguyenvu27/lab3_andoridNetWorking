package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView Sport,Maths ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sport = findViewById(R.id.sport) ;
        Maths = findViewById(R.id.maths) ;

        Sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.TYPE = 1;
                startActivity(new Intent(MainActivity.this,QuizActivity.class));
                finish();
            }
        });

        Maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.TYPE = 2;
                startActivity(new Intent(MainActivity.this,QuizActivity.class));
                finish();
            }
        });

    }
}
