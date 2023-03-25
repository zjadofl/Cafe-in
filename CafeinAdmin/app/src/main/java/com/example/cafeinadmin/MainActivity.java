package com.example.cafeinadmin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, Main.OnReceivedDataListener {

    TextView userName;
    ImageView settingIcon;
    String receivedUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle("Cafe-in Admin.");

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //메뉴 버튼 변경.
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        toggle.setHomeAsUpIndicator(R.drawable.menu_icon);


        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Fragment인 Main을 메인화면으로 만듦.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_fragment_layout, new Main());
        ft.commit();


        //네비게이션에 이름 추가.
        userName = (TextView) headerView.findViewById(R.id.userName);
        settingIcon = (ImageView) headerView.findViewById(R.id.settingIcon);

        settingIcon.setOnClickListener(this);



    } //onCreate 끝

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_home) {
            fragment = new Main();
            title = "Cafe-in Admin.";

        } else if (id == R.id.nav_order) {
            fragment = new OrderManage();
            title = "주문관리";


        } else if (id == R.id.nav_menu) {
            fragment = new MenuManage();
            title = "메뉴관리";

        } else if (id == R.id.nav_sale) {
            fragment = new SalesManage();
            title = "매출관리";

        } else if (id == R.id.nav_cs) {
            fragment = new CSManage();
            title = "CS관리";

        } else if (id == R.id.nav_stock) {
            fragment = new StockManageEdit();
            title = "재고관리";

        } else if (id == R.id.nav_employee) {
            fragment = new EmployeeManage();
            title = "직원관리";
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //다른 Fragment로 이동.
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment_layout, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settingIcon:
                Intent settingIntent = new Intent(this, Setting.class);
                startActivity(settingIntent);
                break;
        }
    }

    @Override
    public void onReceivedData(String name) {
        receivedUserName = name;
        userName.setText(receivedUserName);
    }

    //액션바 핸들링하여 툴바의 제목을 바꿔주는 메소드.
    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public class OrderTask extends AsyncTask<String, Void, String> {

        private final String url;
        private final String sId;
        private final String encodeType;
        private final String msg;

        /**
         *
         * @param url url for request
         * @param sId key and value (ex. "key=1234";)
         * @param encodeType ex. UTF-8, EUC-KR
         */
        public OrderTask(String url, String sId, String encodeType, String msg) {
            this.url = url;
            this.sId = sId;
            this.encodeType = encodeType;
            this.msg = msg;
        }

        private String sResult;

        @Override
        protected String doInBackground(String[] sId) {
            String str;
            try {
                URL url = new URL(this.url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.connect();
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), encodeType);
                osw.write(this.sId+"&message="+msg);
                osw.flush();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), encodeType);
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        stringBuilder.append(str);
                    }
                    sResult = stringBuilder.toString();

                } else {
                    Log.i("통신결과", conn.getResponseMessage() + conn.getResponseCode() + "/" + conn.getErrorStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sResult;
        }
    }



}
