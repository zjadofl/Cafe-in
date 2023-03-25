package com.example.cafeinadmin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmployeeRegister extends AppCompatActivity implements View.OnClickListener {

    EditText nameEdit, phoneEdit, addressEdit, dateEdit, partEdit1, partEdit2;
    TimePickerDialog dialog1, dialog2;
    Button regBtn;

    //데이트 피커
    Calendar time = Calendar.getInstance();
    Calendar minDate = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();

    String name, phone, date, part1, part2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);

        nameEdit = (EditText) findViewById(R.id.nameEdit);
        phoneEdit = (EditText) findViewById(R.id.phoneEdit);
        addressEdit = (EditText) findViewById(R.id.addressEdit);
        dateEdit = (EditText) findViewById(R.id.dateEdit);
        partEdit1 = (EditText) findViewById(R.id.partEdit1);
        partEdit2 = (EditText) findViewById(R.id.partEdit2);

        regBtn = (Button) findViewById(R.id.regBtn);

        dialog1 = new TimePickerDialog(this, listener1, 15, 24, false);
        dialog2 = new TimePickerDialog(this, listener2, 15, 24, false);

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
                name = nameEdit.getText().toString().trim();
                phone = phoneEdit.getText().toString().trim();
                date = dateEdit.getText().toString().trim();
                part1 = partEdit1.getText().toString().trim();
                part2 = partEdit2.getText().toString().trim();

                if (name.equals("")) {
                    nameEdit.setError("이름을 입력해주세요.");
                    nameEdit.setFocusable(true);
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
                    String json = sendEmployeeInfo();
                    Log.i("jsonEmployee", json);
                    //String returns = new EmployeeInfoTask().execute(name, price, explain, radioCategory, json).get();
                }
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

    //json 파일을 서버로 보내는 메소드
    private String sendEmployeeInfo() {
        JSONObject jsonObject = new JSONObject();
        JSONArray employeeArray = new JSONArray();
        JSONObject infoObject = new JSONObject();

        try {
            infoObject.put("name", name);
            infoObject.put("phone", phone);
            infoObject.put("date", date);
            infoObject.put("part1", part1);
            infoObject.put("part2", part2);
            employeeArray.put(infoObject);
            jsonObject.put("employee", employeeArray);

            Log.i("Test:",infoObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return employeeArray.toString();
    }


    static class EmployeeInfoTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/insertMenu.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //convertImg, name, price, explain
                sendMsg = "name="+strings[0]+"&price="+strings[1] + "&explain="+strings[2] + "&type="+strings[3] +
                        "&json="+strings[4];
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
