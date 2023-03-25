package com.example.cafein;



import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderHistory extends Fragment implements View.OnClickListener  {

    TextView orderWriteDate;
    TextView orderWriteDate1;
    TextView orderWriteDate2;
    Button orderHistoryBtn;

    AlertDialog alertDialog;

    String odate, odate2, userNum;

    private RecyclerView orderRV;
    private ArrayList<OrderHistoryData> lstOrder;
    private LinearLayoutManager lm;
    OrderAdapter orderAdapter;

    SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
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

    public OrderHistory() {
        // Required empty public constructor
    }

    public static OrderHistory newInstance() {
        return new OrderHistory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        setHasOptionsMenu(true);

        orderWriteDate = (TextView) view.findViewById(R.id.orderWriteDate);
        orderWriteDate2 = (TextView) view.findViewById(R.id.orderWriteDate2);
        orderHistoryBtn = (Button) view.findViewById(R.id.orderHistoryBtn);

        orderRV = view.findViewById(R.id.orderRV);
        orderRV.setHasFixedSize(true);

        lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        orderRV.setLayoutManager(lm);

        lstOrder = new ArrayList<>();

        orderAdapter = new OrderAdapter(lstOrder, getActivity().getApplicationContext());
        orderRV.setAdapter(orderAdapter);

        //오늘 날짜.
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);

        userNum = PreferenceUtils.getNum(getActivity());
        orderWriteDate.setText(date_text);
        orderWriteDate2.setText(date_text);

        try {
            Log.i("date", date_text);
            Log.i("유저번호", userNum);
            String obj = new OrderUserTask().execute(userNum, date_text, date_text).get();
            Log.i("json", ""+obj);
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
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderWriteDate:


                DatePickerDialog datePickerDialog1 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                Date todayDate = new Date ();
                maxDate.setTime(todayDate);
                datePickerDialog1.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

                datePickerDialog1.show();

                break;

            case R.id.orderWriteDate2:
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                Toast.makeText(getActivity(), "갱신되었습니다.", Toast.LENGTH_LONG).show();
                String date1 = orderWriteDate.getText().toString().trim();
                String date2 = orderWriteDate2.getText().toString().trim();
                try {
                    String obj2 = new OrderUserTask().execute(userNum, date1, date2).get();
                    orderAdapter.notifyDataSetChanged();
                    lstOrder.clear();
                    parseJson(obj2);
                    Log.i("테스트1", obj2+userNum+date1+date2);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        } //switch 끝


    }


    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("주문내역");
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
        String TAG_JSON = "order";
        String TAG_NUM = "orderNum";
        String TAG_MNUM="menuNum";
        String TAG_MENUNAME = "menuName";
        String TAG_PRICE="price";
        String TAG_IMG="img";
        String TAG_TYPE="type";
        String TAG_ORDERDATE = "date";
        String TAG_AMOUNT = "amount";
        String TAG_ODPRICE = "odprice";
        String TAG_TEM = "tem";
        String TAG_SIZE = "size";
        String TAG_CUP = "cup";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);


        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            String num = item.getString(TAG_NUM);
            String menuNum=item.getString(TAG_MNUM);
            String menuName = item.getString(TAG_MENUNAME);
            String price=item.getString(TAG_PRICE);
            String img=item.getString(TAG_IMG);
            String type=item.getString(TAG_TYPE);
            String orderDate = item.getString(TAG_ORDERDATE);
            String amount = item.getString(TAG_AMOUNT)+" 개";
            String odprice = item.getString(TAG_ODPRICE);
            String size = item.getString(TAG_SIZE);
            String cup = item.getString(TAG_CUP);

            if (cup.equals("매")) {
                cup = "매장용컵";
            } else if (cup.equals("일")) {
                cup = "일회용컵";
            }


            String option = cup + "/" + size;


            lstOrder.add(new OrderHistoryData(num,menuNum, menuName,price,img,type,orderDate, amount, odprice, option));
        }


    } //parseJson 끝


    class OrderUserTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/userOrderList.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "userNum=" + strings[0] + "&sDate=" + strings[1] + "&eDate=" + strings[2];
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