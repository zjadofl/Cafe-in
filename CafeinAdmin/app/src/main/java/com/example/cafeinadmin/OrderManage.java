package com.example.cafeinadmin;


import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;



/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class OrderManage extends Fragment implements View.OnClickListener {


    ViewPager viewPager;
    ArrayList<Order> orders;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TextView no;
    TextView ocontent, orderText;
    String num, result,countAc,count,countRe ;
    Button orderCompleteBtn;
    private Context orderManage;

    public OrderManage() {
        // Required empty public constructor
    }

    public static OrderManage newInstance() {
        return new OrderManage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_manage, container, false);
        setHasOptionsMenu(true);
        orderManage=container.getContext();

        TextView acceptCount = view.findViewById(R.id.acceptCount);


        TextView refuseCount = view.findViewById(R.id.refuseCount);



        TextView totalCount = view.findViewById(R.id.allCount);

        orderCompleteBtn = view.findViewById(R.id.orderCompleteBtn);

        orderText = view.findViewById(R.id.orderText);
        no = (TextView) view.findViewById(R.id.orderNo);
        ocontent = (TextView) view.findViewById(R.id.orderListContent);
        acceptCount.setOnClickListener(this);
        refuseCount.setOnClickListener(this);
        totalCount.setOnClickListener(this);
        orderCompleteBtn.setOnClickListener(this);
        ImageButton bigBtn=view.findViewById(R.id.makeBigButton);
        bigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderExpand.class);
                intent.putExtra("orderno",no.getText().toString());
                intent.putExtra("ordercontent",ocontent.getText().toString());
                startActivity(intent);
            }
        });



        try {
            result = new OrderAppJsonTask().execute().get();
            countAc = new CountAcceptTask().execute().get();
            // Log.i("깔",countAc);
            acceptCount.setText(countAc+" 개");
            countRe = new CountRefuseTask().execute().get();
            count = new CountTask().execute().get();
            refuseCount.setText(countRe+" 개");
            totalCount.setText(count+" 개");
            Log.i("냐옹", result);
            orderJson(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    } //onCreateView 끝

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("주문관리");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acceptCount:
                Intent intent = new Intent(getActivity(), OrderManageApproval.class);
                startActivity(intent);
                break;

            case R.id.refuseCount:
                Intent intent2 = new Intent(getActivity(), OrderRefuseList.class);
                startActivity(intent2);
                break;

            case R.id.allCount:
                Intent intent4 = new Intent(getActivity(), TotalOrderList.class);
                startActivity(intent4);

                break;

            case R.id.orderCompleteBtn:
                complete();
                break;

        }

    }

    @SuppressLint("StaticFieldLeak")
    class OrderAppJsonTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/readOrder.jsp");
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


    public void orderJson(String json) throws JSONException {
        Log.i("왈왈", "dkjfkdj");
        String TAG_JSON = "order";
        String TAG_NUM = "num";
        String TAG_MENU = "menuName";
        String TAG_MN_AMNT = "mn_amnt";
        String TAG_CUP="cup";
        String TAG_SIZE="size";
        String TAG_TEM="tem";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            num = item.getString(TAG_NUM);
            String menu = item.getString(TAG_MENU);
            String mn_amnt = item.getString(TAG_MN_AMNT);
            String cup=item.getString(TAG_CUP);
            String size=item.getString(TAG_SIZE);
            String tem=item.getString(TAG_TEM);
            no.setText(num);
            Log.i("멍멍", num);
            Log.i("캉캉", menu);

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
            ocontent.setText(menu + " " + mn_amnt + "개"+"\n  -"+cup+"\n  -"+ size +"\n  -" + tem);

        }
    }

    public void complete() {
        // TODO Auto-generated method stub
        android.app.AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(orderManage);
        builder.setTitle("제조를 완료하시겠습니까?\n");
        builder.setMessage("완료버튼 누르기 전 꼼꼼히 확인하십시오.");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    orderJson(result);
                    num = no.getText().toString();
                    Log.i("complete num", num);
                    Log.i("complete result", result);
                    String obj = new OrderAppCompleteTask().execute(num).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(orderManage, "주문이 완료처리되었습니다.", Toast.LENGTH_SHORT).show();

            }
        }).setNegativeButton("취소", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @SuppressLint("StaticFieldLeak")
    class OrderAppCompleteTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/orderStatusComplete.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "num=" + strings[0];
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

    @SuppressLint("StaticFieldLeak")
    class CountAcceptTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/countAccept.jsp");
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
    @SuppressLint("StaticFieldLeak")
    class CountRefuseTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/countRefuse.jsp");
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

    class CountTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/count.jsp");
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










}//class 끝