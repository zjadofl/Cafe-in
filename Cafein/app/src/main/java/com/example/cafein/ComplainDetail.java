package com.example.cafein;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class ComplainDetail extends AppCompatActivity {

    TextView complainType, complainTitle, complainWriteDate, complainDate, complainContent, complainReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_detail);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("컴플레인 상세");
        toolbar.setTitleTextColor(Color.WHITE);

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        complainType = (TextView) findViewById(R.id.complainType);
        complainTitle = (TextView) findViewById(R.id.complainTitle);
        complainWriteDate = (TextView) findViewById(R.id.complainWriteDate);
        complainDate = (TextView) findViewById(R.id.complainDate);
        complainContent = (TextView) findViewById(R.id.complainContent);
        complainReply = (TextView) findViewById(R.id.complainReply);



        Intent detailIntent = getIntent();
        String name = detailIntent.getStringExtra("name");
        String content = detailIntent.getStringExtra("content");
        String date = detailIntent.getStringExtra("date");
        String type = "[" + detailIntent.getStringExtra("type") + "] ";
        String wDate = detailIntent.getStringExtra("wDate");
        String reply = detailIntent.getStringExtra("reply");

        complainType.setText(type);
        complainTitle.setText(name);
        complainDate.setText(complainDate.getText() + date);
        complainContent.setText(content);
        complainWriteDate.setText(complainWriteDate.getText() + wDate);
        complainReply.setText(reply);





    }

    //백버튼 작동
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
