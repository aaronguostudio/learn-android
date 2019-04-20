package com.aaronguostudio.handler;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        * UI Thread
        * */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tx = (TextView) findViewById(R.id.textView2);
        final Button btn = (Button) findViewById(R.id.button2);

        /*
        * Create handler
        * */
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                /*
                * Main thread receives msg from the child
                * */
                Log.d(TAG, "handleMessage: " + msg.what);

                if (msg.what == 1001) {
                    tx.setText("1001");
                }

                if (msg.what == 1002) {
                    tx.setText("1002");
                }
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {

            /*
            * Operate heavy computing jobs
            * */
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1001);
                    }
                }).start();
            }
        });


//        handler.sendEmptyMessage(1001);
    }
}
