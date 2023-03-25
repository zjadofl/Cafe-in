package com.example.cafein;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class BasketViewHolder extends RecyclerView.ViewHolder {
    CardView basketCV;
    TextView menuName, menuPrice;

    public BasketViewHolder(@NonNull View itemView) {
        super(itemView);
        basketCV = itemView.findViewById(R.id.basketCV);
        menuName = itemView.findViewById(R.id.menuName);
        menuPrice = itemView.findViewById(R.id.menuPrice);
    }
}
