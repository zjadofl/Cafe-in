package com.example.cafeinadmin;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class CSManageDetail extends AppCompatActivity implements View.OnClickListener {

    TextView csTitle,reasonTxt, writerText2, csDateText2, csType, dateText2;
    EditText answerEdit;
    Button csBtn;

    String id, reply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csmanage_detail);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("CS 상세");
        toolbar.setTitleTextColor(Color.WHITE);

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김


        csTitle = (TextView) findViewById(R.id.csTitle);
        reasonTxt = (TextView) findViewById(R.id.reasonTxt);
        writerText2 = (TextView) findViewById(R.id.writerText2);
        csType = (TextView) findViewById(R.id.csType);
        csDateText2 = (TextView) findViewById(R.id.csDateText2);
        dateText2 = (TextView) findViewById(R.id.dateText2);

        answerEdit = (EditText) findViewById(R.id.answerEdit);

        csBtn = (Button) findViewById(R.id.csBtn);

        Intent detailIntent = getIntent();
        id = detailIntent.getStringExtra("id");
        String userID = detailIntent.getStringExtra("userID");
        String name = detailIntent.getStringExtra("name");
        String content = detailIntent.getStringExtra("content");
        String wDate = detailIntent.getStringExtra("wDate");
        String date = detailIntent.getStringExtra("date");
        String type = detailIntent.getStringExtra("type");
        reply = detailIntent.getStringExtra("reply");

        Log.i("id값", id);
        csTitle.setText(wDate);
        writerText2.setText(userID);
        reasonTxt.setText(content);
        csType.setText("[" + type + "]");
        csDateText2.setText(date);
        dateText2.setText(name);
        if (!reply.equals("")) {
            answerEdit.setText(reply);
            answerEdit.setEnabled(false);
        }


        if (!reply.equals("")) {
            csBtn.setText("확인");
        }
        csBtn.setOnClickListener(this);
        //액티비티가 아닌 곳에서 startActivity()를 하면 에러가 생긴다. 그걸 해결해주는 코드.
        detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.csBtn:
                if (!reply.equals("")) {
                    CSManageDetail.this.finish();
                } else {
                    String answer = answerEdit.getText().toString().trim();
                    if (answer.equals("")) {
                        answerEdit.setError("답변을 입력해주세요.");
                    } else {
                        try {
                            String returns = new CSDetailTask().execute(answer, id).get();
                            if (returns.equals("ok")) {
                                Toast.makeText(this,"성공적으로 답변을 작성하였습니다.", Toast.LENGTH_LONG).show();
                                CSManageDetail.this.finish();
                            }
                            Log.i("cpid?", id);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    static class CSDetailTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/insertCSAnswer.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //convertImg, name, price, explain
                sendMsg = "answer=" + strings[0] + "&cpID=" + strings[1];
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
