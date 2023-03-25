package com.example.cafein;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class EStampMain extends Fragment {
    String num, obj;
    ImageView stamp1, stamp2, stamp3, stamp4, stamp5, stamp6, stamp7, stamp8, stamp9, stamp10, stamp11, stamp12;
    TextView stampNum;


    public EStampMain() {
        // Required empty public constructor
    }

    public static EStampMain newInstance() {
        return new EStampMain();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estamp_main, container, false);
        stamp1 = view.findViewById(R.id.stamp1);
        stamp2 = view.findViewById(R.id.stamp2);
        stamp3 = view.findViewById(R.id.stamp3);
        stamp4 = view.findViewById(R.id.stamp4);
        stamp5 = view.findViewById(R.id.stamp5);
        stamp6 = view.findViewById(R.id.stamp6);
        stamp7 = view.findViewById(R.id.stamp7);
        stamp8 = view.findViewById(R.id.stamp8);
        stamp9 = view.findViewById(R.id.stamp9);
        stamp10 = view.findViewById(R.id.stamp10);
        stamp11 = view.findViewById(R.id.stamp11);
        stamp12 = view.findViewById(R.id.stamp12);

        stampNum = view.findViewById(R.id.stampNum);

        num = PreferenceUtils.getNum(getActivity());

        //초기값.
        obj = "0";
        stampNum.setText(obj + "/12");

        //stamp 갯수
        obj = PreferenceUtils.getEStamp(getActivity());
        stampNum.setText(obj + "/12");


        stamp();
        return view;
    }

    private void stamp() {
        int stampCnt = Integer.parseInt(obj);
        if (stampCnt == 0) {

        }
        else if (stampCnt == 1) {
            stamp1.setImageResource(R.drawable.stamp);
        }
        else if (stampCnt == 2) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
        }
        else if (stampCnt == 3) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);

        }
        else if (stampCnt == 4) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);
            stamp4.setImageResource(R.drawable.stamp);


        }
        else if (stampCnt == 5) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);
            stamp4.setImageResource(R.drawable.stamp);
            stamp5.setImageResource(R.drawable.stamp);

        }
        else if (stampCnt == 6) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);
            stamp4.setImageResource(R.drawable.stamp);
            stamp5.setImageResource(R.drawable.stamp);
            stamp6.setImageResource(R.drawable.stamp);


        }
        else if (stampCnt == 7) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);
            stamp4.setImageResource(R.drawable.stamp);
            stamp5.setImageResource(R.drawable.stamp);
            stamp6.setImageResource(R.drawable.stamp);
            stamp7.setImageResource(R.drawable.stamp);


        }
        else if (stampCnt == 8) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);
            stamp4.setImageResource(R.drawable.stamp);
            stamp5.setImageResource(R.drawable.stamp);
            stamp6.setImageResource(R.drawable.stamp);
            stamp7.setImageResource(R.drawable.stamp);
            stamp8.setImageResource(R.drawable.stamp);

        }
        else if (stampCnt == 9) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);
            stamp4.setImageResource(R.drawable.stamp);
            stamp5.setImageResource(R.drawable.stamp);
            stamp6.setImageResource(R.drawable.stamp);
            stamp7.setImageResource(R.drawable.stamp);
            stamp8.setImageResource(R.drawable.stamp);
            stamp9.setImageResource(R.drawable.stamp);

        }
        else if (stampCnt == 10) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);
            stamp4.setImageResource(R.drawable.stamp);
            stamp5.setImageResource(R.drawable.stamp);
            stamp6.setImageResource(R.drawable.stamp);
            stamp7.setImageResource(R.drawable.stamp);
            stamp8.setImageResource(R.drawable.stamp);
            stamp9.setImageResource(R.drawable.stamp);
            stamp10.setImageResource(R.drawable.stamp);


        }
        else if (stampCnt == 11) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);
            stamp4.setImageResource(R.drawable.stamp);
            stamp5.setImageResource(R.drawable.stamp);
            stamp6.setImageResource(R.drawable.stamp);
            stamp7.setImageResource(R.drawable.stamp);
            stamp8.setImageResource(R.drawable.stamp);
            stamp9.setImageResource(R.drawable.stamp);
            stamp10.setImageResource(R.drawable.stamp);
            stamp11.setImageResource(R.drawable.stamp);


        }
        else if (stampCnt >= 12) {
            stamp1.setImageResource(R.drawable.stamp);
            stamp2.setImageResource(R.drawable.stamp);
            stamp3.setImageResource(R.drawable.stamp);
            stamp4.setImageResource(R.drawable.stamp);
            stamp5.setImageResource(R.drawable.stamp);
            stamp6.setImageResource(R.drawable.stamp);
            stamp7.setImageResource(R.drawable.stamp);
            stamp8.setImageResource(R.drawable.stamp);
            stamp9.setImageResource(R.drawable.stamp);
            stamp10.setImageResource(R.drawable.stamp);
            stamp11.setImageResource(R.drawable.stamp);
            stamp12.setImageResource(R.drawable.stamp);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("E 스탬프");
        }
    }

}