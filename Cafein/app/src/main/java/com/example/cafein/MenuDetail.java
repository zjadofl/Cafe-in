package com.example.cafein;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.camera2.params.LensShadingMap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafein.Database.ModelDB.BasketItem;
import com.example.cafein.Utils.Common;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MenuDetail extends AppCompatActivity implements View.OnClickListener {
    TextView menuNameDetail, menuPriceDetail;
    ImageView menuImgDetail;
    LinearLayout sizeLayout;
    Button hotBtn, iceBtn, storeCupCBtn, disposableCupCBtn, paymentBtn, smallBtn, mediumBtn, largeBtn;
    String temperature, cup, unitPrice, size,img,price,getType,soprice;
    int totalPrice;
    NumberPicker numberPicker;
    Integer intPrice;
    DecimalFormat format = new DecimalFormat("#,000"); //형식 바꿈.

    String menuNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        //Button myButton = new Button(this);
        //myButton.setText("test");


        menuNameDetail = (TextView) findViewById(R.id.menuNameDetail);
        menuPriceDetail = (TextView) findViewById(R.id.menuPriceDetail);

        menuImgDetail = (ImageView) findViewById(R.id.menuImgDetail);

        sizeLayout = (LinearLayout) findViewById(R.id.sizeLayout);
        LinearLayout tempLayout=(LinearLayout)findViewById(R.id.temperature);

        hotBtn = (Button) findViewById(R.id.hotBtn);
        iceBtn = (Button) findViewById(R.id.iceBtn);
        paymentBtn = (Button) findViewById(R.id.paymentBtn);

        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);


        smallBtn = (Button) findViewById(R.id.smallBtn);
        mediumBtn = (Button) findViewById(R.id.mediumBtn);
        largeBtn = (Button) findViewById(R.id.largeBtn);



        storeCupCBtn = (Button) findViewById(R.id.storeCupCBtn);
        disposableCupCBtn = (Button) findViewById(R.id.disposableCupCBtn);

        temperature = "HOT";
        cup = "일회용컵";
        size = "S";



        //sizeLayout.addView(myButton);

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("메뉴 상세");
        toolbar.setTitleTextColor(Color.WHITE);

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        //선택된 정보를 받아옴.
        Intent detailIntent = getIntent();
        menuNum = detailIntent.getStringExtra("menuNum");
        String name = detailIntent.getStringExtra("name");
        img = detailIntent.getStringExtra("img");
        price = detailIntent.getStringExtra("price") + "원";
        soprice = detailIntent.getStringExtra("price") + "원";
        unitPrice = detailIntent.getStringExtra("price");
        intPrice=Integer.parseInt(unitPrice);
        //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        menuNameDetail.setText(name);
        menuPriceDetail.setText(price);
        Picasso.with(this).load(img).into(menuImgDetail);

        try {
            getType = new ShowTypeTask().execute(menuNum).get();
            Log.i("졸업굿",getType);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(getType.equals("b")){
            sizeLayout.setVisibility(View.INVISIBLE);
            tempLayout.setVisibility(View.GONE);
            View d1=(View)findViewById(R.id.dividerC1);
            View d4=(View)findViewById(R.id.dividerC4);
            View d5=(View)findViewById(R.id.dividerC5);
            View d6=(View)findViewById(R.id.dividerC6);
            d1.setVisibility(View.GONE);
            d4.setVisibility(View.INVISIBLE);
            d5.setVisibility(View.INVISIBLE);
            d6.setVisibility(View.INVISIBLE);
            RelativeLayout cupLayout=(RelativeLayout)findViewById(R.id.cup);
            cupLayout.setVisibility(View.INVISIBLE);
            TextView sizeText=(TextView)findViewById(R.id.sizeTextView);
            sizeText.setVisibility(View.INVISIBLE);




        }

        //numberPicker의 작동에 따라 가격이 다르게 측정.
        numberPicker.setValueChangedListener(new ValueChangedListener() {

            @Override
            public void valueChanged(int value, ActionEnum action) {
                String strPrice = price;
                String subPrice = strPrice.substring(0, strPrice.length()-1).replace(",",""); //마지막 문자("원")와 컴마 제거.
                int menuQuantity = numberPicker.getValue(); //메뉴 수량
                int menuPrice = Integer.parseInt(subPrice);
                Log.i("졸업좀",size);
                totalPrice = menuQuantity * menuPrice;
                String addPrice =  format.format(totalPrice) + "원";
                menuPriceDetail.setText(addPrice);
                Log.i("price", addPrice+"/"+menuQuantity);

            }
        });


        getOptionList();



        //이벤트 리스너
        hotBtn.setOnClickListener(this);
        iceBtn.setOnClickListener(this);
        storeCupCBtn.setOnClickListener(this);
        disposableCupCBtn.setOnClickListener(this);
        paymentBtn.setOnClickListener(this);
        smallBtn.setOnClickListener(this);
        mediumBtn.setOnClickListener(this);
        largeBtn.setOnClickListener(this);

        if(price.equals("0원")){
            paymentBtn.setText("품절인 메뉴입니다.");
            paymentBtn.setBackgroundColor(getColor(R.color.divider));
        }



    } //onCreate() 끝

    @SuppressLint("StaticFieldLeak")
    class ShowTypeTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/showType.jsp");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "menuNum=" + strings[0];
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


    private void getOptionList() {
    }

    //**************************** 만든 메소드 ****************************
    //이벤트 리스너
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hotBtn:
                hotBtn.setBackgroundResource(R.drawable.menu_option_active);
                hotBtn.setTextColor(getColor(R.color.white));
                iceBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                iceBtn.setTextColor(getColor(R.color.black));
                temperature = "HOT";
                break;
            case R.id.iceBtn:
                hotBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                hotBtn.setTextColor(getColor(R.color.black));
                iceBtn.setBackgroundResource(R.drawable.menu_option_active);
                iceBtn.setTextColor(getColor(R.color.white));
                temperature = "ICE";
                break;

            case R.id.storeCupCBtn:
                storeCupCBtn.setBackgroundResource(R.drawable.menu_option_active);
                storeCupCBtn.setTextColor(getColor(R.color.white));
                disposableCupCBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                disposableCupCBtn.setTextColor(getColor(R.color.black));
                cup = "매장용컵";
                break;

            case R.id.disposableCupCBtn:
                storeCupCBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                storeCupCBtn.setTextColor(getColor(R.color.black));
                disposableCupCBtn.setBackgroundResource(R.drawable.menu_option_active);
                disposableCupCBtn.setTextColor(getColor(R.color.white));
                cup = "일회용컵";
                break;

            case R.id.smallBtn:
                String sPrice = unitPrice+"원";
                String subsPrice = sPrice.substring(0, sPrice.length()-1).replace(",","");
                int smenuPrice = Integer.parseInt(subsPrice);
                smallBtn.setBackgroundResource(R.drawable.menu_option_active);
                smallBtn.setTextColor(getColor(R.color.white));
                mediumBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                mediumBtn.setTextColor(getColor(R.color.black));
                largeBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                largeBtn.setTextColor(getColor(R.color.black));
                if(numberPicker.getValue()!=1){
                    numberPicker.setValue(1);
                    Toast.makeText(this,"사이즈변경에 따른 수량을 다시 선택해주세요.",Toast.LENGTH_SHORT).show();}
                size = "S";
                price=format.format(smenuPrice)+"원";
                menuPriceDetail.setText(price);

                break;

            case R.id.mediumBtn:
                String mPrice = unitPrice+"원";
                String submPrice = mPrice.substring(0, mPrice.length()-1).replace(",","");
                int mmenuPrice = Integer.parseInt(submPrice);
                smallBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                smallBtn.setTextColor(getColor(R.color.black));
                mediumBtn.setBackgroundResource(R.drawable.menu_option_active);
                mediumBtn.setTextColor(getColor(R.color.white));
                largeBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                largeBtn.setTextColor(getColor(R.color.black));
                price=format.format(mmenuPrice+500)+"원";
                menuPriceDetail.setText(price);
                size = "M";
                if(numberPicker.getValue()!=1){
                    numberPicker.setValue(1);
                    Toast.makeText(this,"사이즈변경에 따른 수량을 다시 선택해주세요.",Toast.LENGTH_SHORT).show();}
                break;

            case R.id.largeBtn:
                String lPrice = unitPrice+"원";
                String sublPrice = lPrice.substring(0, lPrice.length()-1).replace(",","");
                int lmenuPrice = Integer.parseInt(sublPrice);
                smallBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                smallBtn.setTextColor(getColor(R.color.black));
                mediumBtn.setBackgroundResource(R.drawable.menu_option_not_active);
                mediumBtn.setTextColor(getColor(R.color.black));
                largeBtn.setBackgroundResource(R.drawable.menu_option_active);
                largeBtn.setTextColor(getColor(R.color.white));
                price=format.format(lmenuPrice+1000)+"원";
                menuPriceDetail.setText(price);
                if(numberPicker.getValue()!=1){
                    numberPicker.setValue(1);
                    Toast.makeText(this,"사이즈변경에 따른 수량을 다시 선택해주세요.",Toast.LENGTH_SHORT).show();}
                size = "L";

                break;


            case R.id.paymentBtn:
                if(soprice.equals("0원")){
                    Toast.makeText(this,"품절된 메뉴입니다.", Toast.LENGTH_SHORT).show();
                } else{
                    String menuName = menuNameDetail.getText().toString();
                    String menuPrice = menuPriceDetail.getText().toString();
                    String menuQuantity = Integer.toString(numberPicker.getValue());
                    Log.i("수량", menuQuantity+"");

                    Intent paymentIntent = new Intent(this, Payment.class);
                    paymentIntent.putExtra("menuNum", menuNum);
                    paymentIntent.putExtra("temperature", temperature);
                    paymentIntent.putExtra("cup", cup);
                    paymentIntent.putExtra("size", size);
                    paymentIntent.putExtra("menuName", menuName);
                    paymentIntent.putExtra("menuPrice", menuPrice);
                    paymentIntent.putExtra("quantity", menuQuantity);
                    paymentIntent.putExtra("unitPrice", unitPrice);


                    startActivity(paymentIntent);
                    break;}



        } //switch 끝
    }






    //백버튼 작동
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}