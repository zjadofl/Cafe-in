package com.example.cafein;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Measure;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Payment extends AppCompatActivity implements View.OnClickListener {

    private ExpandableListView parentListView;
    TextView orderAmount, paymentAmount;
    RadioButton selectedPay, radioButton3;
    String temperature, cup, menuNum, name, price, quantity, unitPrice, option, radioText, turn, stamp, size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ArrayList<GroupData> groupData = new ArrayList<>();
        parentListView = (ExpandableListView) findViewById(R.id.paymentELV);

        orderAmount = (TextView) findViewById(R.id.orderAmount);
        paymentAmount = (TextView) findViewById(R.id.paymentAmount);

        radioButton3 = (RadioButton) findViewById(R.id.paymentRadio3);


        //선택된 정보를 받아옴.
        Intent paymentIntent = getIntent();
        menuNum = paymentIntent.getStringExtra("menuNum");
        temperature = paymentIntent.getStringExtra("temperature");
        cup = paymentIntent.getStringExtra("cup");
        size = paymentIntent.getStringExtra("size");
        name = paymentIntent.getStringExtra("menuName");
        price = paymentIntent.getStringExtra("menuPrice");
        quantity = paymentIntent.getStringExtra("quantity") + "개";
        unitPrice = paymentIntent.getStringExtra("unitPrice");


        option = temperature + "/" + cup + "/" +quantity + "/" + size;

        GroupData groupData1 = new GroupData(name);
        groupData1.child.add(new ChildData(option));

        groupData.add(groupData1);

        stamp = PreferenceUtils.getEStamp(this);


        radioButton3.setText("e - 스탬프 ( " + stamp + " / 12 )");
        orderAmount.setText(price);
        paymentAmount.setText(price);


        PaymentELVAdapter adapter = new PaymentELVAdapter(getApplicationContext(), groupData);
        parentListView.setAdapter(adapter);

        setExpandableListViewHeight(parentListView, -1);
        parentListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int position, long id) {
                setExpandableListViewHeight(parent, position);
                return false;
            }
        });




        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("결제");
        toolbar.setTitleTextColor(Color.WHITE);

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        RadioGroup pay_group = (RadioGroup) findViewById(R.id.paymentGroup);
        pay_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio_btn = (RadioButton) findViewById(checkedId);
                switch (checkedId) {
                    case R.id.paymentRadio1:
                        radioText = "e-카드";
                        break;
                    case R.id.paymentRadio2:
                        radioText = "신용카드";
                        break;
                    case R.id.paymentRadio3:
                        radioText = "E-스탬프";

                }
            }
        });

        radioText = "e-카드";

        Log.i("메뉴 이름", name);


        int stampCnt = Integer.parseInt(stamp);
        if (name.equals("아메리카노") && stampCnt >= 12) {
            radioButton3.setEnabled(true);
        }

        /*selectedPay = (RadioButton)findViewById(pay_group.getCheckedRadioButtonId());
        // pay_group 라디오그룹의 체크된(getCheckedRadioButtonId) 라디오버튼 객체 맵핑*/

        // "결제하기" 버튼 클릭시 다른 페이지로 이동
        Button paybtn = (Button) findViewById(R.id.paymentBtn);

        paybtn.setOnClickListener(this);



    } //onCreate 끝

    private void setExpandableListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            view = listAdapter.getGroupView(i, false, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
            if(((listView.isGroupExpanded(i)) && (i != group)) || ((!listView.isGroupExpanded(i)) && (i == group))) {
                View listItem = null;
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    listItem = listAdapter.getChildView(i, j, false, listItem, listView);
                    listItem.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, View.MeasureSpec.UNSPECIFIED));
                    listItem.measure(
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.paymentBtn:
                //String selectedValue = selectedPay.getText().toString(); // 해당 라디오버튼 객체의 값 가져오기
                Log.i("이름이 무엇?", radioText);
                if (radioText.equals("e-카드")) {
                    Log.i("들어옴", "성공");
                    int priceNum = Integer.parseInt(price.substring(0, price.length()-1).replace(",","")); //마지막 문자("원")와 컴마 제거.);
                    int saveEmoney = Integer.parseInt(PreferenceUtils.getEMoney(this));
                    if (priceNum > saveEmoney) { //메뉴가격이 현 이머니보다 많을 때.
                        Toast.makeText(this, "다른 결제 수단을 이용하시거나 E-money를 충전해주세요.", Toast.LENGTH_LONG).show();
                    } else { //다른 결제 수단.
                        Intent paymentIntent = new Intent(Payment.this, PaymentComplete.class);    // 보내는 클래스, 받는 클래스
                        paymentIntent.putExtra("menuNum", menuNum);
                        paymentIntent.putExtra("temperature", temperature);
                        paymentIntent.putExtra("cup", cup);
                        paymentIntent.putExtra("size", size);
                        paymentIntent.putExtra("PAYMENT", radioText); // "PAYMENT"이란 키 값으로 selectedValue를 넘김
                        paymentIntent.putExtra("menuName", name);
                        paymentIntent.putExtra("menuPrice", price); //total price
                        paymentIntent.putExtra("unitPrice", unitPrice);
                        paymentIntent.putExtra("quantity", quantity);
                        paymentIntent.putExtra("option", option);


                        //turn은 나중에 설정할 것.
                        turn = "1";
                        String userNum = PreferenceUtils.getNum(this);
                        String amount = quantity.substring(0, quantity.length()-1).replace(",",""); //마지막 문자("개")와 컴마 제거.
                        String strUnitPrice = unitPrice.replace(",",""); //마지막 문자(",")와 컴마 제거.
                        try {
                            int totalPrice = Integer.parseInt(strUnitPrice) * Integer.parseInt(amount);
                            String strPrice = Integer.toString(totalPrice);
                            //순서, 사용자번호, 메뉴번호, 수량, 가격
                            String splitCup = cup.substring(0,1);
                            String splitTem = temperature.substring(0,1);
                            Log.i("cup", splitCup);
                            Log.i("tem", splitTem);
                            String result = new OrderRequestTask().execute(turn, userNum, menuNum, amount,
                                    strPrice, radioText, splitTem, splitCup, size).get();
                            Log.i("주문입력", turn+ "/" + userNum+ "/" + name+ "/" + amount + "/" + strPrice);

                            if(result.equals("ok")){
                                Payment.this.startActivity(paymentIntent);
                                Payment.this.finish();
                            } else {
                                Log.i("주문 넣을 때", "에러발생");
                                Toast.makeText(this, "결제를 실패하셨습니다.", Toast.LENGTH_LONG).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (radioText.equals("E-스탬프")){ //e-스탬프
                    try {
                        String userNum = PreferenceUtils.getNum(this);

                        String amount = quantity.substring(0, quantity.length()-1).replace(",",""); //마지막 문자("개")와 컴마 제거.
                        String strUnitPrice = unitPrice.replace(",",""); //마지막 문자(",")와 컴마 제거.
                        int totalPrice = Integer.parseInt(strUnitPrice) * Integer.parseInt(amount);
                        String strPrice = Integer.toString(totalPrice);
                        String result = null;


                            turn = "1";
                            String splitCup = cup.substring(0,1);
                            String splitTem = temperature.substring(0,1);
                            Log.i("뭐지?", "turn:"+turn+"/"+"userNum:"+userNum+"/"+"menuNum:"+menuNum+"/"+"radioText"+radioText);
                            result = new OrderRequestTask().execute(turn, userNum, menuNum, amount,
                                    strPrice, radioText, splitTem, splitCup, size).get();


                        //수량 곱하기 12개
                        String strQuan = quantity.substring(0, quantity.length()-1).replace(",",""); //마지막 문자("개")와 컴마 제거.
                        int quan = Integer.parseInt(strQuan); //숫자로 변환.
                        int coupon = quan * 12; //선택된 수량에 따른 스탬프 사용량.
                        int stampCnt = Integer.parseInt(stamp);
                        if(stampCnt >= coupon) {
                            for(int j=0; j < quan; j++) {
                                String result1 = new EstampTask().execute(userNum).get(); //e-stamp를 깎음.
                            }

                            Intent paymentIntent = new Intent(Payment.this, PaymentComplete.class); // 보내는 클래스, 받는 클래스
                            paymentIntent.putExtra("menuNum", menuNum);
                            paymentIntent.putExtra("temperature", temperature);
                            paymentIntent.putExtra("cup", cup);
                            paymentIntent.putExtra("size", size);
                            paymentIntent.putExtra("PAYMENT", radioText); // "PAYMENT"이란 키 값으로 selectedValue를 넘김
                            paymentIntent.putExtra("menuName", name);
                            paymentIntent.putExtra("menuPrice", price); //total price
                            paymentIntent.putExtra("unitPrice", unitPrice);
                            paymentIntent.putExtra("quantity", quantity);
                            paymentIntent.putExtra("option", option);

                            Log.i("주문입력2", turn+ "/" + userNum+ "/" + name+ "/" + amount + "/" + strPrice);
                            if(result.equals("ok")){
                                Payment.this.startActivity(paymentIntent);
                                Payment.this.finish();
                            } else {
                                Log.i("주문 넣을 때", "에러발생");
                                Toast.makeText(this, "결제를 실패하셨습니다.", Toast.LENGTH_LONG).show();
                            }

                        } else { //스탬프 부족할 때.
                            Toast.makeText(this, "스탬프가 부족합니다. 개수를 조절해주세요.", Toast.LENGTH_SHORT).show();
                        }


                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else { //신용카드
                    //turn은 나중에 설정할 것.
                    turn = "1";
                    String userNum = PreferenceUtils.getNum(this);
                    String amount = quantity.substring(0, quantity.length()-1).replace(",",""); //마지막 문자("개")와 컴마 제거.
                    String strUnitPrice = unitPrice.replace(",",""); //마지막 문자(",")와 컴마 제거.
                    int totalPrice = Integer.parseInt(strUnitPrice) * Integer.parseInt(amount);
                    String strPrice = Integer.toString(totalPrice);
                    String result = null;

                    try {
                        String splitCup = cup.substring(0,1);
                        String splitTem = temperature.substring(0,1);
                        result = new OrderRequestTask().execute(turn, userNum, menuNum, amount, strPrice, radioText, splitTem, splitCup, size).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent paymentIntent = new Intent(Payment.this, PaymentComplete.class); // 보내는 클래스, 받는 클래스
                    paymentIntent.putExtra("menuNum", menuNum);
                    paymentIntent.putExtra("temperature", temperature);
                    paymentIntent.putExtra("cup", cup);
                    paymentIntent.putExtra("size", size);
                    paymentIntent.putExtra("PAYMENT", radioText); // "PAYMENT"이란 키 값으로 selectedValue를 넘김
                    paymentIntent.putExtra("menuName", name);
                    paymentIntent.putExtra("menuPrice", price); //total price
                    paymentIntent.putExtra("unitPrice", unitPrice);
                    paymentIntent.putExtra("quantity", quantity);
                    paymentIntent.putExtra("option", option);

                    Log.i("주문입력2", turn+ "/" + userNum+ "/" + name+ "/" + amount + "/" + strPrice);
                    if(result.equals("ok")){
                        Payment.this.startActivity(paymentIntent);
                        Payment.this.finish();
                    } else {
                        Log.i("주문 넣을 때", "에러발생");
                        Toast.makeText(this, "결제를 실패하셨습니다.", Toast.LENGTH_LONG).show();
                    }

                }

                break;
        }
    } //onClick 끝


    //주문 요청.
    static class OrderRequestTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/insertOrder.jsp");
                //URL url = new URL("http://14.33.171.115:8080/CafeinProject/insertOrder.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //순서, 사용자번호, 메뉴번호, 수량, 가격
                sendMsg = "turn=" + strings[0] + "&usNum=" + strings[1] + "&menuNum=" + strings[2]
                        + "&quantity=" + strings[3] + "&price=" + strings[4] + "&payment=" + strings[5]
                        + "&temperature=" + strings[6] + "&cup=" + strings[7] + "&size=" + strings[8];

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

    static class EstampTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/estamp.jsp");
                //URL url = new URL("http://14.33.171.115:8080/CafeinProject/insertOrder.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //사용자번호
                sendMsg = "usNum=" + strings[0];

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
