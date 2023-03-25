package com.example.cafeinadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

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
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BasicMenuInfo extends AppCompatActivity implements View.OnClickListener{

    ImageView stepClose;
    RadioGroup sizeRadio;
    RelativeLayout sizeRelative;
    EditText size1Edit, size2Edit, size3Edit, op1, op2, op3, ingre1, ingre2, ingre3;
    Button optionAdd, optionAdd2, regBtn;
    TableLayout addTable, addTable2;

    List<EditText> allEdit, allEdit2, sizeEdit;
    String sizeYesNo, orginJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_menu_info);

        allEdit = new ArrayList<EditText>();
        allEdit2 = new ArrayList<EditText>();
        sizeEdit = new ArrayList<>();

        stepClose = (ImageView) findViewById(R.id.stepClose);

        sizeRadio = (RadioGroup) findViewById(R.id.sizeRadio);

        sizeRelative = (RelativeLayout) findViewById(R.id.sizeRelative);

        addTable = (TableLayout) findViewById(R.id.addTable);
        addTable2 = (TableLayout) findViewById(R.id.addTable2);

        size1Edit = (EditText) findViewById(R.id.size1Edit);
        size2Edit = (EditText) findViewById(R.id.size2Edit);
        size3Edit = (EditText) findViewById(R.id.size3Edit);
        op2 = (EditText) findViewById(R.id.op2);
        op1 = (EditText) findViewById(R.id.op1);
        op3 = (EditText) findViewById(R.id.op3);
        ingre1 = (EditText) findViewById(R.id.ingre1);
        ingre2 = (EditText) findViewById(R.id.ingre2);
        ingre3 = (EditText) findViewById(R.id.ingre3);

        optionAdd = (Button) findViewById(R.id.optionAdd);
        optionAdd2 = (Button) findViewById(R.id.optionAdd2);
        regBtn = (Button) findViewById(R.id.regBtn);

        sizeYesNo = "s";

        allEdit.add(op1);
        allEdit.add(op2);
        allEdit.add(op3);

        allEdit2.add(ingre1);
        allEdit2.add(ingre2);
        allEdit2.add(ingre3);

        sizeEdit.add(size1Edit);
        sizeEdit.add(size2Edit);
        sizeEdit.add(size3Edit);

        sizeRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.sameSize) {
                    sizeYesNo = "s";
                    sizeRelative.setVisibility(View.GONE);

                } else if(checkedId == R.id.differSize) {
                    sizeYesNo = "d";
                    sizeRelative.setVisibility(View.VISIBLE);
                }
            }
        });


        //이벤트 리스너
        stepClose.setOnClickListener(this);
        optionAdd.setOnClickListener(this);
        optionAdd2.setOnClickListener(this);
        regBtn.setOnClickListener(this);

        try {
            orginJson = new GetBasicMenuInfoTask().execute().get();
            if (orginJson != null) {
                showSetting(orginJson);
            }
            Log.i("json을 받았다", orginJson);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    } //onCreate 끝

    //이벤트 리스너
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stepClose:
                BasicMenuInfo.this.finish();
                break;

            case R.id.optionAdd:
                int editID = View.generateViewId ();
                final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
                TableRow addTr = new TableRow(this); //row 추가
                EditText td1 = new EditText(this); //edit 추가
                EditText td2 = new EditText(this); //edit 추가
                EditText td3 = new EditText(this); //edit 추가

                td1.setId(editID);
                td2.setId(editID);
                td3.setId(editID);

                allEdit.add(td1); //배열에 넣기.
                allEdit.add(td2);
                allEdit.add(td3);

                td1.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                td2.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                td3.setBackgroundResource(R.drawable.table);

                td1.setGravity(Gravity.CENTER);
                td2.setGravity(Gravity.CENTER);
                td3.setGravity(Gravity.CENTER);

                TableRow.LayoutParams params = new TableRow.LayoutParams(width, TableRow.LayoutParams.MATCH_PARENT, 1f);

                td1.setLayoutParams(params);
                td2.setLayoutParams(params); //edit 추가
                td3.setLayoutParams(params); //edit 추가

                addTr.addView(td1); //tableRow에 edit 넣기.
                addTr.addView(td2); //tableRow에 edit 넣기.
                addTr.addView(td3); //tableRow에 edit 넣기.

                addTable.addView(addTr);


                break;

            case R.id.optionAdd2:
                int editID2 = View.generateViewId ();
                final int width2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
                TableRow addTr2 = new TableRow(this); //row 추가
                EditText td1two = new EditText(this); //edit 추가
                EditText td2two = new EditText(this); //edit 추가
                EditText td3two = new EditText(this); //edit 추가

                td1two.setId(editID2);
                td2two.setId(editID2);
                td3two.setId(editID2);

                allEdit2.add(td1two); //배열에 넣기.
                allEdit2.add(td2two);
                allEdit2.add(td3two);

                td1two.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                td2two.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                td3two.setBackgroundResource(R.drawable.table);

                td1two.setGravity(Gravity.CENTER);
                td2two.setGravity(Gravity.CENTER);
                td3two.setGravity(Gravity.CENTER);

                TableRow.LayoutParams params2 = new TableRow.LayoutParams(width2, TableRow.LayoutParams.MATCH_PARENT, 1f);

                td1two.setLayoutParams(params2);
                td2two.setLayoutParams(params2); //edit 추가
                td3two.setLayoutParams(params2); //edit 추가

                addTr2.addView(td1two); //tableRow에 edit 넣기.
                addTr2.addView(td2two); //tableRow에 edit 넣기.
                addTr2.addView(td3two); //tableRow에 edit 넣기.

                addTable2.addView(addTr2);
                break;

            case R.id.regBtn:
                String json = sendMenuInfo();
                if (sizeYesNo.equals("s") || (sizeYesNo.equals("d") && checkSize())) {
                    try {
                        String returns = new BasicMenuInfoTask().execute(json).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent regIntent = new Intent(this, MenuRegister.class);
                    startActivity(regIntent);
                    BasicMenuInfo.this.finish();
                }
                break;
        }
    } //onClick 끝

    //json 파일을 서버로 보내는 메소드
    private String sendMenuInfo() {
        String size1 = size1Edit.getText().toString().trim();
        String size2 = size2Edit.getText().toString().trim();
        String size3 = size3Edit.getText().toString().trim();

        //String[] optionArr = new String[allEdit.size()];
        ArrayList<String> optionArr = new ArrayList<>(allEdit.size());
        //String[] ingreArr = new String[allEdit2.size()];
        ArrayList<String> ingreArr = new ArrayList<>(allEdit2.size());

        ArrayList<String> sizeArr = new ArrayList<>(sizeEdit.size());

        JSONObject jsonObject = new JSONObject();
        JSONArray menuSettingArray = new JSONArray();
        JSONObject infoObject = new JSONObject();

        JSONArray optionArray = new JSONArray();
        JSONObject optionObj = new JSONObject();

        JSONArray ingreArray = new JSONArray();
        JSONObject ingreObj = new JSONObject();

        try {
            infoObject.put("size", sizeYesNo);

            if (sizeYesNo.equals("d")) {
                for(int i=0; i < sizeEdit.size(); i++) {
                    sizeArr.add(sizeEdit.get(i).getText().toString()); //배열에 추가.
                    String value = sizeArr.get(i);

                    infoObject.put("size"+(i+1), value);
                }
            }

            //옵션
            for(int i=0; i < allEdit.size(); i++) {
                optionArr.add(allEdit.get(i).getText().toString()); //배열에 추가.
                String value = optionArr.get(i);


                if (i%3==0) {
                    optionObj.put("name", value);
                    Log.i("옵션1", optionObj.toString());
                } else if (i%3==1) {
                    optionObj.put("price", value);
                    Log.i("옵션2", optionObj.toString());
                } else if (i%3==2) {
                    optionObj.put("interest", value);
                    Log.i("옵션3", optionObj.toString());
                    optionArray.put(optionObj); //array에 추가.
                    Log.i("옵션 어레이", optionArray.toString());
                    optionObj = new JSONObject();
                }
            }

            infoObject.put("option", optionArray);


            //재료
            for(int i=0; i < allEdit2.size(); i++) {
                ingreArr.add(allEdit2.get(i).getText().toString()); //배열에 추가.
                String value = ingreArr.get(i);

                if (i%3==0) {
                    ingreObj.put("name", value);
                } else if (i%3==1) {
                    ingreObj.put("price", value);
                } else if (i%3==2) {
                    ingreObj.put("interest", value);
                    ingreArray.put(ingreObj); //array에 추가.
                    //Log.i("재료 어레이", ingreArray.toString());
                    ingreObj = new JSONObject();
                }
            }

            infoObject.put("ingredient", ingreArray);

           // menuSettingArray.put(infoObject);
            //jsonObject.put("menuSetting", menuSettingArray);

            Log.i("Test:",infoObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infoObject.toString();
    }

    //기존 기본 메뉴 설정대로 지정.
    private void showSetting(String json) {
        String size, option, ingredient;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String menuStr = jsonObject.getString("카페드림");
            JSONArray menuArr = new JSONArray(menuStr);
            for(int i=0; i < menuArr.length(); i++) {
                JSONObject menuObj = menuArr.getJSONObject(i);
                size = menuObj.getString("size");
                if (size.equals("s")) {
                    sizeRadio.check(R.id.sameSize);
                } else if (size.equals("d")) {
                    sizeRadio.check(R.id.differSize);
                    String size1 =  menuObj.getString("size1");
                    String size2 =  menuObj.getString("size2");
                    String size3 =  menuObj.getString("size3");

                    size1Edit.setText(size1);
                    size2Edit.setText(size2);
                    size3Edit.setText(size3);
                }

                //옵션
                String optionStr =  menuObj.getString("option");
                JSONArray optionArr = new JSONArray(optionStr);
                for(int j=0; j < optionArr.length(); j++) {
                    JSONObject optionObj = optionArr.getJSONObject(j);
                    String name = optionObj.getString("name");
                    String price = optionObj.getString("price");
                    String interest = optionObj.getString("interest");
                    Log.i("j의숫자", j+"");
                    if (j == 0) {
                        op1.setText(name);
                        op2.setText(price);
                        op3.setText(interest);
                    } else {
                        final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
                        TableRow addTr = new TableRow(this); //row 추가
                        EditText td1 = new EditText(this); //edit 추가
                        EditText td2 = new EditText(this); //edit 추가
                        EditText td3 = new EditText(this); //edit 추가

                        //setText(값지정)
                        td1.setText(name);
                        td2.setText(price);
                        td3.setText(interest);

                        allEdit.add(td1); //배열에 넣기.
                        allEdit.add(td2);
                        allEdit.add(td3);


                        Log.i("옵션이름", "j: " + j + ", name : " + name);
                        Log.i("옵션가격", "j: " + j + ", price : " + price);

                        td1.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                        td2.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                        td3.setBackgroundResource(R.drawable.table);

                        td1.setGravity(Gravity.CENTER);
                        td2.setGravity(Gravity.CENTER);
                        td3.setGravity(Gravity.CENTER);

                        TableRow.LayoutParams params = new TableRow.LayoutParams(width, TableRow.LayoutParams.MATCH_PARENT, 1f);

                        td1.setLayoutParams(params);
                        td2.setLayoutParams(params); //edit 추가
                        td3.setLayoutParams(params); //edit 추가

                        addTr.addView(td1); //tableRow에 edit 넣기.
                        addTr.addView(td2); //tableRow에 edit 넣기.
                        addTr.addView(td3); //tableRow에 edit 넣기.

                        addTable.addView(addTr);

                    }
                } //for문 두번째 (j)


                //재료
                String ingreStr =  menuObj.getString("ingredient");
                JSONArray ingreArr = new JSONArray(ingreStr);
                for(int k=0; k < ingreArr.length(); k++) {
                    JSONObject ingreObj = ingreArr.getJSONObject(k);
                    String name = ingreObj.getString("name");
                    String price = ingreObj.getString("price");
                    String interest = ingreObj.getString("interest");

                    if (k==0) {
                        ingre1.setText(name);
                        ingre2.setText(price);
                        ingre3.setText(interest);

                        Log.i("재료이름", "k: " + k + ", name : " + name);
                        Log.i("재료가격", "k: " + k + ", price : " + price);
                    } else {
                        final int width2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
                        TableRow addTr2 = new TableRow(this); //row 추가
                        EditText td1two = new EditText(this); //edit 추가
                        EditText td2two = new EditText(this); //edit 추가
                        EditText td3two = new EditText(this); //edit 추가

                        td1two.setText(name);
                        td2two.setText(price);
                        td3two.setText(interest);

                        allEdit2.add(td1two); //배열에 넣기.
                        allEdit2.add(td2two);
                        allEdit2.add(td3two);

                        td1two.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                        td2two.setBackgroundResource(R.drawable.table); //edit에 배경 삽입.
                        td3two.setBackgroundResource(R.drawable.table);

                        td1two.setGravity(Gravity.CENTER);
                        td2two.setGravity(Gravity.CENTER);
                        td3two.setGravity(Gravity.CENTER);

                        TableRow.LayoutParams params2 = new TableRow.LayoutParams(width2, TableRow.LayoutParams.MATCH_PARENT, 1f);

                        td1two.setLayoutParams(params2);
                        td2two.setLayoutParams(params2); //edit 추가
                        td3two.setLayoutParams(params2); //edit 추가

                        addTr2.addView(td1two); //tableRow에 edit 넣기.
                        addTr2.addView(td2two); //tableRow에 edit 넣기.
                        addTr2.addView(td3two); //tableRow에 edit 넣기.

                        addTable2.addView(addTr2);

                    }

                }
            } //for문 첫번째 (i)

        } catch (JSONException e) {
            e.printStackTrace();
        }


    } //showSetting 끝


    //사이즈 공백 체크
    private  boolean checkSize() {
        String size1 = size1Edit.getText().toString().trim();
        String size2 = size2Edit.getText().toString().trim();
        String size3 = size3Edit.getText().toString().trim();

        if (size1.replace(" ", "").equals("")) {
            size1Edit.setError("사이즈를 입력해주세요.");
            size1Edit.requestFocus();
            return false;
        }

        if (size2.replace(" ", "").equals("")) {
            size2Edit.setError("사이즈를 입력해주세요.");
            size2Edit.requestFocus();
            return false;
        }

        return true;
    }



    static class BasicMenuInfoTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/insertBasicMenu.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //convertImg, name, price, explain
                sendMsg = "json="+strings[0];
                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;
        }
    }


    static class GetBasicMenuInfoTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/sendBasicMenuInfo.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //convertImg, name, price, explain
                //sendMsg = "json="+strings[0];
                //osw.write(sendMsg);
                //osw.flush();
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
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
