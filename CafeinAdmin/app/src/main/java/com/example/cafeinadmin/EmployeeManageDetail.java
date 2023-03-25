package com.example.cafeinadmin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class EmployeeManageDetail extends AppCompatActivity implements View.OnClickListener {

    TextView nameText, phoneText, addressText, dateText, part1Text, part2Text, partDivider, moneyText;
    TimePickerDialog dialog1, dialog2;
    Button regBtn;
    TableLayout attendTL;

    //데이트 피커
    Calendar time = Calendar.getInstance();
    Calendar minDate = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();

    String detailName, num, name, phone, date, address, part1, part2, employeeInfo, workTime, attendanceInfo;

    Long gyeb;
    Date gyes,gyee;
    int nujuk = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage_detail);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("직원 상세");
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        nameText = (TextView) findViewById(R.id.nameText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        addressText = (TextView) findViewById(R.id.addressText);
        dateText = (TextView) findViewById(R.id.dateText);
        part1Text = (TextView) findViewById(R.id.part1Text);
        part2Text = (TextView) findViewById(R.id.part2Text);
        partDivider = (TextView) findViewById(R.id.partDivider);
         moneyText=(TextView)findViewById(R.id.money);


        regBtn = (Button) findViewById(R.id.regBtn);

        attendTL = (TableLayout) findViewById(R.id.attendTL);

        Intent detailIntent = getIntent();
        num = detailIntent.getStringExtra("num");
        workTime = detailIntent.getStringExtra("time");
        detailName = detailIntent.getStringExtra("name");

        nameText.setText(detailName);

        try {
            //해당 직원의 정보를 읽어옴.
            employeeInfo = new GetEmployeeInfoTask().execute(num.toString()).get();

            //근태정보를 읽어옴.
            attendanceInfo = new AttendJsonTask().execute(num.toString()).get();
            parseAttendJson(attendanceInfo);
            if (!(employeeInfo.equals(null)) || !(attendanceInfo.equals(null))) {
                Log.i("json 받음", employeeInfo);
                setSetting(employeeInfo);
            }
        } catch (ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    } //onCreate 끝

    //툴바에 menu item 추가.
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.only_edit, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.edit:
                Intent editIntent = new Intent(this, EmployeeManageDetailEdit.class);
                editIntent.putExtra("num", num);
                editIntent.putExtra("name", detailName);
                editIntent.putExtra("time", workTime);
                startActivity(editIntent);
                EmployeeManageDetail.this.finish();
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    } //onClick 끝


    //json 파일을 서버로 보내는 메소드
    private String sendEmployeeInfo() {
        JSONObject jsonObject = new JSONObject();
        JSONArray employeeArray = new JSONArray();
        JSONObject infoObject = new JSONObject();
        try {
            //전에 있던 데이터들을 합치기 위한 작업.
            if (!(employeeInfo == null) || !(employeeInfo.equals("")) || !(employeeInfo.equals("{}"))) {
                JSONObject addJson = new JSONObject(employeeInfo);
                String infoStr = addJson.getString("카페드림");
                Log.i("카페드림 제거", infoStr);
                JSONArray infoArr = new JSONArray(infoStr);
                for (int i = 0; i < infoArr.length(); i++) {
                    JSONObject infoObj = infoArr.getJSONObject(i);
                    employeeArray .put(infoObj);
                }
            }
            //동명이인이 있는 경우는 사번으로 해결. (아직 안함)
            //에딧 텍스트에서 받은 값들을 json에 담아 보내는 작업.

            infoObject.put("name", name);
            infoObject.put("phone", phone);
            infoObject.put("address", address);
            infoObject.put("date", date);
            infoObject.put("part1", part1);
            infoObject.put("part2", part2);
            employeeArray.put(infoObject);
            jsonObject.put("카페드림", employeeArray);

            Log.i("Test:",infoObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


    private void setSetting(String json) throws JSONException {
        String TAG_JSON = "employee";
        String TAG_NUM = "num";
        String TAG_NAME = "name";
        String TAG_POS = "pos";
        String TAG_PHONE = "phone";
        String TAG_ADDRESS = "address";
        String TAG_DATE = "date";
        String TAG_PART1 = "part1";
        String TAG_PART2 = "part2";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);


        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            /*String jNum = item.getString(TAG_NUM);
            String jName = item.getString(TAG_NAME);
            String jPos = item.getString(TAG_POS);*/
            String jPhone = item.getString(TAG_PHONE);
            String jAddress = item.getString(TAG_ADDRESS);
            String jDate = item.getString(TAG_DATE);
            String jPart1 = item.getString(TAG_PART1);
            String jPart2 = item.getString(TAG_PART2);

            phoneText.setText(jPhone);
            addressText.setText(jAddress);
            dateText.setText(jDate);
            part1Text.setText(jPart1);
            part2Text.setText(jPart2);

        }
    } //setSetting 끝


    //근태표 만들기.
    private void parseAttendJson(String json) throws JSONException, ParseException {
        String TAG_JSON = "attendance";
        String TAG_DATE = "date";
        String TAG_STATUS = "status";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String date = item.getString(TAG_DATE);
            String status = item.getString(TAG_STATUS);

            //테이블 row에 width, height, 가중치 정하기.
            TableRow.LayoutParams params1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
            params1.weight = 0.3f;
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
            params2.weight = 0.3f;
            TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
            params3.weight = 0.3f;

            //생성.
            TableRow addTR = new TableRow(this); //row 추가
            TextView td1 = new TextView(this);
            TextView td2 = new TextView(this);
            TextView td3 = new TextView(this);

            //param 설정.
            td1.setLayoutParams(params1);
            td2.setLayoutParams(params2);
            td3.setLayoutParams(params2);

            //글자 삽입.
            //td1.setText(num);
            td2.setText(date);
            td3.setText(status);

            //글자가 넘쳤을 때 ...로 생략.
            td1.setMaxLines(1);
            td1.setEllipsize(TextUtils.TruncateAt.END);
            td2.setMaxLines(1);
            td2.setEllipsize(TextUtils.TruncateAt.END);
            td3.setMaxLines(1);
            td3.setEllipsize(TextUtils.TruncateAt.END);

            //글자 크기.
            td1.setTextSize(15);
            td2.setTextSize(15);
            td3.setTextSize(15);

            //정렬 : 가운데.
            td1.setGravity(Gravity.CENTER);
            td2.setGravity(Gravity.CENTER);
            td3.setGravity(Gravity.CENTER);

            //배경을 테이블로 설정.
            td1.setBackgroundResource(R.drawable.table);
            td2.setBackgroundResource(R.drawable.table);
            td3.setBackgroundResource(R.drawable.table);

            addTR.addView(td1); //tableRow에 Text 넣기.
            addTR.addView(td2);
            addTR.addView(td3);

            attendTL.addView(addTR);

            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date gyed = transFormat.parse(date);


            if(i%2==0){
                gyes= gyed;
            }else if(i%2==1){
                gyee=gyed;
                gyeb=gyee.getTime()-gyes.getTime();
                long gyebs = gyeb / (60*60*1000);
                nujuk=nujuk+Integer.parseInt(String.valueOf(gyebs).substring(0,1));
                Log.i("제발졸업", String.valueOf(nujuk));
                int total = nujuk*8350;
                moneyText.setText("현재 기준 이번달 예상 급여는 "+total+"원 입니다.");


            }


        } //for 끝


    }


    static class UpdateEmployeeInfoTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/updateEmployeeInfo.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //phone, address, date, part1, part2
                sendMsg = "phone="+strings[0]+"&address="+strings[1]+"&date="+strings[2]
                        +"&part1="+strings[3]+"&part2="+strings[4]+"&num="+strings[5];
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

    static class GetEmployeeInfoTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/employeeDetail.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //convertImg, name, price, explain
                sendMsg = "epNum="+strings[0];
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

    static class AttendJsonTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/attendanceInfo.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //convertImg, name, price, explain
                sendMsg = "epNum="+strings[0];
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