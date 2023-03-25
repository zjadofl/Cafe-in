package com.example.cafein;

import android.content.Intent;
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

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class PaymentComplete extends AppCompatActivity implements View.OnClickListener {

    TextView paytxt, menuNameDetail, menuUnitPriceDetail, menuQuantityDetail, menuPriceDetail,
            totalPrice, paymentPrice, orderDateTime, eCard, orderNo, eCoupon;

    Button checkBtn;

    String payment, menuNum, name, price, unitPrice, quantity, option, dateTime,obj,
            temperature, cup, size;

    SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
    Date time = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_complete);

        try {
            obj = new PayNumTask().execute().get();
            //obj = Integer.toString(Integer.parseInt(obj));
            //Integer obj2 = Integer.parseInt(obj);
            //obj = obj2.toString();
            Log.i("json", ""+obj);

        } catch (ExecutionException  | InterruptedException e) {
            e.printStackTrace();
        }




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("결제 내역");
        toolbar.setTitleTextColor(Color.WHITE);

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        // 넘어온 인텐트 타임 값 받기 뿌리기
        Intent payIntent = getIntent();
        payment = payIntent.getStringExtra("PAYMENT"); // MainActivity에서 "PAYMENT"이란 키로 넘낀 인탠트값 가져오기
        menuNum = payIntent.getStringExtra("menuNum");
        temperature = payIntent.getStringExtra("temperature");
        cup = payIntent.getStringExtra("cup");
        size = payIntent.getStringExtra("size");
        name = payIntent.getStringExtra("menuName");
        price = payIntent.getStringExtra("menuPrice");
        unitPrice = payIntent.getStringExtra("unitPrice");
        quantity = payIntent.getStringExtra("quantity");
        option = payIntent.getStringExtra("option");

        paytxt = (TextView) findViewById(R.id.payment);
        menuNameDetail = (TextView) findViewById(R.id.menuNameDetail);
        menuUnitPriceDetail = (TextView) findViewById(R.id.menuUnitPriceDetail);
        menuQuantityDetail = (TextView) findViewById(R.id.menuQuantityDetail);
        menuPriceDetail = (TextView) findViewById(R.id.menuPriceDetail);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        paymentPrice = (TextView) findViewById(R.id.paymentPrice);
        orderDateTime = (TextView) findViewById(R.id.orderDateTime);
        eCard = (TextView) findViewById(R.id.eCard);
        orderNo = (TextView) findViewById(R.id.orderNo);
        eCoupon = (TextView) findViewById(R.id.eCoupon);

        checkBtn = (Button) findViewById(R.id.checkBtn);

        paytxt.setText(payment);
        menuNameDetail.setText(name);
        menuUnitPriceDetail.setText(unitPrice);
        menuQuantityDetail.setText(quantity);
        if (payment.equals("e-카드") || payment.equals("신용카드")) {
            eCoupon.setText(quantity);
        } else {
            eCoupon.setText("0개");
        }
        menuPriceDetail.setText(price);
        totalPrice.setText(price);
        paymentPrice.setText(price);
        orderNo.setText("A-" + obj);

        //결제 완료 날짜
        dateTime = format1.format(time);

        DecimalFormat format = new DecimalFormat("#,##0"); //형식 바꿈.
        if (payment.equals("e-카드")) {
            //e-머니 잔고 계산하기
            int subPrice = Integer.parseInt(price.substring(0, price.length() - 1).replace(",", "")); //마지막 문자("원")와 컴마 제거.
            int eMoney = Integer.parseInt(PreferenceUtils.getEMoney(this));

            int calPrice = eMoney - subPrice;
            String leaveMoney = format.format(calPrice) + "원";

            PreferenceUtils.saveEMoney(calPrice+"", this);

            String userID = PreferenceUtils.getId(this);

            try {
                String returns = new EmoneyUpdateTask().execute(Integer.toString(calPrice), userID).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            eCard.setText(leaveMoney);
        }
        eCard.setText(format.format(Integer.parseInt(PreferenceUtils.getEMoney(this)))+"원");
        orderDateTime.setText(dateTime);
        checkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkBtn:
                Intent orderStatusIntent = new Intent(this, OrderStatus.class);
                orderStatusIntent.putExtra("menuNum", menuNum);
                orderStatusIntent.putExtra("PAYMENT", payment);
                orderStatusIntent.putExtra("temperature", temperature);
                orderStatusIntent.putExtra("cup", cup);
                orderStatusIntent.putExtra("size", size);
                orderStatusIntent.putExtra("menuName", name);
                orderStatusIntent.putExtra("menuPrice", price);
                orderStatusIntent.putExtra("unitPrice", unitPrice);
                orderStatusIntent.putExtra("quantity", quantity);
                orderStatusIntent.putExtra("option", option);
                orderStatusIntent.putExtra("date", dateTime);
                orderStatusIntent.putExtra("orderNum", "A-" + obj);
                startActivity(orderStatusIntent);
                PaymentComplete.this.finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //이머니를 업뎃. (차감)
    static class EmoneyUpdateTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/updateEMoney.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //convertImg, name, price, explain
                sendMsg = "eMoney="+strings[0]+"&usID="+strings[1];
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

    static class PayNumTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/paymentManageJSON.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
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