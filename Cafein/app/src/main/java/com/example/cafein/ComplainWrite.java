package com.example.cafein;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ComplainWrite extends AppCompatActivity implements View.OnClickListener {
    Button complainDateBtn;
    TextView complainDate;
    Button complainWriteBtn;
    AlertDialog alertDialog;
    EditText complainTitle, complainContent;
    Spinner complainTypeSpinner;

    String userNum, cDate;


    SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
    Calendar time = Calendar.getInstance();
    Calendar minDate = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();

    String complainWriteDate = format1.format(time.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_write);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("컴플레인 작성");
        toolbar.setTitleTextColor(Color.WHITE);

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김




        complainDateBtn = (Button) findViewById(R.id.complainDateBtn);
        complainDateBtn.setOnClickListener(this);
        complainDate = (TextView) findViewById(R.id.complainWriteDate);
        complainWriteBtn = (Button) findViewById(R.id.complainWriteBtn);
        complainWriteBtn.setOnClickListener(this);
        complainTitle = (EditText) findViewById(R.id.complainTitle);
        complainContent = (EditText) findViewById(R.id.complainContent);
        complainTypeSpinner = (Spinner)findViewById(R.id.complainTypeSpinner);

        userNum = PreferenceUtils.getNum(this); //사용자 번호 가져오기.
        //Log.i("사용자 번호를 알려주라", userNum);


        complainTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String getType = complainTypeSpinner.getSelectedItem().toString();
                Log.i("스피너 테스트", "테스트" + getType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    } //onCreate 끝


    @Override
    public void onClick(View v) {
        if (v == complainDateBtn) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    cDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    complainDate.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
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

        } else if (v == complainWriteBtn) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("글을 등록하시겠습니까?\n");
            builder.setMessage(" 빠른 시일내에 개선하도록 하겠습니다.\n 행복한 하루되세요!");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String cTitle = complainTitle.getEditableText().toString().trim();
                    complainTypeSpinner = (Spinner)findViewById(R.id.complainTypeSpinner);
                    final String getType = complainTypeSpinner.getSelectedItem().toString();
                    String cType= getType;
                    Log.i("테스트", "테스트" + cType);
                    //String cDate = complainDate.getText().toString().trim();
                    String cContent = complainContent.getEditableText().toString().trim();
                    String title = complainTitle.getText().toString().trim();
                    if (title.equals("")) {
                        complainTitle.setError("제목을 입력해주세요.");
                    } else if (getType.equals("유형을 선택해주세요.")) {
                        Toast.makeText(ComplainWrite.this, "유형을 선택해주세요.", Toast.LENGTH_LONG).show();
                    } else {
                        String cpResult = null;
                        try {
                            cpResult = new ComplainTask().execute(cTitle, cType, cDate, cContent, complainWriteDate, userNum).get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //Intent intent = new Intent(getApplicationContext(), ComplainDetail.class);
                        if (cpResult.equals("not ok")) {
                            Toast.makeText(ComplainWrite.this, "등록에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            finish();
                        }
                        //화면을 띄워주고 다시 돌아오기 위함 요청 코드 필요

                        //startActivity(intent);
                    }

                }
            }).setNegativeButton("취소", null);

            alertDialog = builder.create();
            alertDialog.show();
        }
    }


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

    class ComplainTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/insertComplain.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "cTitle=" + strings[0] + "&cType=" + strings[1] + "&cDate=" + strings[2] + "&cContent=" + strings[3]
                        + "&cWriteDate=" + strings[4] + "&userNum=" + strings[5];
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