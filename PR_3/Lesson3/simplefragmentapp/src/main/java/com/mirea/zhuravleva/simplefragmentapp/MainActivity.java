package com.mirea.zhuravleva.simplefragmentapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.zhuravleva.simplefragmentapp.FirstFragment;
import com.mirea.zhuravleva.simplefragmentapp.R;
import com.mirea.zhuravleva.simplefragmentapp.SecondFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openFirstFragment(View view) {
        FirstFragment firstFragment = new FirstFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, firstFragment)
                .commit();
    }
    public void openSecondFragment(View view) {
        SecondFragment secondFragment = new SecondFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, secondFragment)
                .commit();
    }
}