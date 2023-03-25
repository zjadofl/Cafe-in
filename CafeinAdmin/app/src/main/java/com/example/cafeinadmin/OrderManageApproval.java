package com.example.cafeinadmin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class OrderManageApproval extends AppCompatActivity {

    /* 나중에 지우세요.
     * 해야할 일:
     * 1) '오늘'을 기준으로 들어온 주문만 띄워주세요.
     * 2) 동적으로 생성하기.
     * */

    TableLayout menuARTable;
    AlertDialog alertDialog;
    Handler handler;
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage_approval);


        menuARTable = (TableLayout) findViewById(R.id.menuARTable);


        /*handler = new Handler();
        refresh = new Runnable() {
            public void run() {
                // Do something
                handler.postDelayed(refresh, 5000);
                Toast.makeText(OrderManageApproval.this, "test", Toast.LENGTH_LONG).show();
            }
        };
        handler.post(refresh);*/

        //임시


        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        setTitle("주문승인/거절");

        Button allOk = (Button) findViewById(R.id.allOk);
        Button allRefuse = (Button) findViewById(R.id.allRefuse);
        allOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(OrderManageApproval.this);
                builder.setTitle("전체주문을 승인하시겠습니까?\n");
                builder.setMessage("승인 후 빠른 제조 요망!");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            String obj2 = new GetOrderNumTask().execute().get();
                            String TAG_JSON = "order";
                            String TAG_NUM = "num";

                            JSONObject jsonObject = new JSONObject(obj2);
                            String jsonStr = jsonObject.getString(TAG_JSON);
                            JSONArray jsonArray = new JSONArray(jsonStr);
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject item = jsonArray.getJSONObject(j);
                                String num = item.getString(TAG_NUM);
                                String obj1 = new AllRLStuckTask().execute(num).get();
                                String obj4 = new EstampTask().execute(num).get();
                            }


                            String obj3 = new OrderAppAllOkTask().execute().get();
                            Toast.makeText(OrderManageApproval.this, "주문이 승인되었습니다.", Toast.LENGTH_SHORT).show();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).setNegativeButton("취소", null);

                alertDialog = builder.create();
                alertDialog.show();

            }
        });
        allRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderAllRefusePop customDialog = new OrderAllRefusePop(OrderManageApproval.this);
                customDialog.callFunction();

            }
        });


        try {
            String result = new OrderARJsonTask().execute().get();
            parseOrderJson(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    } //onCreate 끝



    //툴바에 menu item 추가.
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                break;
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void parseOrderJson(String json) throws JSONException, ParseException {
        String TAG_JSON = "order";
        String TAG_NUM = "num";
        String TAG_USERID = "userId";
        String TAG_MNAME = "menuName";
        String TAG_date = "date";
        String TAG_cup = "cup";
        String TAG_tem = "tem";
        String TAG_size = "size";

        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            final String num = item.getString(TAG_NUM);
            String userID = item.getString(TAG_USERID);
            String menuName = item.getString(TAG_MNAME);
            String date = item.getString(TAG_date);
            String cup = item.getString(TAG_cup);
            String tem = item.getString(TAG_tem);
            String size = item.getString(TAG_size);


            //임시 코딩
            //테이블 row에 width, height, 가중치 정하기.
            TableRow.LayoutParams params1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
            params1.weight = 1.0f;
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
            params2.weight = 2.0f;
            TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
            params3.weight = 2.5f;

            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);


            //생성.
            TableRow addTR = new TableRow(this); //row 추가
            TextView td1 = new TextView(this);
            TextView td2 = new TextView(this);
            TextView td3 = new TextView(this);
            LinearLayout td4 = new LinearLayout(this);
            final TextView aBtn = new TextView(this); //승인 버튼

            final TextView rBtn = new TextView(this); //거절 버튼

            aBtn.setLayoutParams(lp);
            rBtn.setLayoutParams(lp);

            aBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(OrderManageApproval.this);
                    builder.setTitle("주문을 승인하시겠습니까?\n");
                    builder.setMessage("승인 후 빠른 제조 요망!");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                String obj = new OrderAppOkTask().execute(Integer.toString(aBtn.getId())).get();
                                String obj1 = new UpdateStockTask().execute(num).get();
                                String obj4 = new EstampTask().execute(num).get();
                                Toast.makeText(OrderManageApproval.this, "전체주문이 승인되었습니다.", Toast.LENGTH_SHORT).show();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }).setNegativeButton("취소", null);

                    alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            rBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderRefusePop customDialog = new OrderRefusePop(OrderManageApproval.this);
                    customDialog.callFunction();
                    String id = Integer.toString(rBtn.getId());
                    OrderRefusePop.setRefuseId(id);
                }
            });


            //margin 설정
            lp.setMargins(6, 6, 6, 6);

            td4.addView(aBtn);
            td4.addView(rBtn);

            //param 설정.
            td1.setLayoutParams(params1);
            td2.setLayoutParams(params2);
            td3.setLayoutParams(params2);
            td4.setLayoutParams(params3);

            //방향 설정.
            td4.setOrientation(LinearLayout.HORIZONTAL);

            //글자 삽입.
            td1.setText(num);
            td2.setText(menuName);
            td3.setText(cup+"/"+tem+"/"+size);
            aBtn.setText("승인");
            rBtn.setText("거절");
            aBtn.setId(Integer.parseInt(num));
            rBtn.setId(Integer.parseInt(num));

            //글자가 넘쳤을 때 ...로 생략.
            td1.setMaxLines(1);
            td1.setEllipsize(TextUtils.TruncateAt.END);
            td2.setMaxLines(1);
            td2.setEllipsize(TextUtils.TruncateAt.END);
            td3.setMaxLines(1);
            td3.setEllipsize(TextUtils.TruncateAt.END);

            //글자 크기.
            td1.setTextSize(15);
            td2.setTextSize(15);
            td3.setTextSize(15);

            //정렬 : 가운데.
            td1.setGravity(Gravity.CENTER);
            td2.setGravity(Gravity.CENTER);
            td3.setGravity(Gravity.CENTER);
            td4.setGravity(Gravity.CENTER);

            //배경을 테이블로 설정.
            td1.setBackgroundResource(R.drawable.table);
            td2.setBackgroundResource(R.drawable.table);
            td3.setBackgroundResource(R.drawable.table);
            td4.setBackgroundResource(R.drawable.table);
            aBtn.setBackgroundResource(R.drawable.rounded_corner_photo3);
            rBtn.setBackgroundResource(R.drawable.rounded_corner_photo3);


            addTR.addView(td1); //tableRow에 Text 넣기.
            addTR.addView(td2);
            addTR.addView(td3);
            addTR.addView(td4);

            menuARTable.addView(addTR);


        } //for 끝


    }




    class OrderARJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/orderARJSON.jsp");
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
    class OrderAppOkTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/orderStatusOk.jsp");
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
    class OrderAppAllOkTask extends AsyncTask<String, String, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/orderStatusAllOk.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
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

    class UpdateStockTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/updatestock.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "odNum=" + strings[0];
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


    class AllRLStuckTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/allRLStuck.jsp");
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

    class GetOrderNumTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/getOrderNumTask.jsp");
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

    class EstampTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/addEstamp.jsp");
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



}







