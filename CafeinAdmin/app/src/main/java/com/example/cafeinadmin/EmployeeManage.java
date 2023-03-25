package com.example.cafeinadmin;



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeManage extends Fragment {

    private RecyclerView epManageRV;
    private ArrayList<EmployeeManageData> lstEp;
    private LinearLayoutManager lm;
    EmployeeAdapter employeeAdapter;


    public EmployeeManage() {
        // Required empty public constructor
    }

    public static EmployeeManage newInstance() {
        return new EmployeeManage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_manage, container, false);
        setHasOptionsMenu(true);

        epManageRV = view.findViewById(R.id.epManageRV);
        epManageRV.setHasFixedSize(true);

        lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        epManageRV.setLayoutManager(lm);

        lstEp = new ArrayList<>();

        employeeAdapter = new EmployeeAdapter(lstEp, getActivity().getApplicationContext());
        epManageRV.setAdapter(employeeAdapter);
        try {
            String obj = new epJsonTask().execute().get();
            Log.i("json", ""+obj);
            if (!(obj.equals("{}")) || !(obj == null)) {
                parseJson(obj);
            }
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
            ((MainActivity) activity).setActionBarTitle("직원관리");
        }
    }

    //툴바에 menu item 추가.
    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.refresh:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void parseJson(String json) throws JSONException, ExecutionException, InterruptedException {
        String TAG_JSON = "employee";
        String TAG_NUM = "num";
        String TAG_NAME = "name";
        String TAG_POS = "pos";
        String TAG_PART1 = "part1";
        String TAG_PART2 = "part2";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);


        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String num = item.getString(TAG_NUM);
            String name = item.getString(TAG_NAME);
            String pos = item.getString(TAG_POS);
            String part1 = item.getString(TAG_PART1);
            String part2 = item.getString(TAG_PART2);

            String time = part1 + "~" + part2;
            if (time.equals("~")) {
                time = "";
                lstEp.add(new EmployeeManageData(num, name, pos, time));
            } else {
                lstEp.add(new EmployeeManageData(num, name, pos, time));
            }



        }






    } //parseJson 끝


    class epJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/employeeManage.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
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
