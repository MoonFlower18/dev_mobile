package com.mirea.zhuravleva.Lesson4;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.zhuravleva.Lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding	= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navbut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.mirea.zhuravleva.thread", "com.mirea.zhuravleva.thread.MainActivity"));
                startActivity(intent);
            }
        });

        binding.navbut3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.mirea.zhuravleva.data_thread", "com.mirea.zhuravleva.data_thread.MainActivity"));
                startActivity(intent);
            }
        });

        binding.navbut4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.mirea.zhuravleva.looper", "com.mirea.zhuravleva.looper.MainActivity"));
                startActivity(intent);
            }
        });

        binding.navbut5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.mirea.zhuravleva.cryptoloader", "com.mirea.zhuravleva.cryptoloader.MainActivity"));
                startActivity(intent);
            }
        });

        binding.navbut6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.mirea.zhuravleva.serviceapp", "com.mirea.zhuravleva.serviceapp.MainActivity"));
                startActivity(intent);
            }
        });

        binding.navbut7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.mirea.zhuravleva.workmanager", "com.mirea.zhuravleva.workmanager.MainActivity"));
                startActivity(intent);
            }
        });

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void	onClick(View v) {
                Log.d(MainActivity.class.getSimpleName(),"onClickListener");
                binding.editTextTextMirea.setText("Мой номер по списку №13");
            }
        });
    };
}