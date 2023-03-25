package com.example.cafein;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafein.Database.ModelDB.DataSource.BasketRepository;
import com.example.cafein.Database.ModelDB.Local.BasketDataSource;
import com.example.cafein.Database.ModelDB.Local.BasketDatabase;
import com.example.cafein.Utils.Common;

import java.util.Objects;
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

        //navigation drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        setTitle("Cafe-in");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        //String usName = PreferenceUtils.getName(this) + "님";
        //userName.setText(usName);

        //init Database
        initDB();

        settingIcon.setOnClickListener(this);



    } //onCreate 끝

    private void initDB() {
        Common.basketDatabase = BasketDatabase.getInstance(this);
        Common.basketRepository = BasketRepository.getInstance(BasketDataSource.getInstance(Common.basketDatabase.basketDAO()));
    }

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
        if (id == R.id.alarm_btn) {
            Intent detailIntent = new Intent(this, OrderStatus2.class);
            startActivity(detailIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_home) { //메인
            fragment = new Main();
            title = "Cafe-in";

        } else if (id == R.id.nav_eCard) { //e-카드
            fragment = new ECardMain();
            title = "E-카드";

        } else if (id == R.id.nav_eStamp) { //e-스탬프
            fragment = new EStampMain();
            title = "E-스탬프";

        } else if (id == R.id.nav_smartOrder) { //스마트 오더
            fragment = new SmartOrder();
            title = "스마트오더";

        } else if (id == R.id.nav_complain) { //컴플레인
            fragment = new ComplainMain();
            title = "컴플레인";

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
                //Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //액션바 핸들링하여 툴바의 제목을 바꿔주는 메소드.
    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }


    @Override
    public void onReceivedData(String name) {
        receivedUserName = name;
        userName.setText(receivedUserName);
    }
}
