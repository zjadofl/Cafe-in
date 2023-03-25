package com.example.cafeinadmin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class EmployeeManageDetailEdit extends AppCompatActivity implements View.OnClickListener {
    TextView nameText;
    EditText phoneEdit, addressEdit, dateEdit, partEdit1, partEdit2;
    TimePickerDialog dialog1, dialog2;
    Button regBtn;

    //데이트 피커
    Calendar time = Calendar.getInstance();
    Calendar minDate = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();

    String detailName, num, name, phone, date, address, part1, part2, employeeInfo, workTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage_detail_edit);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("직원 수정");
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        nameText = (TextView) findViewById(R.id.nameText);
        phoneEdit = (EditText) findViewById(R.id.phoneEdit);
        addressEdit = (EditText) findViewById(R.id.addressEdit);
        dateEdit = (EditText) findViewById(R.id.dateEdit);
        partEdit1 = (EditText) findViewById(R.id.partEdit1);
        partEdit2 = (EditText) findViewById(R.id.partEdit2);

        regBtn = (Button) findViewById(R.id.regBtn);

        Intent detailIntent = getIntent();
        num = detailIntent.getStringExtra("num");
        workTime = detailIntent.getStringExtra("time");
        detailName = detailIntent.getStringExtra("name");

        nameText.setText(detailName);


        dialog1 = new TimePickerDialog(this, listener1, 15, 24, false);
        dialog2 = new TimePickerDialog(this, listener2, 15, 24, false);

        name = nameText.getText().toString().trim();
        phone = phoneEdit.getText().toString().trim();
        date = dateEdit.getText().toString().trim();
        address = addressEdit.getText().toString().trim();
        part1 = partEdit1.getText().toString().trim();
        part2 = partEdit2.getText().toString().trim();

        try {
            //해당 직원의 정보를 읽어옴.
            employeeInfo = new EmployeeManageDetail.GetEmployeeInfoTask().execute(num.toString()).get();
            if (!(employeeInfo == null)) {
                Log.i("json 받음", employeeInfo);
                setSetting(employeeInfo);
            }
        } catch (ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Log.i("json 받음3", employeeInfo);


        dateEdit.setOnClickListener(this);
        partEdit1.setOnClickListener(this);
        partEdit2.setOnClickListener(this);
        regBtn.setOnClickListener(this);

    } //onCreate 끝


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateEdit:
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateEdit.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                    }
                }, year, month + 1, day);

                /* 최소 날짜 설정.
                minDate.set(2018,2-1,10);
                datePickerDialog.getDatePicker().setMinDate(minDate.getTime().getTime());
                */

                // 최대 날짜 설정.
                Date todayDate = new Date ();
                maxDate.setTime(todayDate);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

                datePickerDialog.show();
                break;

            case R.id.partEdit1:
                dialog1.show();
                break;

            case R.id.partEdit2:
                dialog2.show();
                break;

            case R.id.regBtn:
                name = nameText.getText().toString().trim();
                phone = phoneEdit.getText().toString().trim();
                date = dateEdit.getText().toString().trim();
                address = addressEdit.getText().toString().trim();
                part1 = partEdit1.getText().toString().trim();
                part2 = partEdit2.getText().toString().trim();

                //빈칸 체크.
                if (name.equals("")) {
                    nameText.setError("이름을 입력해주세요.");
                    nameText.setFocusable(true);
                } else if (phone.equals("")) {
                    phoneEdit.setError("전화번호를 입력해주세요.");
                    phoneEdit.setFocusable(true);
                } else if (date.equals("")) {
                    dateEdit.setError("입사일을 입력해주세요.");
                    dateEdit.setFocusable(true);
                } else if (part1.equals("")) {
                    partEdit1.setError("출근시간을 입력해주세요.");
                    partEdit1.setFocusable(true);
                } else if (part2.equals("")) {
                    partEdit2.setError("퇴근시간을 입력해주세요.");
                    partEdit2.setFocusable(true);
                } else {
                    try {
                        //년월일, 시분을 :로 바꿔야 mysql에 넣을 수 있음.
                        //변환과정.
                        //년월일.
                        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy년 MM월 dd일");
                        SimpleDateFormat format2 = new SimpleDateFormat ( "yyyy-MM-dd");
                        Date date1 = format1.parse(date); //Date로 형변환.
                        String formatDate1 = format2.format(date1); //형식으로 바꿈.

                        //시분
                        SimpleDateFormat format3 = new SimpleDateFormat ( "HH시 mm분");
                        SimpleDateFormat format4 = new SimpleDateFormat ( "HH:mm");
                        Date time1 = format3.parse(part1); //Date로 형변환.
                        Date time2 = format3.parse(part2); //Date로 형변환.
                        String formatTime1 = format4.format(time1); //형식으로 바꿈.
                        String formatTime2 = format4.format(time2); //형식으로 바꿈.
                        Log.i("날짜", formatDate1);

                        String info = new EmployeeManageDetail.UpdateEmployeeInfoTask().execute(phone, address,
                                formatDate1, formatTime1, formatTime2, num).get(); //정보 업뎃.
                        Log.i("phone:",""+phone);
                        Log.i("address:",""+address);
                        Log.i("date:",date);
                        Log.i("part1:",formatTime1);
                        Log.i("part2:",formatTime2);
                        Log.i("num:",num+"");
                        Log.i("info", info);
                        if (info.equals("ok")) {
                            String result = new EmployeeManageDetail.GetEmployeeInfoTask().execute(num.toString()).get();
                            Log.i("설정하기 위해 가져옴", result);
                            //setSetting(result);
                            Intent detailIntent = new Intent(this, EmployeeManageDetail.class);
                            detailIntent.putExtra("num", num);
                            detailIntent.putExtra("name", detailName);
                            startActivity(detailIntent);
                            EmployeeManageDetailEdit.this.finish();
                        }

                    } catch (ExecutionException | ParseException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } //else 끝


                break;
        }
    } //onClick 끝

    //타임 피커1
    private TimePickerDialog.OnTimeSetListener listener1 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // 설정버튼 눌렀을 때
            //Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            partEdit1.setText(hourOfDay + "시 " + minute + "분");

        }

    };

    //타임 피커2
    private TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // 설정버튼 눌렀을 때
            //Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
            partEdit2.setText(hourOfDay + "시 " + minute + "분");

        }

    };

    //툴바에 menu item 추가.
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

            phoneEdit.setText(jPhone);
            addressEdit.setText(jAddress);
            if (!jDate.equals("0000년 00월 00일")){
                dateEdit.setText(jDate);
            }
            partEdit1.setText(jPart1);
            partEdit2.setText(jPart2);
        }
    } //setSetting 끝



}
