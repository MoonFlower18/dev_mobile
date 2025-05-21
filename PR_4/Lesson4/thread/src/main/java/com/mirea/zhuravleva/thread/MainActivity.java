package com.mirea.zhuravleva.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.zhuravleva.thread.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding	= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView infoTextView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());

        mainThread.setName("Мой номер группы: 01, номер по списку: 13, мой любимый фильм: «Т-34»");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());

        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));
        Log.d(MainActivity.class.getSimpleName(), "Group: " + mainThread.getThreadGroup());

        final int[] counter = {0};

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)	{
                new	Thread(new Runnable() {
                    public void run() {
                        int	numberThread = counter[0] +1;
                        counter[0] = numberThread;
                        Log.d("ThreadProject",	String.format("Запущен поток № %d студентом группы № %s номер по " +
                                "списку № %d ",	numberThread, "БИСО-01-20", 13));
                        long endTime = System.currentTimeMillis() + 20 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try	{
                                    wait(endTime - System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                }
                                catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            Log.d("ThreadProject",	"Выполнен поток № " + numberThread);
                        }
                    }
                }).start();
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        String lessons = binding.editTextText.getText().toString();
                        String hours = binding.editTextText2.getText().toString();

                        if (!lessons.isEmpty() && !hours.isEmpty()) {
                            int num_les = Integer.parseInt(lessons);
                            int num_hrs = Integer.parseInt(hours);

                            if (num_hrs != 0) {
                                int result = num_les / num_hrs;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.textViewResult.setText(" " + result);
                                    }
                                });
                            }
                        }
                    }
                }).start();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}