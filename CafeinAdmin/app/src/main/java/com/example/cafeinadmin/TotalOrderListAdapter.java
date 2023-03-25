package com.example.cafeinadmin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TotalOrderListAdapter extends RecyclerView.Adapter<TotalOrderListViewHolder> {
    private ArrayList<TotalOrderListData> lstTotalOrderList;
    private Context context;

    public TotalOrderListAdapter() {}

    public TotalOrderListAdapter(ArrayList<TotalOrderListData> lstOrder, Context context) {
        this.lstTotalOrderList = lstOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public TotalOrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //recycler View에 반복될 아이템 레이아웃 연결.
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, viewGroup, false);

        return new TotalOrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TotalOrderListViewHolder orViewHolder, final int i) {
        TotalOrderListData current = lstTotalOrderList.get(i);

        String orderNum = current.getOrderNum();
        String orderMnName = current.getOrderMnName();
        String option = current.getOption();
        String orderDate = current.getOrderDate();
        String orderAmnt = current.getOrderAmnt();
        String orderstatus = current.getOrderstatus();
        String orderPrice = current.getOrderPrice();


        orViewHolder.orderNum.setText(orderNum);
        orViewHolder.menuName.setText(orderMnName);
        orViewHolder.option.setText(option);
        orViewHolder.orderDate.setText(orderDate);
        orViewHolder.amount.setText(orderAmnt);
        orViewHolder.orderstatus.setText(orderstatus);
        orViewHolder.orderPrice.setText(orderPrice);






    }

    @Override
    public int getItemCount() {
        return lstTotalOrderList.size();
    }
}