package com.example.cafeinadmin;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockManageEdit extends Fragment implements View.OnClickListener {



    TableLayout tableLayout;



    public StockManageEdit() {
        // Required empty public constructor
    }

    public static StockManageEdit newInstance() {
        return new StockManageEdit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_stock_manage_edit, container, false);
        setHasOptionsMenu(true);
        tableLayout = view.findViewById(R.id.stockManageTL);
        try {
            String result  = new StockManageJsonTask().execute().get();
            parseJson(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        tableLayout = view.findViewById(R.id.salesManageTL);



        return view;
    } //onCreateView 끝

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("재고관리");
        }


    } //Resume 끝





    private void parseJson(String json) throws JSONException {
        String TAG_JSON = "stock";
        String TAG_NUMBER = "num";
        String TAG_NAME = "name";
        String TAG_AMOUNT = "amnt";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for(int i=0; i < jsonArray.length(); i++) {
            String num = Integer.toString(i + 1);
            TableRow addTr = new TableRow(getActivity());
            TextView td1 = new TextView(getActivity());
            TextView td2 = new TextView(getActivity());
            TextView td3 = new TextView(getActivity());

            addTr.setWeightSum(9);

            td1.setGravity(Gravity.CENTER);
            td2.setGravity(Gravity.CENTER);
            td3.setGravity(Gravity.CENTER);


            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());

            td1.setLayoutParams(new TableRow.LayoutParams(
                    width, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
            td2.setLayoutParams(new TableRow.LayoutParams(
                    width, TableRow.LayoutParams.WRAP_CONTENT, 4.5f));
            td3.setLayoutParams(new TableRow.LayoutParams(
                    width, TableRow.LayoutParams.WRAP_CONTENT, 3f));


            td1.setBackgroundResource(R.drawable.table);
            td2.setBackgroundResource(R.drawable.table);
            td3.setBackgroundResource(R.drawable.table);


            td1.setPadding(10,10,10,10);
            td2.setPadding(10,10,10,10);
            td3.setPadding(10,10,10,10);



            td1.setTextSize(15);
            td2.setTextSize(15);
            td3.setTextSize(15);



            addTr.addView(td1);
            addTr.addView(td2);
            addTr.addView(td3);


            tableLayout.addView(addTr);


            JSONObject item = jsonArray.getJSONObject(i);
            String number = item.getString(TAG_NUMBER);
            String name = item.getString(TAG_NAME);
            int amount = item.getInt(TAG_AMOUNT);

            if (amount <= 400) {
                td3.setTextColor(Color.RED);
            }

            String stockAmnt = Integer.toString(amount);

            if (stockAmnt.equals("0")) {
                stockAmnt += " (품절)";
            }
            td1.setText(number);
            td2.setText(name);
            td3.setText(stockAmnt);
            //menuNum.setText(num);
            Log.i("숫자", i+"");

        }
    }

    @Override
    public void onClick(View v) {

    }

    class StockManageJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/stockmanage.jsp");
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

} //class 끝