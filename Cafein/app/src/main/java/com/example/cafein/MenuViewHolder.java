package com.example.cafein;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    TextView menuName, menuPrice;
    ImageView menuImg;
    CardView menuCardView;
    //private MenuAdapter.OnItemClickListener menuListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        menuName = itemView.findViewById(R.id.menuName);
        menuImg = itemView.findViewById(R.id.menuImg);
        menuPrice = itemView.findViewById(R.id.menuPrice);
        menuCardView = (CardView) itemView.findViewById(R.id.menuCardView);

        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        menuListener.onItemClick(position);
                    }
                }
            }
        });*/


    }
}
