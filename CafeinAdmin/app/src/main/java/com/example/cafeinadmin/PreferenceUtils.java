package com.example.cafeinadmin;

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

    //사용자 번호
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

    //사용자 직급
    public static boolean savePos(String pos, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_USER_POS, pos);
        prefsEditor.apply();
        return true;
    }

    public static String getPos(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_USER_POS, null);
    }


}
