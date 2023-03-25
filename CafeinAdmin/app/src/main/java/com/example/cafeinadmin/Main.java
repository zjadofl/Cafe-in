package com.example.cafeinadmin;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Objects;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main extends Fragment implements View.OnClickListener {

    CardView orderManageCV, menuManageCV, saleManageCV, csManageCV, stockManageCV, employeeManageCV;

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
        View view = inflater.inflate(R.layout.fragment_main, null);



        orderManageCV = (CardView) view.findViewById(R.id.orderManageCV);
        menuManageCV = (CardView) view.findViewById(R.id.menuManageCV);
        saleManageCV = (CardView) view.findViewById(R.id.saleManageCV);
        csManageCV = (CardView) view.findViewById(R.id.csManageCV);
        stockManageCV = (CardView) view.findViewById(R.id.stockManageCV);
        employeeManageCV = (CardView) view.findViewById(R.id.employeeManageCV);

        String usID = PreferenceUtils.getId(getActivity());

        try {
            String result  = new UserInfoTask().execute(usID).get();
            if (result != null || result.equals("")) {
                parseJson(result);
                String name = PreferenceUtils.getName(getActivity()) + "님";

                if (mORDListener != null) {
                    mORDListener.onReceivedData(name);
                }
            }
        } catch (ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        orderManageCV.setOnClickListener(this);
        menuManageCV.setOnClickListener(this);
        saleManageCV.setOnClickListener(this);
        csManageCV.setOnClickListener(this);
        stockManageCV.setOnClickListener(this);
        employeeManageCV.setOnClickListener(this);
        return view;
    } //onCreateView 끝




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderManageCV:
                ((MainActivity) getActivity()).replaceFragment(OrderManage.newInstance());

                break;
            case R.id.menuManageCV:
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(MenuManage.newInstance());
                break;
            case R.id.saleManageCV:
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(SalesManage.newInstance());
                break;
            case R.id.csManageCV:
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(CSManage.newInstance());
                break;
            case R.id.stockManageCV:
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(StockManageEdit.newInstance());
                break;
            case R.id.employeeManageCV:
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(EmployeeManage.newInstance());
                break;
        }
    }


    private void parseJson(String json) throws JSONException, ExecutionException, InterruptedException {
        String TAG_JSON = "employee";
        String TAG_NUM = "num";
        String TAG_NAME = "name";
        String TAG_POS = "pos";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);


        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String num = item.getString(TAG_NUM);
            String name = item.getString(TAG_NAME);
            String pos = item.getString(TAG_POS);

            PreferenceUtils.saveNum(num, getActivity()); //번호 저장.
            PreferenceUtils.saveName(name, getActivity()); //이름 저장.
            PreferenceUtils.savePos(pos, getActivity()); //직급 저장.

        }

    } //parseJson 끝




    @SuppressLint("StaticFieldLeak")
    class UserInfoTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/checkEmployee.jsp");
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
