package com.example.cafein;


import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoffeeTab extends Fragment {

    private RecyclerView recyclerCoffee;
    private ArrayList<Menu> arrayList;
    private GridLayoutManager gridLayoutManager;


    public CoffeeTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coffee_tab, null);

        recyclerCoffee = view.findViewById(R.id.recycleCoffee);
        recyclerCoffee.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerCoffee.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerCoffee.setLayoutManager(gridLayoutManager);

        arrayList= new ArrayList<Menu>();

        CoffeeJsonTask menuJsonTask = new CoffeeJsonTask();
        menuJsonTask.execute();




        return view;
    } //onCreateView 끝


    class CoffeeJsonTask extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        String fullfile, menuNum, menuName, menuImg, menuPrice, inAmnt, pMenuNum, mAmnt;
        int s=0;

        @Override
        protected String doInBackground(String... strings) {
            try {
                //URL url = new URL("https://api.myjson.com/bins/10f43z");
                //URL url = new URL("https://api.myjson.com/bins/n3no6");
                URL url = new URL("http://cafein.freehost.kr/readCoffee.jsp");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                fullfile = stringBuffer.toString();

                /*JSONObject jsonObject = new JSONObject(fullfile); //전체
                JSONObject jsonObject1youtube = jsonObject.getJSONObject("menu"); //menu
                for(Iterator key = jsonObject1youtube.keys(); key.hasNext();) { //menu의 키
                    //key값을 전부 요청해서 List에 담는다.
                    JSONObject child = (JSONObject) jsonObject1youtube.get((String) key.next()); //next: 다음 요소 반환
                    //get: 키에 해당하는 value를 뽑는다.
                    menuName = child.getString("name");
                    menuImg = child.getString("img"); */


                JSONObject jsonObject = new JSONObject(fullfile);
                String menuStr = jsonObject.getString("coffee");
                JSONArray menuArr = new JSONArray(menuStr);
                for (int i = 0; i < menuArr.length(); i++) {
                    JSONObject menuObj = menuArr.getJSONObject(i);
                    menuNum = menuObj.getString("num");
                    menuName = menuObj.getString("name");
                    menuImg = menuObj.getString("img");
                    menuPrice = menuObj.getString("price");
                    inAmnt=menuObj.getString("amnt");
                    mAmnt = menuObj.getString("mamnt");
                    Log.i("커피", menuImg+inAmnt);

                    int manuAmnt = Integer.parseInt(mAmnt);
                    int ingreAmnt = Integer.parseInt(inAmnt);

                    menuImg = "http://cafein.freehost.kr/uploadImg/" + menuImg;
                    if (i==0) {
                        pMenuNum = menuObj.getString("num");
                        arrayList.add(new Menu(menuNum, menuName, menuImg, menuPrice));
                    }

                    if (menuNum.equals(pMenuNum)) { //우유 1 , 커피 3일 때 (재고)/ 우유1, 커피 4(제조)
                        //재료1일 때
                        s+=1;
                        if(ingreAmnt < manuAmnt) {
                            menuImg="http://cafein.freehost.kr/uploadImg/sold_out.png";
                            menuPrice="0";
                            arrayList.set(i-s+1,new Menu(menuNum, menuName, menuImg, menuPrice));

                        }

                    } else {
                        if(ingreAmnt < manuAmnt) {
                            menuImg="http://cafein.freehost.kr/uploadImg/sold_out.png";
                            menuPrice="0";
                        }
                        arrayList.add(new Menu(menuNum, menuName, menuImg, menuPrice));
                        pMenuNum = menuNum;

                    }


                    //(1)
                    /*if(Integer.parseInt(inAmnt)==0){
                        menuImg="http://cafein.freehost.kr/uploadImg/sold_out.png";
                    } else {
                        menuImg = "http://cafein.freehost.kr/uploadImg/" + menuImg;
                    }*/



                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MenuAdapter menuAdapter = new MenuAdapter(arrayList, getActivity().getApplicationContext());
            recyclerCoffee.setAdapter(menuAdapter);

        }
    }


    //menu-item의 그리드 시스템
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



}