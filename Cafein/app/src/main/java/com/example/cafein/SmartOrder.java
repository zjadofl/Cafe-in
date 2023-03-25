package com.example.cafein;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cafein.Utils.Common;
import com.nex3z.notificationbadge.NotificationBadge;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmartOrder extends Fragment implements View.OnClickListener {

    TextView wholeMenuBtn, orderDetailBtn, coffeeOneTxt, coffeeTwoTxt, coffeeThreeTxt, nonCoffeeOneTxt, nonCoffeeTwoTxt, nonCoffeeThreeTxt,
            bakeryOneTxt, bakeryTwoTxt, bakeryThreeTxt, coffeeOneInfo, coffeeTwoInfo, coffeeThreeInfo, coffeeOneInfo2, coffeeTwoInfo2, coffeeThreeInfo2,
            nonCoffeeOneInfo, nonCoffeeTwoInfo, nonCoffeeThreeInfo, nonCoffeeOneInfo2, nonCoffeeTwoInfo2, nonCoffeeThreeInfo2,
            bakeryOneInfo, bakeryTwoInfo, bakeryThreeInfo, bakeryOneInfo2, bakeryTwoInfo2, bakeryThreeInfo2;
    NotificationBadge badge;
    ImageView basketIcon, coffeeOne, coffeeTwo, coffeeThree, nonCoffeeOne, nonCoffeeTwo, nonCoffeeThree, bakeryOne, bakeryTwo, bakeryThree;
    String name, img, num, type, price;  //추가



    public SmartOrder() {
        // Required empty public constructor
    }

    public static SmartOrder newInstance() {
        return new SmartOrder();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_smart_order, container, false);
        setHasOptionsMenu(true);

        wholeMenuBtn = view.findViewById(R.id.wholeMenuBtn);
        orderDetailBtn = view.findViewById(R.id.orderDetailBtn);

        coffeeOneTxt = view.findViewById(R.id.coffeeOneTxt);
        coffeeTwoTxt = view.findViewById(R.id.coffeeTwoTxt);
        coffeeThreeTxt = view.findViewById(R.id.coffeeThreeTxt);
        nonCoffeeOneTxt = view.findViewById(R.id.nonCoffeeOneTxt);
        nonCoffeeTwoTxt = view.findViewById(R.id.nonCoffeeTwoTxt);
        nonCoffeeThreeTxt = view.findViewById(R.id.nonCoffeeThreeTxt);
        bakeryOneTxt = view.findViewById(R.id.bakeryOneTxt);
        bakeryTwoTxt = view.findViewById(R.id.bakeryTwoTxt);
        bakeryThreeTxt = view.findViewById(R.id.bakeryThreeTxt);

        coffeeOne = view.findViewById(R.id.coffeeOne);
        coffeeTwo = view.findViewById(R.id.coffeeTwo);
        coffeeThree = view.findViewById(R.id.coffeeThree);
        nonCoffeeOne = view.findViewById(R.id.nonCoffeeOne);
        nonCoffeeTwo = view.findViewById(R.id.nonCoffeeTwo);
        nonCoffeeThree = view.findViewById(R.id.nonCoffeeThree);
        bakeryOne = view.findViewById(R.id.bakeryOne);
        bakeryTwo = view.findViewById(R.id.bakeryTwo);
        bakeryThree = view.findViewById(R.id.bakeryThree);

        coffeeOneInfo = view.findViewById(R.id.coffeeOneInfo);
        coffeeTwoInfo = view.findViewById(R.id.coffeeTwoInfo);
        coffeeThreeInfo = view.findViewById(R.id.coffeeThreeInfo);
        coffeeOneInfo2 = view.findViewById(R.id.coffeeOneInfo2);
        coffeeTwoInfo2 = view.findViewById(R.id.coffeeTwoInfo2);
        coffeeThreeInfo2 = view.findViewById(R.id.coffeeThreeInfo2);
        nonCoffeeOneInfo = view.findViewById(R.id.nonCoffeeOneInfo);
        nonCoffeeTwoInfo = view.findViewById(R.id.nonCoffeeTwoInfo);
        nonCoffeeThreeInfo = view.findViewById(R.id.nonCoffeeThreeInfo);
        nonCoffeeOneInfo2 = view.findViewById(R.id.nonCoffeeOneInfo2);
        nonCoffeeTwoInfo2 = view.findViewById(R.id.nonCoffeeTwoInfo2);
        nonCoffeeThreeInfo2 = view.findViewById(R.id.nonCoffeeThreeInfo2);
        bakeryOneInfo = view.findViewById(R.id.bakeryOneInfo);
        bakeryTwoInfo = view.findViewById(R.id.bakeryTwoInfo);
        bakeryThreeInfo = view.findViewById(R.id.bakeryThreeInfo);
        bakeryOneInfo2 = view.findViewById(R.id.bakeryOneInfo2);
        bakeryTwoInfo2 = view.findViewById(R.id.bakeryTwoInfo2);
        bakeryThreeInfo2 = view.findViewById(R.id.bakeryThreeInfo2);

        SliderView sliderView = view.findViewById(R.id.imageSlider);

        SliderAdapter adapter = new SliderAdapter(getActivity());

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();


        try {
            String result = new CoffeeRankingJsonTask().execute().get();
            Log.i("coffee", result);
            parseJson(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String result = new BakeryRankingJsonTask().execute().get();
            Log.i("bakery", result);
            bakeryJson(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String result = new NonCoffeeRankingJsonTask().execute().get();
            Log.i("nonCoffee", result);
            nonCoffeeJson(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        wholeMenuBtn.setOnClickListener(this);
        orderDetailBtn.setOnClickListener(this);
        coffeeOne.setOnClickListener(this);
        coffeeTwo.setOnClickListener(this);
        coffeeThree.setOnClickListener(this);
        nonCoffeeOne.setOnClickListener(this);
        nonCoffeeTwo.setOnClickListener(this);
        nonCoffeeThree.setOnClickListener(this);
        bakeryOne.setOnClickListener(this);
        bakeryTwo.setOnClickListener(this);
        bakeryThree.setOnClickListener(this);
        return view;


    } //OnCreate 끝


    //커피 추천
    public void parseJson(String json) throws JSONException {
        String TAG_JSON = "order";
        String TAG_NAME = "menuName";
        String TAG_NUM = "menuNum";     // 추가
        String TAG_IMG = "menuImg";
        String TAG_TYPE = "menuType";
        String TAG_PRICE = "menuPrice";


        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            name = item.getString(TAG_NAME);
            num = item.getString(TAG_NUM);     // 추가
            img = item.getString(TAG_IMG);
            type = item.getString(TAG_TYPE);
            price = item.getString(TAG_PRICE);
            Log.i("name", name);
            if (i == 0) {
                coffeeOneTxt.setText(name);
                coffeeOneInfo.setText(num);
                coffeeOneInfo2.setText(price);
                Picasso.with(getActivity()).load("http://cafein.freehost.kr/uploadImg/"+img).into(coffeeOne);
            } else if (i == 1) {
                coffeeTwoTxt.setText(name);
                coffeeTwoInfo.setText(num);
                coffeeTwoInfo2.setText(price);
                Picasso.with(getActivity()).load("http://cafein.freehost.kr/uploadImg/"+img).into(coffeeTwo);
            } else {
                coffeeThreeTxt.setText(name);
                coffeeThreeInfo.setText(num);
                coffeeThreeInfo2.setText(price);
                Picasso.with(getActivity()).load("http://cafein.freehost.kr/uploadImg/"+img).into(coffeeThree);
            }
        }
    }

    class CoffeeRankingJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/coffeeRankingJSON.jsp");
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

    //논커피 추천
    public void nonCoffeeJson(String json) throws JSONException {
        String TAG_JSON = "order";
        String TAG_NAME = "menuName";
        String TAG_NUM = "menuNum";     // 추가
        String TAG_IMG = "menuImg";
        String TAG_TYPE = "menuType";
        String TAG_PRICE = "menuPrice";


        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            name = item.getString(TAG_NAME);
            num = item.getString(TAG_NUM);     // 추가
            img = item.getString(TAG_IMG);
            type = item.getString(TAG_TYPE);
            price = item.getString(TAG_PRICE);
            Log.i("non-name", name);
            if (i == 0) {
                nonCoffeeOneTxt.setText(name);
                nonCoffeeOneInfo.setText(num);
                nonCoffeeOneInfo2.setText(price);
                Picasso.with(getActivity()).load("http://cafein.freehost.kr/uploadImg/"+img).into(nonCoffeeOne);
            } else if (i == 1) {
                nonCoffeeTwoTxt.setText(name);
                nonCoffeeTwoInfo.setText(num);
                nonCoffeeTwoInfo2.setText(price);
                Picasso.with(getActivity()).load("http://cafein.freehost.kr/uploadImg/"+img).into(nonCoffeeTwo);
            } else {
                nonCoffeeThreeTxt.setText(name);
                nonCoffeeThreeInfo.setText(num);
                nonCoffeeThreeInfo2.setText(price);
                Picasso.with(getActivity()).load("http://cafein.freehost.kr/uploadImg/"+img).into(nonCoffeeThree);
            }
        }
    }

    class NonCoffeeRankingJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/nonCoffeeRankingJSON.jsp");
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

    //베이커리 추천
    public void bakeryJson(String json) throws JSONException {
        String TAG_JSON = "order";
        String TAG_NAME = "menuName";
        String TAG_NUM = "menuNum";     // 추가
        String TAG_IMG = "menuImg";
        String TAG_TYPE = "menuType";
        String TAG_PRICE = "menuPrice";


        JSONObject jsonObject = new JSONObject(json);
        String jsonStr = jsonObject.getString(TAG_JSON);
        JSONArray jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            name = item.getString(TAG_NAME);
            num = item.getString(TAG_NUM);     // 추가
            img = item.getString(TAG_IMG);
            type = item.getString(TAG_TYPE);
            price = item.getString(TAG_PRICE);
            Log.i("name", name);
            if (i == 0) {
                bakeryOneTxt.setText(name);
                bakeryOneInfo.setText(num);
                bakeryOneInfo2.setText(price);
                Picasso.with(getActivity()).load("http://cafein.freehost.kr/uploadImg/"+img).into(bakeryOne);
            } else if (i == 1) {
                bakeryTwoTxt.setText(name);
                bakeryTwoInfo.setText(num);
                bakeryTwoInfo2.setText(price);
                Picasso.with(getActivity()).load("http://cafein.freehost.kr/uploadImg/"+img).into(bakeryTwo);
            } else {
                bakeryThreeTxt.setText(name);
                bakeryThreeInfo.setText(num);
                bakeryThreeInfo2.setText(price);
                Picasso.with(getActivity()).load("http://cafein.freehost.kr/uploadImg/"+img).into(bakeryThree);
            }
        }
    }

    class BakeryRankingJsonTask extends AsyncTask<String, String, String> {
        String sendMsg;
        String receiveMsg;


        @Override
        protected String doInBackground(String... strings) {
            try {
                String str;
                URL url = new URL("http://cafein.freehost.kr/bakeryRankingJSON.jsp");
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
        inflater.inflate(R.menu.main, menu);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wholeMenuBtn:
                Intent wholeMenuIntent = new Intent(getActivity(), WholeMenu.class);
                startActivity(wholeMenuIntent);
                break;
            case R.id.orderDetailBtn:
                ((MainActivity)getActivity()).replaceFragment(OrderHistory.newInstance());
                break;
            case R.id.coffeeOne:     // 추가
                String mnNum = coffeeOneInfo.getText().toString().trim();
                String mnPrice = coffeeOneInfo2.getText().toString().trim();
                Log.i("커피 개수", mnNum);
                Intent menuDetailIntent = new Intent(getActivity(), MenuRankingDetail.class);
                menuDetailIntent.putExtra("menuNum", mnNum);
                menuDetailIntent.putExtra("menuPrice", mnPrice);
                startActivity(menuDetailIntent);
                break;
            case R.id.coffeeTwo:     // 추가
                String mnNum2 = coffeeTwoInfo.getText().toString().trim();
                String mnPrice2 = coffeeTwoInfo2.getText().toString().trim();
                Log.i("커피 개수", mnNum2);
                Intent menuDetail2Intent = new Intent(getActivity(), MenuRankingDetail.class);
                menuDetail2Intent.putExtra("menuNum", mnNum2);
                menuDetail2Intent.putExtra("menuPrice", mnPrice2);
                startActivity(menuDetail2Intent);
                break;
            case R.id.coffeeThree:     // 추가
                String mnNum3 = coffeeThreeInfo.getText().toString().trim();
                String mnPrice3 = coffeeThreeInfo2.getText().toString().trim();
                Log.i("커피 개수", mnNum3);
                Intent menuDetail3Intent = new Intent(getActivity(), MenuRankingDetail.class);
                menuDetail3Intent.putExtra("menuNum", mnNum3);
                menuDetail3Intent.putExtra("menuPrice", mnPrice3);
                startActivity(menuDetail3Intent);
                break;
            case R.id.nonCoffeeOne:     // 추가
                String mnNum4 = nonCoffeeOneInfo.getText().toString().trim();
                String mnPrice4 = nonCoffeeOneInfo2.getText().toString().trim();
                Log.i("커피 개수", mnNum4);
                Intent menuDetail4Intent = new Intent(getActivity(), MenuRankingDetail.class);
                menuDetail4Intent.putExtra("menuNum", mnNum4);
                menuDetail4Intent.putExtra("menuPrice", mnPrice4);
                startActivity(menuDetail4Intent);
                break;
            case R.id.nonCoffeeTwo:     // 추가
                String mnNum5 = nonCoffeeTwoInfo.getText().toString().trim();
                String mnPrice5 = nonCoffeeTwoInfo2.getText().toString().trim();
                Log.i("커피 개수", mnNum5);
                Intent menuDetail5Intent = new Intent(getActivity(), MenuRankingDetail.class);
                menuDetail5Intent.putExtra("menuNum", mnNum5);
                menuDetail5Intent.putExtra("menuPrice", mnPrice5);
                startActivity(menuDetail5Intent);
                break;
            case R.id.nonCoffeeThree:     // 추가
                String mnNum6 = nonCoffeeThreeInfo.getText().toString().trim();
                String mnPrice6 = nonCoffeeThreeInfo2.getText().toString().trim();
                Log.i("커피 개수", mnNum6);
                Intent menuDetail6Intent = new Intent(getActivity(), MenuRankingDetail.class);
                menuDetail6Intent.putExtra("menuNum", mnNum6);
                menuDetail6Intent.putExtra("menuPrice", mnPrice6);
                startActivity(menuDetail6Intent);
                break;
            case R.id.bakeryOne:     // 추가
                String mnNum7 = bakeryOneInfo.getText().toString().trim();
                String mnPrice7 = bakeryOneInfo2.getText().toString().trim();
                Log.i("커피 개수", mnNum7);
                Intent menuDetail7Intent = new Intent(getActivity(), MenuRankingDetail.class);
                menuDetail7Intent.putExtra("menuNum", mnNum7);
                menuDetail7Intent.putExtra("menuPrice", mnPrice7);
                startActivity(menuDetail7Intent);
                break;
            case R.id.bakeryTwo:     // 추가
                String mnNum8 = bakeryTwoInfo.getText().toString().trim();
                String mnPrice8 = bakeryTwoInfo2.getText().toString().trim();
                Log.i("커피 개수", mnNum8);
                Intent menuDetail8Intent = new Intent(getActivity(), MenuRankingDetail.class);
                menuDetail8Intent.putExtra("menuNum", mnNum8);
                menuDetail8Intent.putExtra("menuPrice", mnPrice8);
                startActivity(menuDetail8Intent);
                break;
            case R.id.bakeryThree:     // 추가
                String mnNum9 = bakeryThreeInfo.getText().toString().trim();
                String mnPrice9 = bakeryThreeInfo2.getText().toString().trim();
                Log.i("커피 개수", mnNum9);
                Intent menuDetail9Intent = new Intent(getActivity(), MenuRankingDetail.class);
                menuDetail9Intent.putExtra("menuNum", mnNum9);
                menuDetail9Intent.putExtra("menuPrice", mnPrice9);
                startActivity(menuDetail9Intent);
                break;
        }
    } //OnClick 끝

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("스마트오더");
        }
    }



}