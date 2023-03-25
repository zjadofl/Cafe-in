package com.example.cafeinadmin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class OrderExpand extends AppCompatActivity implements View.OnClickListener {

    String orderno;
    String ordercontent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_expand);
        ImageButton smallBtn=(ImageButton)findViewById(R.id.imageButton3);
        smallBtn.setOnClickListener(this);
        Button completeBtn=(Button)findViewById(R.id.completeBtn);
        completeBtn.setOnClickListener(this);
        TextView ocontent=(TextView)findViewById(R.id.odContent);
        TextView orderN=(TextView)findViewById(R.id.orderTitle);
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        orderno=intent.getStringExtra("orderno");
        ordercontent=intent.getStringExtra("ordercontent");
        orderN.setText(orderno);
        ocontent.setText(ordercontent);

    }

    public void complete() {
        // TODO Auto-generated method stub
        android.app.AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("제조를 완료하시겠습니까?\n");
        builder.setMessage("완료버튼 누르기 전 꼼꼼히 확인하십시오.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    Log.i("complete num", orderno);
                    String obj = new OrderAppCompleteTask().execute(orderno).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"주문이 완료처리되었습니다.",Toast.LENGTH_SHORT).show();

            }
        }).setNegativeButton("취소", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton3:
                this.finish();
                /*Intent intent = new Intent(this, OrderManage.class);
                startActivity(intent);*/
                //((MainActivity) this).replaceFragment(OrderManage.newInstance());
                break;
            case R.id.completeBtn:
                complete();
                break;
        }

    }


    @SuppressLint("StaticFieldLeak")
    class OrderAppCompleteTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/orderStatusComplete.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "num=" + strings[0];
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }



}