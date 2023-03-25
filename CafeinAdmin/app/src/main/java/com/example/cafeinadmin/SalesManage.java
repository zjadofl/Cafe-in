package com.example.cafeinadmin;


import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class SalesManage extends Fragment implements View.OnClickListener {

    TextView salesDate, totalSales;
    String sDate;

    TableLayout tableLayout;

    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
    Calendar time = Calendar.getInstance();
    Calendar minDate = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();

    String filterDate = format1.format(time.getTime());

    public SalesManage() {
        // Required empty public constructor
    }

    public static SalesManage newInstance() {
        return new SalesManage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_manage, container, false);
        setHasOptionsMenu(true);
        salesDate = (TextView) view.findViewById(R.id.salesDate);
        salesDate.setOnClickListener(this);
        totalSales = (TextView) view.findViewById(R.id.totalSales);


        tableLayout = view.findViewById(R.id.salesManageTL);

        //텍스트가 변할 때.
        salesDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tableLayout.removeAllViewsInLayout();
                //tableLayout.setBackgroundResource(R.drawable.border);
                try {

                    String date = salesDate.getText().toString().trim();
                    String obj = new OrderManageJsonTask().execute(date).get();
                    Log.i("json", ""+obj);
                    Log.i("date는 무엇", date);
                    parseJson(obj);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    String date = salesDate.getText().toString().trim();
                    String obj = new totalSalesManageTask().execute(date).get();    //132번쨰 줄의 데이트 값?
                    Log.i("json", ""+obj);
                    parseTotalJson(obj);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }



            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    } //onCreateView 끝

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("매출관리");
        }


    } //Resume 끝

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.refresh:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.salesDate:
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        sDate = year + "-" + month + "-" + dayOfMonth;
                        salesDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);

                // 최대 날짜 설정.
                Date todayDate = new Date();
                maxDate.setTime(todayDate);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());


                datePickerDialog.show();


                break;
        }
    } //onClick 끝

    private void parseJson(String json) throws JSONException {
        String TAG_JSON = "sales";
        String TAG_DATE = "date";
        String TAG_NAME = "name";
        String TAG_AMOUNT = "amount";
        String TAG_PRICE = "price";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for(int i=0; i < jsonArray.length(); i++) {
            String num = Integer.toString(i + 1);
            TableRow addTr = new TableRow(getActivity());
            TextView td1 = new TextView(getActivity());
            TextView td2 = new TextView(getActivity());
            TextView td3 = new TextView(getActivity());
            TextView td4 = new TextView(getActivity());

            addTr.setWeightSum(12);

            td1.setGravity(Gravity.CENTER);
            td2.setGravity(Gravity.CENTER);
            td3.setGravity(Gravity.CENTER);
            td4.setGravity(Gravity.CENTER);

            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());

            td1.setLayoutParams(new TableRow.LayoutParams(
                    width, TableRow.LayoutParams.WRAP_CONTENT, 1.5f));
            td2.setLayoutParams(new TableRow.LayoutParams(
                    width, TableRow.LayoutParams.WRAP_CONTENT, 4.5f));
            td3.setLayoutParams(new TableRow.LayoutParams(
                    width, TableRow.LayoutParams.WRAP_CONTENT, 3f));
            td4.setLayoutParams(new TableRow.LayoutParams(
                    width, TableRow.LayoutParams.WRAP_CONTENT, 3f));

            td1.setBackgroundResource(R.drawable.table);
            td2.setBackgroundResource(R.drawable.table);
            td3.setBackgroundResource(R.drawable.table);
            td4.setBackgroundResource(R.drawable.table);

            td1.setPadding(10,10,10,10);
            td2.setPadding(10,10,10,10);
            td3.setPadding(10,10,10,10);
            td4.setPadding(10,10,10,10);

            td1.setTextSize(15);
            td2.setTextSize(15);
            td3.setTextSize(15);
            td4.setTextSize(15);


            addTr.addView(td1);
            addTr.addView(td2);
            addTr.addView(td3);
            addTr.addView(td4);

            tableLayout.addView(addTr);


            JSONObject item = jsonArray.getJSONObject(i);
            String date = item.getString(TAG_DATE);
            String name = item.getString(TAG_NAME);
            String sum = item.getString(TAG_AMOUNT);
            String price = item.getString(TAG_PRICE);



            td1.setText(num);
            td2.setText(name);
            td3.setText(sum);
            td4.setText(price);
            //menuNum.setText(num);
            Log.i("숫자", i+"");

        }
    }

    class OrderManageJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/salesManageJSON.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "date=" + strings[0];
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

    private void parseTotalJson(String json) throws JSONException {
        String TAG_JSON = "total";
        String TAG_DATE = "date";
        String TAG_PRICE = "price";
        String date = null;
        String price = null;

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            date = item.getString(TAG_DATE);
            price = item.getString(TAG_PRICE);

        }
        totalSales.setText(date + "의 총 매출액은" + price + "입니다." );
    }

    class totalSalesManageTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/totalSalesManageJSON.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "date=" + strings[0];
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

} //class 끝