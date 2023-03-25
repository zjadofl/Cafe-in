package com.example.cafein;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgabrielfreitas.core.BlurImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout registerGroup;
    EditText IDEditText, passwordEditText;
    Button loginBtn;
    TextView forgetPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        registerGroup = (LinearLayout) findViewById(R.id.registerGroup);
        IDEditText = (EditText) findViewById(R.id.IDEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        loginBtn = (Button) findViewById(R.id.loginBtn);

        //forgetPw = (TextView) findViewById(R.id.forgetPw);


        registerGroup.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        //forgetPw.setOnClickListener(this);

        if (PreferenceUtils.getId(this) != null){
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            SignInActivity.this.finish();
        }
    }//onCreate 끝


    //이벤트 리스너
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.registerGroup: //register 화면으로 이동.
                Intent registerIntent = new Intent(SignInActivity.this, RegStep.class);
                startActivity(registerIntent);
                break;


            case R.id.loginBtn:
                String usID = IDEditText.getText().toString().trim(); //아이디
                String usPw = passwordEditText.getText().toString().trim(); //비밀번호

                if (validateUserLogin()) {
                    try {
                        String result = new LoginTask().execute(usID, usPw).get();
                        Log.i("로그인", result + " ");
                        if (result.equals("true")) {
                            Log.i("로그인", "성공");
                            //로그인 상태로 유지.
                            PreferenceUtils.saveId(usID, this);
                            PreferenceUtils.savePassword(usPw, this);

                            //Toast.makeText(SignInActivity.this,"로그인", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            SignInActivity.this.finish();
                        } else if (result.equals("false")) {
                            Toast.makeText(SignInActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            IDEditText.setText("");
                            passwordEditText.setText("");
                        } else if (result.equals("noId")) {
                            Toast.makeText(SignInActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                            IDEditText.setText("");
                            passwordEditText.setText("");
                        }

                        //Toast.makeText(SignInActivity.this, "결과" + result, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            /*case R.id.forgetPw:
                Intent forgetPw = new Intent(this, FindUserID.class);
                startActivity(forgetPw);
                break;*/
        }
    }


    private boolean validateUserLogin () {
        String idInput = IDEditText.getText().toString().trim();
        String pwInput = passwordEditText.getText().toString().trim();

        //아이디 유효성 검사
        if(idInput.isEmpty()) {
            IDEditText.setError("아이디를 입력해주세요.");
            IDEditText.requestFocus();
            return false;
        }

        //비밀번호 유효성 검사
        if(pwInput.isEmpty()) {
            passwordEditText.setError("비밀번호를 입력해주세요.");
            passwordEditText.requestFocus();
            return false;
        }

        return true;
    }



    @SuppressLint("StaticFieldLeak")
    class LoginTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                //URL url = new URL("http://14.33.171.115:8080/CafeinProject/loginUser.jsp");
                URL url = new URL("http://cafein.freehost.kr/loginUser.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "usID="+strings[0]+"&usPw="+strings[1];
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
