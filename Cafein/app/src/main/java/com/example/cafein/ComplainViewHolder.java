package com.example.cafein;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ComplainViewHolder extends RecyclerView.ViewHolder {
    TextView complainTitle, complainWriteDate, complainStatus;
    CardView complainCV;
    //public final View mView;

    public ComplainViewHolder(@NonNull View itemView) {
        super(itemView);
        complainCV = itemView.findViewById(R.id.complainCV);
        complainTitle = itemView.findViewById(R.id.complainTitle);
        complainWriteDate = itemView.findViewById(R.id.complainWriteDate);
        complainStatus = itemView.findViewById(R.id.complainStatus);

        //itemLL = itemView.findViewById(R.id.itemLL);
    }
}
