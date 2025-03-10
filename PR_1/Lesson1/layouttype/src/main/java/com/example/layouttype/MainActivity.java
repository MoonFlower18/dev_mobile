package com.example.layouttype;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Код для заданий 6 главы
        TextView myTextView = (TextView) findViewById(R.id.textViewStudent);
        myTextView.setText("New text in MIREA");

        Button buttonStudent = findViewById(R.id.buttonStudent);
        buttonStudent.setText("MireaButton");

        CheckBox checkBoxStudent = findViewById(R.id.checkBoxStudent);
        checkBoxStudent.setChecked(true);

        // Навигация главной страницы
        Button butn1 = findViewById(R.id.navbut1);
        Button butn2 = findViewById(R.id.navbut2);
        Button butn3 = findViewById(R.id.navbut3);
        Button butn4 = findViewById(R.id.navbut4);
        Button butn5 = findViewById(R.id.navbut5);
        Button butn7 = findViewById(R.id.navbut7);

        butn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LinearActivity.class);
                startActivity(intent);
            }
        });

        butn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                startActivity(intent);
            }
        });

        butn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConstraintLayout.class);
                startActivity(intent);
            }
        });

        butn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ControlLesson1.class);
                startActivity(intent);
            }
        });

        butn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivitySecond.class);
                startActivity(intent);
            }
        });

        butn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.buttonclicker", "com.example.buttonclicker.MainActivity"));
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
