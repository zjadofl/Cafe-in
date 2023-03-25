package com.example.cafeinadmin;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class TotalOrderList extends AppCompatActivity implements View.OnClickListener {

    TextView orderWriteDate;
    TextView orderWriteDate1;
    TextView orderWriteDate2;
    Button orderHistoryBtn;
    Spinner OrderTypeSpinner;

    AlertDialog alertDialog;

    String odate, odate2, status;

    private RecyclerView TotalOrderListRV;
    private ArrayList<TotalOrderListData> lstTotalOrderList;
    private LinearLayoutManager lm;
    TotalOrderListAdapter TotalOrderListAdapter;

    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
    Calendar time = Calendar.getInstance();
    Calendar minDate = Calendar.getInstance();
    Calendar maxDate = Calendar.getInstance();

    Calendar time2 = Calendar.getInstance();
    Calendar minDate2 = Calendar.getInstance();
    Calendar maxDate2 = Calendar.getInstance();

    String orderWriteDate3 = format1.format(time.getTime());
    String orderWriteDate4 = format1.format(time.getTime());

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_order_list);

        //getSupportActionBar().setDisplayShowTitleEnabled(true);


        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        setTitle("총주문목록");

        orderWriteDate = (TextView) findViewById(R.id.orderWriteDate);
        orderWriteDate2 = (TextView) findViewById(R.id.orderWriteDate2);
        orderHistoryBtn = (Button) findViewById(R.id.orderHistoryBtn);
        OrderTypeSpinner = (Spinner)findViewById(R.id.OrderTypeSpinner);

        TotalOrderListRV = findViewById(R.id.TotalOrderListRV);
        TotalOrderListRV.setHasFixedSize(true);

        lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        TotalOrderListRV.setLayoutManager(lm);

        lstTotalOrderList = new ArrayList<>();

        TotalOrderListAdapter = new TotalOrderListAdapter(lstTotalOrderList, this.getApplicationContext());
        TotalOrderListRV.setAdapter(TotalOrderListAdapter);

        //오늘 날짜.
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);


        orderWriteDate.setText(date_text);
        orderWriteDate2.setText(date_text);

        try {
            Log.i("date", date_text);
            String obj = new OrderListTask().execute(date_text, date_text).get();
            Log.i("json", "" + obj);
            parseJson(obj);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        orderWriteDate.setOnClickListener(this);
        orderWriteDate2.setOnClickListener(this);
        orderHistoryBtn.setOnClickListener(this);

        OrderTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*startActivity(new Intent(this, OrderManage.class));
        finish();*/

    }

    //툴바에 menu item 추가.
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderWriteDate:


                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        odate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date oneDay = formatDate1.parse(odate);
                            odate = formatDate1.format(oneDay);
                            orderWriteDate.setText(odate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, year, month + 1, day);


                // 최대 날짜 설정.
                Date todayDate = new Date();
                maxDate.setTime(todayDate);
                datePickerDialog1.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

                datePickerDialog1.show();

                break;

            case R.id.orderWriteDate2:
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        odate2 = year + "-" + (month + 1) + "-" + dayOfMonth;
                        SimpleDateFormat formatDate2 = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date oneDay = formatDate2.parse(odate2);
                            odate2 = formatDate2.format(oneDay);
                            orderWriteDate2.setText(odate2);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month + 1, day);


                // 최대 날짜 설정.
                Date todayDate2 = new Date();
                maxDate2.setTime(todayDate2);
                datePickerDialog2.getDatePicker().setMaxDate(maxDate2.getTimeInMillis());

                datePickerDialog2.show();

                break;

            case R.id.orderHistoryBtn:
                Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
                String date1 = orderWriteDate.getText().toString().trim();
                String date2 = orderWriteDate2.getText().toString().trim();
                String getType = OrderTypeSpinner.getSelectedItem().toString();
                Log.i("바뀜", getType);
                /*try {
                    if (getType.equals("전체")) {
                        String obj2 = new OrderListTask().execute(date1, date2).get();
                        //Log.i("값 갖고 오기", obj2);
                        parseJson(obj2);
                        TotalOrderListAdapter.notifyDataSetChanged();
                        lstTotalOrderList.clear();
                        Log.i("테스트1", obj2 + date1 + date2);
                    }
                    else if (getType.equals("요청")){
                        String obj3 = new totalOrderTask().execute(date1, date2, status).get();
                        //lstTotalOrderList.clear();
                        parseJson(obj3);
                    }
                    else if (getType.equals("거부")){
                        String obj4 = new totalOrderTask().execute(date1, date2, status).get();
                        //lstTotalOrderList.clear();
                        parseJson(obj4);
                    }
                    else if (getType.equals("승인")) {
                        String obj5 = new totalOrderTask().execute(date1, date2, status).get();
                        //lstTotalOrderList.clear();
                        parseJson(obj5);
                    }
                    else if (getType.equals("완료")) {
                        String obj5 = new totalOrderTask().execute(date1, date2, status).get();
                        //lstTotalOrderList.clear();
                        parseJson(obj5);
                    }


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

                try {
                    if (getType.equals("전체")) {
                        String obj2 = new OrderListTask().execute(date1, date2).get();
                        TotalOrderListAdapter.notifyDataSetChanged();
                        lstTotalOrderList.clear();
                        parseJson(obj2);
                        Log.i("테스트1", obj2+date1+date2);
                    } else if (getType.equals("요청")) {
                        status = "1";
                        String obj3 = new totalOrderTask().execute(date1, date2, status).get();
                        TotalOrderListAdapter.notifyDataSetChanged();
                        lstTotalOrderList.clear();
                        parseJson(obj3);
                    } else if (getType.equals("거부")){
                        status = "2";
                        String obj4 = new totalOrderTask().execute(date1, date2, status).get();
                        TotalOrderListAdapter.notifyDataSetChanged();
                        lstTotalOrderList.clear();
                        parseJson(obj4);
                    }
                    else if (getType.equals("승인")) {
                        status = "3";
                        String obj5 = new totalOrderTask().execute(date1, date2, status).get();
                        TotalOrderListAdapter.notifyDataSetChanged();
                        lstTotalOrderList.clear();
                        parseJson(obj5);
                    }
                    else if (getType.equals("완료")) {
                        status = "4";
                        String obj6 = new totalOrderTask().execute(date1, date2, status).get();
                        TotalOrderListAdapter.notifyDataSetChanged();
                        lstTotalOrderList.clear();
                        parseJson(obj6);
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        } //switch 끝

    }

    private void parseJson(String json) throws JSONException, ExecutionException, InterruptedException {
        String TAG_JSON = "order";
        String TAG_NUM = "num";
        String TAG_PRICE = "price";
        String TAG_STATUS = "status";
        String TAG_TEM = "tem";
        String TAG_SIZE = "size";
        String TAG_CUP = "cup";
        String TAG_ORDERDATE = "date";
        String TAG_MENUNAME = "menuName";
        String TAG_AMOUNT = "amount";


        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String num = item.getString(TAG_NUM);
            String menuName = item.getString(TAG_MENUNAME);
            String tem = item.getString(TAG_TEM);
            String size = item.getString(TAG_SIZE);
            String cup = item.getString(TAG_CUP);
            String orderDate = item.getString(TAG_ORDERDATE);
            String amount = item.getString(TAG_AMOUNT) + "개";
            String status = item.getString(TAG_STATUS);
            String price = item.getString(TAG_PRICE) + "원";

            if (tem.equals("I")) {
                tem = "ICE";
            } else if (tem.equals("H")) {
                tem = "HOT";
            }

            if (cup.equals("매")) {
                cup = "매장용컵";
            } else if (cup.equals("일")) {
                cup = "일회용컵";
            }


            String option = cup + "/" + size + "/" + tem;

            Log.i("들어왔엉", "tEST");
            lstTotalOrderList.add(new TotalOrderListData(num, menuName, option, orderDate, amount, status, price));
        }


    } //parseJson 끝


    class OrderListTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/totalOrder.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "sDate=" + strings[0] + "&eDate=" + strings[1];
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

    class totalOrderTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/totalOrdertwo.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "sDate=" + strings[0] + "&eDate=" + strings[1] + "&status=" + strings[2];
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