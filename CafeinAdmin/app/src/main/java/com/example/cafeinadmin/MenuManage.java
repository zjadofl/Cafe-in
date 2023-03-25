package com.example.cafeinadmin;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuManage extends Fragment {

    private RecyclerView recyclerMenu;
    private ArrayList<Menu> arrayList;
    private LinearLayoutManager linearLayoutManager;
    MenuAdapter menuAdapter;


    public MenuManage() {
        // Required empty public constructor
    }

    public static MenuManage newInstance() {
        return new MenuManage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_manage, container, false);
        setHasOptionsMenu(true);

        recyclerMenu = view.findViewById(R.id.recyclerMenu);
        recyclerMenu.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMenu.setLayoutManager(linearLayoutManager);

        arrayList= new ArrayList<Menu>();

        menuAdapter = new MenuAdapter(arrayList, getActivity().getApplicationContext());
        recyclerMenu.setAdapter(menuAdapter);
        try {
            String obj = new MenuManageJsonTask().execute().get();
            Log.i("json", ""+obj);
            parseMenuJson(obj);
        } catch (ExecutionException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("메뉴관리");
        }



        activity.invalidateOptionsMenu();
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

    private void parseMenuJson(String json) throws JSONException {
        String TAG_JSON = "menu";
        String TAG_NAME = "name";
        String TAG_PRICE = "price";
        String TAG_TYPE = "type";
        String TAG_IMG="image";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String name = item.getString(TAG_NAME);
            String price = item.getString(TAG_PRICE);
            String img=item.getString(TAG_IMG);

            arrayList.add(new Menu(name, price,img));
        }
    }




    class MenuManageJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/menuManageJSON.jsp");
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