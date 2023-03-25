package com.example.cafein;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComplainMain extends Fragment {

    private RecyclerView complainRV;
    private ArrayList<Complain> arrComplain;
    private LinearLayoutManager lm;
    ComplainAdapter complainAdapter;
    String userNum;

    public ComplainMain() {
        // Required empty public constructor
    }

    public static ComplainMain newInstance() {
        return new  ComplainMain();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complain_main, container, false);
        setHasOptionsMenu(true);

        complainRV = view.findViewById(R.id.complainRV);
        complainRV.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);

        complainRV.setLayoutManager(lm);
        arrComplain = new ArrayList<Complain>();
        complainAdapter = new ComplainAdapter(arrComplain, getActivity().getApplicationContext());
        complainRV.setAdapter(complainAdapter);

        userNum = PreferenceUtils.getNum(getActivity()); //사용자 정보 가져오기.

        try {
            String obj = new ComplainJsonTask().execute(userNum).get();
            Log.i("json", ""+obj);
            parseJson(obj);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("컴플레인");
        }

        activity.invalidateOptionsMenu();
    }

    //툴바에 menu item 추가.
    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.refresh_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.refresh:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;
            case R.id.add :
                Intent regComplainIntent = new Intent(getActivity(), ComplainWrite.class);
                startActivity(regComplainIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void parseJson(String json) throws JSONException {
        String TAG_JSON = "complain";
        String TAG_TITLE = "title";
        String TAG_TYPE = "type";
        String TAG_DATE = "date";
        String TAG_WRITE_DATE = "writeDate";
        String TAG_CONTENT = "content";
        String TAG_REPLY = "reply";
        String status = "미완료";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String title = item.getString(TAG_TITLE);
            String writeDate = item.getString(TAG_WRITE_DATE);
            String date = item.getString(TAG_DATE);
            String type = item.getString(TAG_TYPE);
            String content = item.getString(TAG_CONTENT);
            String reply = item.getString(TAG_REPLY);

            if (reply.equals("null")) {
                reply = "";
            }

            arrComplain.add(new Complain(title, writeDate, date, status, content, type, reply));
            //순서: 제목, 작성날짜, 컴플레인 날짜, 상태, 내용, 종류
        }


    }


    class ComplainJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/complainJson.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "userNum=" + strings[0];
                osw.write(sendMsg);
                osw.flush();
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }


    }



}
