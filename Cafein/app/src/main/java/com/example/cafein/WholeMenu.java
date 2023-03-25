package com.example.cafein;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class WholeMenu extends AppCompatActivity {

    private TabLayout wholeMenuTab;
    private ViewPager wholeMenuViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        setTitle("전체 메뉴");

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        //actionBar.setHomeAsUpIndicator(R.drawable.button_back); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요



        wholeMenuTab = (TabLayout) findViewById(R.id.wholeMenuTab);
        wholeMenuViewPager = (ViewPager) findViewById(R.id.wholeMenuViewPager);

        //wholeMenuTab.addTab(wholeMenuTab.newTab().setIcon(R.drawable.tab1));
        wholeMenuTab.addTab(wholeMenuTab.newTab().setText("커피"));
        wholeMenuTab.addTab(wholeMenuTab.newTab().setText("논커피"));
        wholeMenuTab.addTab(wholeMenuTab.newTab().setText("베이커리"));
        wholeMenuTab.setTabGravity(TabLayout.GRAVITY_FILL);

        WholeMenuTabPagerAdapter pagerAdapter = new WholeMenuTabPagerAdapter(getSupportFragmentManager(), wholeMenuTab.getTabCount());
        wholeMenuViewPager.setAdapter(pagerAdapter);
        wholeMenuViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(wholeMenuTab));

        wholeMenuTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                wholeMenuViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    } //onCreate 끝

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
