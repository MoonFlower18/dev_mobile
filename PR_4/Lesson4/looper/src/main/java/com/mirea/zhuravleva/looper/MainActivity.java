package com.mirea.zhuravleva.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.zhuravleva.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding	= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper())	{
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Task execute. This is result: " + msg.getData().getString("result"));
                Log.d(MainActivity.class.getSimpleName(), "Task execute. This is result: " + msg.getData().getString("my_result"));
            }
        };
        MyLooper myLooper = new	MyLooper(mainThreadHandler);
        myLooper.start();

        binding.editTextTextMirea.setText("Мой номер по списку №13");

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message	msg = Message.obtain();
                Bundle bundle =	new	Bundle();
                String Age = binding.editTextText2.getText().toString();
                String Work = binding.editTextText3.getText().toString();

                bundle.putString("KEY", "mirea");
                bundle.putString("KEY2", Age);
                bundle.putString("KEY3", Work);
                msg.setData(bundle);
                myLooper.mHandler.sendMessage(msg);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}