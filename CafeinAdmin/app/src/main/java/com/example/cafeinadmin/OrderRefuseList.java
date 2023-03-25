package com.example.cafeinadmin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class OrderRefuseList extends AppCompatActivity {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    boolean isDrawerOpend;
    TableLayout refuseTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_refuse_list);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        setTitle("주문 거절 목록");

        refuseTable = (TableLayout) findViewById(R.id.refuseTable);

        try {
            String obj = new OrderRLJsonTask().execute().get();
            Log.i("json", ""+obj);
            try {
                parseOrderJson(obj);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    //툴바에 menu item 추가.
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.refresh:
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void parseOrderJson(String json) throws JSONException, ParseException {
        String TAG_JSON = "refuse";
        String TAG_NUM = "num";
        String TAG_MNAME = "menuName";
        String TAG_STATUS = "status";
        String TAG_REASON = "reason";
        String TAG_date = "date";


        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String num = item.getString(TAG_NUM);
            String menuName = item.getString(TAG_MNAME);
            String status = item.getString(TAG_STATUS);
            String reason = item.getString(TAG_REASON);
            String date = item.getString(TAG_date);


            date = date.substring(0, date.indexOf('.'));

            Date formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            date = new SimpleDateFormat("HH:mm:ss").format(formatDate);

            //동적 생성.
            //final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()); //가로 넓이 설정.
            TableRow addTr = new TableRow(this); //row 추가
            TextView td1 = new EditText(this); //text 추가
            TextView td2 = new EditText(this);
            TextView td3 = new EditText(this);

            td1.setText(num);
            td2.setText(menuName);
            td3.setText(reason);

            //글자가 넘쳤을 때 ...로 생략.
            td1.setMaxLines(1);
            td1.setEllipsize(TextUtils.TruncateAt.END);
            td2.setMaxLines(1);
            td2.setEllipsize(TextUtils.TruncateAt.END);
            td3.setMaxLines(1);
            td3.setEllipsize(TextUtils.TruncateAt.END);


            td1.setBackgroundResource(R.drawable.table); //text에 배경 삽입.
            td2.setBackgroundResource(R.drawable.table);
            td3.setBackgroundResource(R.drawable.table);

            td1.setGravity(Gravity.CENTER); //gravity를 center로 설정.
            td2.setGravity(Gravity.CENTER);
            td3.setGravity(Gravity.CENTER);

            /*TableRow.LayoutParams params1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f);
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4f);
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 4.5f);*/

            //테이블 row에 width, height, 가중치 정하기.
            TableRow.LayoutParams params1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
            params1.weight = 1.5f;
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
            params2.weight = 4.0f;
            TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
            params3.weight = 4.5f;

            td1.setLayoutParams(params1);
            td2.setLayoutParams(params2);
            td3.setLayoutParams(params2);


            addTr.addView(td1); //tableRow에 Text 넣기.
            addTr.addView(td2);
            addTr.addView(td3);

            refuseTable.addView(addTr); //테이블에 넣기.

        } //for 끝

    }

    class OrderRLJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/refuseManageJSON.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
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