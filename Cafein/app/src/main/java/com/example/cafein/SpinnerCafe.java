package com.example.cafein;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SpinnerCafe extends DialogFragment implements View.OnClickListener {

    public static final String TAG_SPINNER_Cafe = "spinner_cafe";
    private ArrayList<Store> mArrayList;
    private StoreAdapter mAdapter;
    private int count = -1;

    public SpinnerCafe() {}
    public static SpinnerCafe getInstance() {
        SpinnerCafe s = new SpinnerCafe();
        return s;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.spinner_cafe, container);
        setCancelable(false);
        return v;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
}
