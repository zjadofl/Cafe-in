package com.example.cafeinadmin;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import static android.support.v7.widget.RecyclerView.*;

public class TotalOrderListViewHolder extends ViewHolder {
    TextView orderNum, menuName, option, orderDate, amount, orderstatus, orderPrice;
    CardView totalorderCV;
    //public final View mView;

    public TotalOrderListViewHolder(@NonNull View itemView) {
        super(itemView);
        totalorderCV = itemView.findViewById(R.id.orderCV);
        orderNum = itemView.findViewById(R.id.orderNum);
        option = itemView.findViewById(R.id.option);
        menuName = itemView.findViewById(R.id.menuName);
        orderDate = itemView.findViewById(R.id.orderDate);
        amount = itemView.findViewById(R.id.amount);
        orderstatus = itemView.findViewById(R.id.status);
        orderPrice = itemView.findViewById(R.id.price);

        //itemLL = itemView.findViewById(R.id.itemLL);
    }
}