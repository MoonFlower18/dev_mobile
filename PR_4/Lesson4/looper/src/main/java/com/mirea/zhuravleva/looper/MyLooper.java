package com.mirea.zhuravleva.looper;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import android.os.Handler;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;
    public MyLooper(Handler	mainThreadHandler) {
        mainHandler	= mainThreadHandler;
    }
    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            public void	handleMessage(Message msg) {
                String data = msg.getData().getString("KEY");
                Log.d("MyLooper get message: ", data);

                String data2 = msg.getData().getString("KEY2");
                Log.d("MyLooper2 get message: ", data2);

                String data3 = msg.getData().getString("KEY3");
                Log.d("MyLooper3 get message: ", data3);

                int	count = data.length();
                Message	message = new Message();
                Bundle bundle = new	Bundle();
                bundle.putString("result", String.format("The number of letters in the word %s is %d", data, count));
                bundle.putString("result2", String.format("The %s and %s", data2, data3));
                message.setData(bundle);
// Send the message back to main thread message queue use main thread message Handler.
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}
