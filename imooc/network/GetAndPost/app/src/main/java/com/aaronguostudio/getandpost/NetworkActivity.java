package com.aaronguostudio.getandpost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aaronguostudio.getandpost.model.Lesson;
import com.aaronguostudio.getandpost.model.LessonResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button mGetButton;
    private Button mPostButton;
    private Button mParseButton;
    private String mResult;
    private static final String TAG = "MetworkActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        findViews();
        setListeners();
    }

    private void findViews () {
        mTextView = findViewById(R.id.result_text);
        mGetButton = findViewById(R.id.get_button);
        mPostButton = findViewById(R.id.post_button);
        mParseButton = findViewById(R.id.parse_data);
    }

    private void setListeners () {
        mGetButton.setOnClickListener(this);
        mParseButton.setOnClickListener(this);
        mPostButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestDataByGet();
                    }
                }).start();
                break;
            case R.id.post_button:
                System.out.println("test");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestDataByPost();
                    }
                }).start();
                break;
            case R.id.parse_data:
                handleJsonResult(mResult);
                break;
        }
    }

    private void handleJsonResult (String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int status = jsonObject.getInt("status");
            LessonResult lessonResult = new LessonResult();
            lessonResult.setStatus(status);
            List<Lesson> lessonItems = new ArrayList<>();

            JSONArray lessons = jsonObject.getJSONArray("data");
            if (lessons != null && lessons.length() > 0) {
                for (int i = 0; i < lessons.length(); i++) {
                    JSONObject lesson = (JSONObject) lessons.get(i);
                    int id = lesson.getInt("id");
                    int learner = lesson.getInt("learner");
                    String name = lesson.getString("name");
                    String picSmall = lesson.getString("picSmall");
                    String picBig = lesson.getString("picBig");
                    String description = lesson.getString("description");

                    Lesson lessonItem = new Lesson(id, learner, name, picSmall, picBig, description);
                    lessonItems.add(lessonItem);
                }
                lessonResult.setLessons(lessonItems);
            }
            mTextView.setText(lessonResult.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    * Tips
    * 1. new Thread for async operation
    * 2. add internet privacy into manifest
    * 3. update UI in main thread, use handler
    * 4. using runOnUiThread or mTextView.post as a wrap of handler
    * */
    private void requestDataByGet () {
        try {
            URL url = new URL("https://www.imooc.com/api/teacher?type=2&page=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30*1000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.connect();

            int resCode = connection.getResponseCode();
            String resMes = connection.getResponseMessage();
            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                mResult = streamToString(inputStream);
                runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            mResult = decode(mResult);
                            mTextView.setText(mResult);
                        }
                    }
                );
                /*mTextView.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });*/
            } else {
                Log.e(TAG, "run: error code: " + resCode + ", message: " + resMes);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestDataByPost () {
        try {
            System.out.println(">>>>>> requestDataByPost");
            URL url = new URL("https://www.imooc.com/api/teacher");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30*1000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "UTF-8");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            String data = "username=" + getEncodeValue("imooc") + "&number=" + getEncodeValue("123456");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
            connection.connect();

            int resCode = connection.getResponseCode();
            String resMes = connection.getResponseMessage();
            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                mResult = streamToString(inputStream);
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                mResult = decode(mResult);
                                mTextView.setText(mResult);
                            }
                        }
                );
            } else {
                Log.e(TAG, "run: error code: " + resCode + ", message: " + resMes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @NotNull
    private String getEncodeValue (String data) {
        String encode = null;
        try {
            encode = URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

    private String streamToString (InputStream is) {
        try {
            ByteArrayOutputStream baos =  new ByteArrayOutputStream();
            byte[] buffer = new byte[2014];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * 将Unicode字符转换为UTF-8类型字符串
     */
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuilder retBuf = new StringBuilder();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else {
                    retBuf.append(unicodeStr.charAt(i));
                }
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

}
