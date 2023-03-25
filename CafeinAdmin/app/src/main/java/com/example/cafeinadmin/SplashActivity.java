package com.example.cafeinadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SplashActivity extends AppCompatActivity {
    View logoIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemClock.sleep(3000);
        Intent loginIntent = new Intent(SplashActivity.this, SignInActivity.class);
        startActivity(loginIntent);

        finish();


    }

}
