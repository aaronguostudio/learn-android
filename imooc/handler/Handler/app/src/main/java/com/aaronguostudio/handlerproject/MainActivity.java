package com.aaronguostudio.handlerproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * 主线程
         * */
        final TextView textView = (TextView) findViewById(R.id.textView);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Log.d("MainActivity", "handleMessage: " + msg.what);

                switch (msg.what) {
                    case 1001:
                        textView.setText("test");
                    default:
                        break;
                }
            }
        };


        // 点击之后更新文字，在主线程
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {


            /*
            * 子线程
            * */
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);

                            Message message = Message.obtain();
                            message.what = 1001;
                            message.arg1 = 1;
                            message.arg2 = 2;
                            message.obj = MainActivity.this;

                            handler.sendMessage(message);

                            // 定时任务
                            /*handler.sendMessageDelayed(message, 3000);
                                handler.sendMessageAtTime(message, SystemClock.uptimeMillis() + 3000);
                                handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int a = 1 + 2
                                }
                            });*/
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

    }
}
