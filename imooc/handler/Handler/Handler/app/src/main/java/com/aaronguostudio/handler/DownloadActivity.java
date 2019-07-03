package com.aaronguostudio.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadActivity extends Activity {

    private static final int DOWNLOAD_MESSAGE_CODE = 10001;
    private static final int DOWNLOAD_FAILED_MESSAGE_CODE = 10002;
    private Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        /*
        * Main Thread
        * */

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(">> Clicked");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download("http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk");
                    }
                }).start();
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case  DOWNLOAD_MESSAGE_CODE:
                        progressBar.setProgress((Integer) msg.obj);
                        break;
                    case DOWNLOAD_FAILED_MESSAGE_CODE:
                        Log.e(">>>", "Failed");
                }
            }
        };
    }

    private void download (String appUrl) {
        try {
            System.out.println(">> Start Download");
            URL url = new URL(appUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            int contentLength = connection.getContentLength();
            String downloadFileName = Environment.getExternalStorageDirectory() + File.separator + "imooc" + File.separator;

            File file = new File(downloadFileName);

            if (!file.exists()) {
                file.mkdir();
            }

            String fileName = downloadFileName + "imooc.apk";

            File apkFile = new File(fileName);

            if (apkFile.exists()) {
                apkFile.delete();
            }

            int downloadSize = 0;
            byte[] bytes = new byte[1024];
            int length = 0;
            OutputStream outputStream = new FileOutputStream(fileName);

            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                downloadSize += length;
                Message message = Message.obtain();
                message.obj = downloadSize * 100 / contentLength;
                message.what = DOWNLOAD_MESSAGE_CODE;
                mHandler.sendMessage(message);
            }

            inputStream.close();
            outputStream.close();

        } catch (MalformedURLException e) {
            Message message = Message.obtain();
            message.what = DOWNLOAD_FAILED_MESSAGE_CODE;
            mHandler.sendMessage(message);
            e.printStackTrace();
        } catch (IOException e) {
            Message message = Message.obtain();
            message.what = DOWNLOAD_FAILED_MESSAGE_CODE;
            mHandler.sendMessage(message);
            e.printStackTrace();
        }

    }
}
