package com.example.cafeinadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class ChangePw extends AppCompatActivity implements View.OnClickListener {

    //비밀번호 조건: 필수 영숫자, 특수문자 8 ~ 15자리.
    public static final Pattern PW_REGEX
            = Pattern.compile("^"+
            "(?=.*[0-9])" +        //at least 1 digit
            "(?=.*[a-zA-Z])" +   //at least 1 lower/upper case letter
            "(?=.*[@#$%^&!?])" +   //at least 1 special character
            "(?=\\S+$)" +          //no white spaces
            ".{8,15}" +            //at least 8
            "$");

    EditText originalPw, newPw, newPwConfirm;
    RelativeLayout step1, step2;
    Button step1NextBtn, step2NextBtn;
    TextView confirmMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        setTitle("비밀번호 변경");

        originalPw = (EditText) findViewById(R.id.originalPw);
        newPw = (EditText) findViewById(R.id.newPw);
        newPwConfirm = (EditText) findViewById(R.id.newPwConfirm);

        confirmMsg = (TextView) findViewById(R.id.confirmMsg);

        step1 = (RelativeLayout) findViewById(R.id.step1);
        step2 = (RelativeLayout) findViewById(R.id.step2);

        step1NextBtn = (Button) findViewById(R.id.step1NextBtn);
        step2NextBtn = (Button) findViewById(R.id.step2NextBtn);








        //step1이 보임.
        step1.setVisibility(View.VISIBLE);

        step1NextBtn.setOnClickListener(this);
        step2NextBtn.setOnClickListener(this);

    } //onCreate 끝

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


    @Override
    public void onClick(View v) {
        String pwInput = originalPw.getText().toString();
        String usID = PreferenceUtils.getId(this);
        String usPW = PreferenceUtils.getPassword(this);
        switch (v.getId()) {
            case R.id.step1NextBtn:
                if (pwInput.isEmpty()) {
                    originalPw.setError("비밀번호를 입력해주세요.");
                    originalPw.requestFocus();
                } else { //pwInput이 빈칸은 아닐 때
                    if (pwInput.equals(usPW)) { //일치할 떄
                        step1.setVisibility(View.GONE);
                        step2.setVisibility(View.VISIBLE);
                    } else { //일치 안 할때
                        originalPw.setError("비밀번호가 틀렸습니다.");
                        originalPw.requestFocus();
                    }
                }
                break;

            case R.id.step2NextBtn:
                String newPwInput = newPw.getText().toString();
                String newPwComfirmInput = newPwConfirm.getText().toString();
                if (newPwInput.isEmpty()) { //비밀번호가 비었을 때
                    newPw.setError("비밀번호를 입력해주세요.");
                    newPw.requestFocus();
                } else if (newPwComfirmInput.isEmpty()){ //비밀번호 확인이 비었을 때
                    newPwConfirm.setError("비밀번호를 입력해주세요.");
                    newPwConfirm.requestFocus();
                } else { //값이 있을 때
                    if (newPwInput.equals(newPwComfirmInput)) { //비밀번호와 비밀번호 확인이 일치할 때
                        if(newPwInput.equals(usPW)) { //원래 비밀번호랑 일치하는지 확인.
                            confirmMsg.setText("새로운 비밀번호를 입력해주세요.");
                        } else { //일치하지 않을 때 (새로운 비밀번호일 때)
                            if(PW_REGEX.matcher(newPwInput).matches()) { //비밀번호 형식에 맞을 때
                                Toast.makeText(this, "성공적으로 비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent settingIntent = new Intent(this, Setting.class);
                                startActivity(settingIntent);

                                //새로운 비밀번호로 변경하기.
                                PreferenceUtils.savePassword(newPwInput, this);
                                try {
                                    String result = new ChangePwTask().execute(usID, newPwInput).get();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else { //비밀번호 형식에 맞지 않았을때
                                confirmMsg.setText("영숫자, 특수문자를 이용한 8~15글자인 비밀번호를 입력해주세요.");
                            }
                        }
                    } else { //일치하지 않을 때
                        confirmMsg.setText("비밀번호가 일치하지 않습니다.");
                    }
                }
                break;
        } //switch 끝
    }

    @SuppressLint("StaticFieldLeak")
    class ChangePwTask extends AsyncTask<String, Void, String> {
        private String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/empChangePw.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "usID="+strings[0]+"&usPw="+strings[1];
                Log.i("비밀번호", strings[1] + "이다.");
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder buffer = new StringBuilder();
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


} //class 끝
