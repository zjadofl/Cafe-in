package com.example.cafein;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    public PreferenceUtils(){

    }

    public static boolean logout(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    //사용자 아이디
    public static boolean saveNum(String num, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_NUM, num);
        prefsEditor.apply();
        return true;
    }

    public static String getNum(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_NUM, null);
    }

    //사용자 아이디
    public static boolean saveId(String id, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ID, id);
        prefsEditor.apply();
        return true;
    }

    public static String getId(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ID, null);
    }


    //사용자 비밀번호
    public static boolean savePassword(String password, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_PASSWORD, password);
        prefsEditor.apply();
        return true;
    }

    public static String getPassword(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PASSWORD, null);
    }

    //사용자 이름
    public static boolean saveName(String name, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_USER_NAME, name);
        prefsEditor.apply();
        return true;
    }

    public static String getName(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_USER_NAME, null);
    }


    //사용자 e-머니
    public static boolean saveEMoney(String eMoney, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_EMONEY, eMoney);
        prefsEditor.apply();
        return true;
    }

    public static String getEMoney(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_EMONEY, null);
    }

    //사용자 e-스탬프
    public static boolean saveEStamp(String eStamp, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_ESTAMP, eStamp);
        prefsEditor.apply();
        return true;
    }

    public static String getEStamp(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_ESTAMP, null);
    }

}
