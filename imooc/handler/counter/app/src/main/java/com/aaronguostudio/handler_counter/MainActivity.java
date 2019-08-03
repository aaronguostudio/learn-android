package com.aaronguostudio.handler_counter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    public static final int COUNTDOWN_TIME_CODE = 10001;
    public static final int DELAY_MILLIS = 1000;
    public static final int MAX_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView count = (TextView) findViewById(R.id.count);

        CountdownTimeHandler handler = new CountdownTimeHandler(this) {

        };

        Message message = Message.obtain();

        message.what = COUNTDOWN_TIME_CODE;
        message.arg1 = MAX_COUNT;

        handler.sendMessageDelayed(message, DELAY_MILLIS);



    }

    public static class CountdownTimeHandler extends Handler {
        final WeakReference<MainActivity> mWeakReference;

        public CountdownTimeHandler(MainActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            MainActivity activity = mWeakReference.get();
            switch (msg.what) {
                case COUNTDOWN_TIME_CODE:
                    int value = msg.arg1;

                    break;
            }
        }
    }
}
