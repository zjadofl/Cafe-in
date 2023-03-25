package com.example.cafein;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main extends Fragment implements View.OnClickListener {

    CardView smartOrderCV, complainCV, eStampCV, eCardCV;
    RelativeLayout spinnerCafe;
    TextView personName, stampText, moneyText;
    DecimalFormat formatter;

    String obj;


    public Main() {
        // Required empty public constructor
    }

    public static Main newInstance() {
        return new Main();
    }

    public interface OnReceivedDataListener {
        void onReceivedData(String name);
    }

    private OnReceivedDataListener mORDListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof OnReceivedDataListener) {
            mORDListener = (OnReceivedDataListener) getActivity();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, null);

        smartOrderCV = (CardView) view.findViewById(R.id.smartOrderCV);
        complainCV = (CardView) view.findViewById(R.id.complainCV);
        eStampCV = (CardView) view.findViewById(R.id.eStampCV);
        eCardCV = (CardView) view.findViewById(R.id.eCardCV);

        personName = (TextView) view.findViewById(R.id.personName);
        stampText = (TextView) view.findViewById(R.id.stampText);
        moneyText = (TextView) view.findViewById(R.id.moneyText);


        //spinnerCafe = (RelativeLayout) view.findViewById(R.id.spinnerCafe);

        formatter = new DecimalFormat("#,###,##0");


        smartOrderCV.setOnClickListener(this);
        complainCV.setOnClickListener(this);
        eStampCV.setOnClickListener(this);
        eCardCV.setOnClickListener(this);


        String num = PreferenceUtils.getNum(getActivity());




        String usID = PreferenceUtils.getId(getActivity());
        Log.i("usID", usID);
        try {
            String result = new UserInfoTask().execute(usID).get();

            if (!(result == null) || result.equals("") || result.equals("null")) {
                parseJson(result);
                String money = PreferenceUtils.getEMoney(getActivity());
                String name = PreferenceUtils.getName(getActivity()) + "님";
                money = formatter.format(Integer.parseInt(money)) + "원";
                String stamp = PreferenceUtils.getEStamp(getActivity()) + "/12";


                personName.setText(name);
                moneyText.setText(money);
                stampText.setText(stamp);


                if (mORDListener != null) {
                    mORDListener.onReceivedData(name);
                }
            } // result != null
        } catch (ExecutionException | JSONException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        return view;
    } //onCreateView 끝




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.smartOrderCV:
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(SmartOrder.newInstance());
                break;

            case R.id.complainCV:
                ((MainActivity)getActivity()).replaceFragment(ComplainMain.newInstance());
                break;

            case R.id.eStampCV:
                ((MainActivity)getActivity()).replaceFragment(EStampMain.newInstance());
                break;

            case R.id.eCardCV:
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(ECardMain.newInstance());
                break;

            /*case R.id.spinnerCafe:
            SpinnerCafe s = SpinnerCafe.getInstance();
            s.show(getActivity().getSupportFragmentManager(), SpinnerCafe.TAG_SPINNER_Cafe);
            break;*/
        }
    }


    private void parseJson(String json) throws JSONException, ExecutionException, InterruptedException {
        String TAG_JSON = "user";
        String TAG_NUM = "num";
        String TAG_NAME = "name";
        String TAG_MONEY = "money";
        String TAG_STAMP = "stamp";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);


        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String num = item.getString(TAG_NUM);
            String name = item.getString(TAG_NAME);
            String money = item.getString(TAG_MONEY);
            String stamp = item.getString(TAG_STAMP);


            PreferenceUtils.saveNum(num, getActivity()); //번호 저장.
            PreferenceUtils.saveName(name, getActivity()); //이름 저장.
            PreferenceUtils.saveEMoney(money, getActivity()); //이머니 저장.
            PreferenceUtils.saveEStamp(stamp, getActivity()); //이머니 저장.

        }

    } //parseJson 끝


    @SuppressLint("StaticFieldLeak")
    class UserInfoTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                //URL url = new URL("http://14.33.171.115:8080/CafeinProject/checkUser.jsp");
                URL url = new URL("http://cafein.freehost.kr/checkUser.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "usID="+strings[0];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }


} //class 끝