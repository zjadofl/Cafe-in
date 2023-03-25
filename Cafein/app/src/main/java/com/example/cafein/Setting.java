package com.example.cafein;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Objects;


public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("설정");
        toolbar.setTitleTextColor(Color.WHITE);

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김







        Fragment fragment = new SettingHolder();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            transaction.add(R.id.settingHolder, fragment, "setting_screen");
        }
        transaction.commit();

    } //onCreate 끝

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

    public static class SettingHolder extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_setting);

            Preference userID = (Preference) findPreference("userID");
            String id = PreferenceUtils.getId(getActivity());
            userID.setTitle(id);


            //비밀번호 변경.
            PreferenceScreen subPwChange = (PreferenceScreen) findPreference("subPwChange");
            subPwChange.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent pwIntent = new Intent(getActivity(), ChangePw.class);
                    startActivity(pwIntent);
                    return true;
                }
            });

            //개인정보 변경.
            /*PreferenceScreen subUserInfoChange = (PreferenceScreen) findPreference("subUserInfo");
            subUserInfoChange.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent pwIntent = new Intent(getActivity(), ChangeUserInfo.class);
                    startActivity(pwIntent);
                    return true;
                }
            });*/

            //로그아웃
            Preference logout = (Preference) findPreference("logout");
            logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.i("클릭", "됨");
                    PreferenceUtils.saveId("", getActivity());
                    PreferenceUtils.savePassword("", getActivity());

                    PreferenceUtils.logout(getActivity());
                    Intent loginIntent = new Intent(getActivity(), SignInActivity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                    return true;
                }
            });

        } //onCreate 끝


    }
} //class 끝
