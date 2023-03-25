package com.example.cafein;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    TextView orderNum, menuName, orderDate, amount, orderPrice, option;
    CardView orderCV;
    //public final View mView;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        orderCV = itemView.findViewById(R.id.orderCV);
        orderNum = itemView.findViewById(R.id.orderNum);
        menuName = itemView.findViewById(R.id.menuName);
        orderDate = itemView.findViewById(R.id.orderDate);
        amount = itemView.findViewById(R.id.amount);
        orderPrice = itemView.findViewById(R.id.orderprice);
        option = itemView.findViewById(R.id.option);


        //itemLL = itemView.findViewById(R.id.itemLL);
    }
}