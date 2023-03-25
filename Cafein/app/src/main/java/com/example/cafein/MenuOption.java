package com.example.cafein;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

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
import java.util.Iterator;

public class MenuOption extends AppCompatActivity {

    private RecyclerView recycleOption;
    private ArrayList<Option> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option);

        recycleOption = (RecyclerView) findViewById(R.id.recycleOption);

        //각 Item들이 RecyclerView의 전체 크기를 변경될 가능성이 없다면 true
        recycleOption.setHasFixedSize(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleOption.setLayoutManager(layoutManager);

        arrayList = new ArrayList<Option>();

        recycleOption.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        OptionJsonTask optionJsonTask = new OptionJsonTask();
        optionJsonTask.execute();
    }

    public class OptionJsonTask extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        String fullfile;
        String optionName;

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://api.myjson.com/bins/7byq5");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                fullfile = stringBuffer.toString();

                JSONObject jsonObject = new JSONObject(fullfile);
                JSONObject jsonObject1youtube = jsonObject.getJSONObject("option");
                for(Iterator key = jsonObject1youtube.keys(); key.hasNext();) {
                    JSONObject child = (JSONObject) jsonObject1youtube.get((String) key.next());
                    optionName = child.getString("name");

                    arrayList.add(new Option(optionName));

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            OptionAdapter optionAdapter = new OptionAdapter(arrayList, getApplicationContext());
            recycleOption.setAdapter(optionAdapter);


        }
    }

}
