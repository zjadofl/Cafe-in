package com.example.cafeinadmin;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    TextView menuName, menuPrice,menuImage;
    CardView menuCardView;
    //private MenuAdapter.OnItemClickListener menuListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        menuName = itemView.findViewById(R.id.menuManageName);
        menuPrice = itemView.findViewById(R.id.menuManagePrice);
        menuCardView = (CardView) itemView.findViewById(R.id.menuManageCV);

    }
}
