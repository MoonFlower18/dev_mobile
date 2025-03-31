package com.example.zhuravleva.lesson2;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        // Навигация главной страницы
        Button butn1 = findViewById(R.id.navbut1);
        Button butn2 = findViewById(R.id.navbut2);
        Button butn3 = findViewById(R.id.navbut3);
        Button butn4 = findViewById(R.id.navbut4);
        Button butn5 = findViewById(R.id.navbut5);
        Button butn6 = findViewById(R.id.navbut6);

        butn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.zhuravleva.activitylifecycle", "com.example.zhuravleva.activitylifecycle.MainActivity"));
                startActivity(intent);
            }
        });

        butn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.zhuravleva.multiactivity", "com.example.zhuravleva.multiactivity.MainActivity"));
                startActivity(intent);
            }
        });

        butn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.zhuravleva.intentfilter", "com.example.zhuravleva.intentfilter.MainActivity"));
                startActivity(intent);
            }
        });

        butn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.zhuravleva.toastapp", "com.example.zhuravleva.toastapp.MainActivity"));
                startActivity(intent);
            }
        });

        butn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.zhuravleva.notificationapp", "com.example.zhuravleva.notificationapp.MainActivity"));
                startActivity(intent);
            }
        });

        butn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.zhuravleva.dialog", "com.example.zhuravleva.dialog.MainActivity"));
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