package com.mirea.zhuravleva.looper;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import android.os.Handler;

import java.util.concurrent.TimeUnit;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;
    public MyLooper(Handler	mainThreadHandler) {
        mainHandler = mainThreadHandler;
    }
    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            public void	handleMessage(Message msg) {
                String data = msg.getData().getString("KEY");
                Log.d("MyLooper get message: ", data);
                String data_age = msg.getData().getString("KEY2");
                Log.d("MyLooper get age: ", data_age);
                String data_work = msg.getData().getString("KEY3");
                Log.d("MyLooper get work: ", data_work);

                int wait_time = 0;
                try {
                    wait_time = Integer.parseInt(data_age);
                } catch (NumberFormatException e) {
                    Log.e("MyLooper", "Invalid number format for wait_time: " + data_age);
                }
                try {
                    if (wait_time > 0) {
                        TimeUnit.SECONDS.sleep(wait_time);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int	count = data.length();
                Message	message = new Message();
                Bundle bundle = new	Bundle();
                bundle.putString("result", String.format("The number of letters in the word %s is %d", data, count));
                bundle.putString("my_result", String.format("My age is %s and my work is %s", data_age, data_work));
                message.setData(bundle);
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}
