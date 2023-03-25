package com.example.cafein;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class OrderStatus2 extends AppCompatActivity implements View.OnClickListener {

    TextView menuName, optionDetail, paymentDetail, quantityDetail, priceDetail,
            totalPrice, payTotalPrice, totalPrice2, paymentStepTxt2, paymentStepDate,
            orderRequestTxt,
            orderRequestDate, orderApprovalDate, completeDate, orderNo;

    Button finishBtn;

    String payment, name, price, unitPrice, quantity, option, dateTime, requestTime,
            approvalTime, completeTime, usID, obj, orderNum, changeStatus, num, pay, date;

    View orderRequestStep1, orderRequestStep2, orderApprovalStep1, orderApprovalStep2, completeStep1, completeStep2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("주문 현황");
        toolbar.setTitleTextColor(Color.WHITE);


        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        menuName = (TextView) findViewById(R.id.menuName);
        optionDetail = (TextView) findViewById(R.id.optionDetail);
        paymentDetail = (TextView) findViewById(R.id.paymentDetail);
        quantityDetail = (TextView) findViewById(R.id.quantityDetail);
        priceDetail = (TextView) findViewById(R.id.priceDetail);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        payTotalPrice = (TextView) findViewById(R.id.payTotalPrice);
        totalPrice2 = (TextView) findViewById(R.id.totalPrice2);
        paymentStepTxt2 = (TextView) findViewById(R.id.paymentStepTxt2);
        orderRequestTxt = (TextView) findViewById(R.id.orderRequestTxt);
        paymentStepDate = (TextView) findViewById(R.id.paymentStepDate);
        orderRequestDate = (TextView) findViewById(R.id.orderRequestDate);
        orderApprovalDate = (TextView) findViewById(R.id.orderApprovalDate);
        completeDate = (TextView) findViewById(R.id.completeDate);
        orderNo = (TextView) findViewById(R.id.orderNo);


        finishBtn = (Button) findViewById(R.id.finishBtn);

        orderRequestStep1 = (View) findViewById(R.id.orderRequestStep1);
        orderRequestStep2 = (View) findViewById(R.id.orderRequestStep2);
        orderApprovalStep1 = (View) findViewById(R.id.orderApprovalStep1);
        orderApprovalStep2 = (View) findViewById(R.id.orderApprovalStep2);
        completeStep1 = (View) findViewById(R.id.completeStep1);
        completeStep2 = (View) findViewById(R.id.completeStep2);

        num = PreferenceUtils.getNum(this);




        /*
        //이전 화면에 있는 값들 받아오기.
        Intent orderStatusIntent = getIntent();
        payment = orderStatusIntent.getStringExtra("PAYMENT");
        name = orderStatusIntent.getStringExtra("menuName");
        price = orderStatusIntent.getStringExtra("menuPrice");
        unitPrice = orderStatusIntent.getStringExtra("unitPrice");
        quantity = orderStatusIntent.getStringExtra("quantity");
        option = orderStatusIntent.getStringExtra("option");
        dateTime = orderStatusIntent.getStringExtra("date");
        orderNum = orderStatusIntent.getStringExtra("orderNum");

        menuName.setText(name);
        optionDetail.setText(option);
        paymentDetail.setText(payment);
        quantityDetail.setText(quantity);
        priceDetail.setText(price);
        totalPrice.setText(price);
        payTotalPrice.setText(price);
        totalPrice2.setText(price);
        paymentStepTxt2.setText(payment);
        paymentStepDate.setText(dateTime);
        orderRequestDate.setText(requestTime);
        orderApprovalDate.setText(approvalTime);
        completeDate.setText(completeTime);
        orderNo.setText(orderNum);

         */



        usID = PreferenceUtils.getId(this);


        //추후에 다시 수정해야함.
        orderRequestTxt.setTextColor(getColor(R.color.black));
        orderRequestDate.setTextColor(getColor(R.color.black));

        orderRequestStep1.setBackgroundColor(getColor(R.color.colorPrimary));
        orderRequestStep2.setBackgroundResource(R.drawable.neon_main_btn);

        //orderApprovalStep1.setBackgroundColor(getColor(R.color.colorPrimary));
        //orderApprovalStep2.setBackgroundResource(R.drawable.neon_main_btn);

        try {
            String obj = new OrderStatus2.OrderRLJsonTask().execute(num).get();
            Log.i("json", ""+obj);
            parseJson(obj);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }








        finishBtn.setOnClickListener(this);


    } //onCreate 끝

    //툴바에 menu item 추가.
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);

        return true;
    }


    //백버튼 작동
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
            case R.id.refresh:
                /*finish();
                startActivity(getIntent());*/
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }





    private void parseJson(String json) throws JSONException {
        String TAG_JSON = "status";
        String TAG_NAME = "name";
        String TAG_MNUM = "menuNum";
        String TAG_NUM = "orderNum";
        String TAG_PRICE = "price";
        String TAG_STATUS = "status";
        String TAG_DATE = "date";
        String TAG_PAY = "pay";
        String TAG_REASON = "reason";
        String TAG_AMNT = "amnt";
        String TAG_TEM = "tem";
        String TAG_SIZE = "size";
        String TAG_CUP = "cup";
        String name= null;
        String menuNum = null;
        String orderNum = null;
        String price = null;
        String status = null;
        String date = null;
        String pay = null;
        String reason = null;
        String amnt = null;
        String tem = null;
        String size = null;
        String cup = null;

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            name = item.getString(TAG_NAME);
            menuNum = item.getString(TAG_MNUM);
            orderNum = item.getString(TAG_NUM);
            price = item.getString(TAG_PRICE);
            status = item.getString(TAG_STATUS);
            date = item.getString(TAG_DATE);
            pay = item.getString(TAG_PAY);
            reason = item.getString(TAG_REASON);
            amnt = item.getString(TAG_AMNT);
            tem = item.getString(TAG_TEM);
            size = item.getString(TAG_SIZE);
            cup = item.getString(TAG_CUP);
        }
        if(status.equals("승인")){
            orderApprovalStep1.setBackgroundColor(getColor(R.color.colorPrimary));
            orderApprovalStep2.setBackgroundResource(R.drawable.neon_main_btn);
        } else if(status.equals("완료")) {
            orderApprovalStep1.setBackgroundColor(getColor(R.color.colorPrimary));
            orderApprovalStep2.setBackgroundResource(R.drawable.neon_main_btn);
            completeStep1.setBackgroundColor(getColor(R.color.colorPrimary));
            completeStep2.setBackgroundResource(R.drawable.neon_main_btn);
        } else if(status.equals("거부")) {
            Toast.makeText(getApplicationContext(),"주문이 거부되었습니다.\n사유: "+reason, Toast.LENGTH_LONG).show();
        }
        orderNo.setText("A - "+orderNum);
        menuName.setText(name);
        priceDetail.setText(price+"원");
        totalPrice.setText(price+"원");
        payTotalPrice.setText(price+"원");
        totalPrice2.setText(price+"원");
        paymentStepDate.setText(date);
        orderRequestDate.setText(date);
        paymentStepTxt2.setText(pay);
        quantityDetail.setText(amnt);

        if (tem.equals("I")) {
            tem = "ICE";
        } else if (tem.equals("H")) {
            tem = "HOT";
        }

        if (cup.equals("매")) {
            cup = "매장용컵";
        } else if (cup.equals("일")) {
            cup = "일회용컵";
        }

        optionDetail.setText(tem +"/" + cup + "/" + amnt+ "개/" +size);



    }



    static class OrderRLJsonTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/statusManage2JSON.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "num=" + strings[0] ;
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finishBtn:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
        }
    }







} //class 끝