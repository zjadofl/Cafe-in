
package com.example.cafeinadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class OrderCancelList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cancel_list);

        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("주문 취소 목록");


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //startActivity(new Intent(this, OrderManage.class));
        //finish();

    }



}
