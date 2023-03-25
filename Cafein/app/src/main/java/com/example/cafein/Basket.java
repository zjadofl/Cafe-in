package com.example.cafein;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cafein.Database.ModelDB.BasketItem;
import com.example.cafein.Utils.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Basket extends AppCompatActivity {

    ExpandableListView basketELV;
    TextView totalQuantity, totalPrice;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        compositeDisposable = new CompositeDisposable();

        //툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("담기");
        toolbar.setTitleTextColor(Color.WHITE);

        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

        ArrayList<BasketItem> groupData = new ArrayList<>();

        basketELV = (ExpandableListView) findViewById(R.id.basketELV);
        totalQuantity = (TextView) findViewById(R.id.totalQuantity);
        totalPrice = (TextView) findViewById(R.id.totalPrice);

        BasketItem groupData1 = new BasketItem();
        //groupData1.child.add(new ChildData(option));

        groupData.add(groupData1);

        loadBasketItems();

        /*BasketELVAdapter adapter = new BasketELVAdapter(getApplicationContext(), groupData);
        basketELV.setAdapter(adapter);*/

        //setExpandableListViewHeight(parentListView, -1);

        ImageView delete = (ImageView) findViewById(R.id.delete);






        basketELV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int position, long id) {
                //setExpandableListViewHeight(parent, position);
                return false;
            }
        });;

    } //onCreate 끝

    private void loadBasketItems() {
        compositeDisposable.add(
                Common.basketRepository.getBasketItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BasketItem>>() {
                    @Override
                    public void accept(List<BasketItem> baskets) throws Exception {
                        displayBasketItem(baskets);
                    }
                })
        );
    }

    private void displayBasketItem(List<BasketItem> baskets) {

        BasketELVAdapter basketELVAdapter = new BasketELVAdapter(this, baskets);
        basketELV.setAdapter(basketELVAdapter);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
} // class 끝
