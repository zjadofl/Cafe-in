package com.example.cafein;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FindUserID extends AppCompatActivity implements View.OnClickListener{

    EditText birthEdit, IDEdit;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user_id);

        birthEdit = (EditText) findViewById(R.id.birthEdit);
        IDEdit = (EditText) findViewById(R.id.IDEdit);
        btn1 = (Button) findViewById(R.id.btn1);

        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                String userBirth = birthEdit.getText().toString();
                String userId = IDEdit.getText().toString();


        } //switch 끝
    } //onClick 끝
} //class 끝
