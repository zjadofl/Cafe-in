package com.example.cafein;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class NormalCharge extends AppCompatActivity implements View.OnClickListener{

    Button tenBtn, twentyBtn, thirtyBtn, fortyBtn, fiftyBtn, sixtyBtn, chargeBtn;
    TextView nowCharge, afterCharge;

    int eMoney, calEMoney;
    String userID;

    DecimalFormat formatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_charge);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("일반 충전");
        toolbar.setTitleTextColor(Color.WHITE);

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        tenBtn = (Button) findViewById(R.id.tenBtn);
        twentyBtn = (Button) findViewById(R.id.twentyBtn);
        thirtyBtn = (Button) findViewById(R.id.thirtyBtn);
        fortyBtn = (Button) findViewById(R.id.fortyBtn);
        fiftyBtn = (Button) findViewById(R.id.fiftyBtn);
        sixtyBtn = (Button) findViewById(R.id.sixtyBtn);
        chargeBtn = (Button) findViewById(R.id.chargeBtn);

        nowCharge = (TextView) findViewById(R.id.nowCharge);
        afterCharge = (TextView) findViewById(R.id.afterCharge);


        eMoney = Integer.parseInt(PreferenceUtils.getEMoney(this));
        userID = PreferenceUtils.getId(this);

        formatter = new DecimalFormat("#,###,###");

        nowCharge.setText(formatter.format(eMoney)+"원");

        calEMoney = eMoney;
        calEMoney = eMoney + 10000;
        tenBtn.setBackgroundResource(R.drawable.rounded_corner_photo2);
        afterCharge.setText(formatter.format(calEMoney)+"원");

        tenBtn.setOnClickListener(this);
        twentyBtn.setOnClickListener(this);
        thirtyBtn.setOnClickListener(this);
        fortyBtn.setOnClickListener(this);
        fiftyBtn.setOnClickListener(this);
        sixtyBtn.setOnClickListener(this);
        chargeBtn.setOnClickListener(this);

    } //onCreate 끝

    //백버튼 작동
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tenBtn:
                calEMoney = eMoney;
                calEMoney = eMoney + 10000;
                afterCharge.setText(formatter.format(calEMoney)+"원");

                tenBtn.setBackgroundResource(R.drawable.rounded_corner_photo2);
                twentyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                thirtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fortyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fiftyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                sixtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                break;

            case R.id.twentyBtn:
                calEMoney = eMoney;
                calEMoney = eMoney + 20000;
                afterCharge.setText(formatter.format(calEMoney)+"원");

                tenBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                twentyBtn.setBackgroundResource(R.drawable.rounded_corner_photo2);
                thirtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fortyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fiftyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                sixtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                break;
            case R.id.thirtyBtn:
                calEMoney = eMoney;
                calEMoney = eMoney + 30000;
                afterCharge.setText(formatter.format(calEMoney)+"원");

                tenBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                twentyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                thirtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo2);
                fortyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fiftyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                sixtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                break;

            case R.id.fortyBtn:
                calEMoney = eMoney;
                calEMoney = eMoney + 40000;
                afterCharge.setText(formatter.format(calEMoney)+"원");

                tenBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                twentyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                thirtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fortyBtn.setBackgroundResource(R.drawable.rounded_corner_photo2);
                fiftyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                sixtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                break;

            case R.id.fiftyBtn:
                calEMoney = eMoney;
                calEMoney = eMoney + 50000;
                afterCharge.setText(formatter.format(calEMoney)+"원");

                tenBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                twentyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                thirtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fortyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fiftyBtn.setBackgroundResource(R.drawable.rounded_corner_photo2);
                sixtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                break;

            case R.id.sixtyBtn:
                calEMoney = eMoney;
                calEMoney = eMoney + 60000;
                afterCharge.setText(formatter.format(calEMoney)+"원");


                tenBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                twentyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                thirtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fortyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                fiftyBtn.setBackgroundResource(R.drawable.rounded_corner_photo);
                sixtyBtn.setBackgroundResource(R.drawable.rounded_corner_photo2);

                break;

            case R.id.chargeBtn:
                try {
                    String returns = new ChargeTask().execute(calEMoney+"", userID).get();
                    if (returns.equals("ok")) {
                        Toast.makeText(this, "성공적으로 충전하였습니다.", Toast.LENGTH_LONG).show();
                        PreferenceUtils.saveEMoney(calEMoney+"", this);
                        NormalCharge.this.finish();
                    } else {
                        Log.i("돈넣는 작업에서", "에러발생");
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    static class ChargeTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                //URL url = new URL("http://14.33.171.115:8080/CafeinProject/updateEMoney.jsp");
                URL url = new URL("http://cafein.freehost.kr/updateEMoney.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "eMoney="+strings[0] + "&usID=" + strings[1];
                Log.i("sendMsg", strings[0]);
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
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
