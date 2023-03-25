package com.example.cafeinadmin;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CSViewHolder extends RecyclerView.ViewHolder {
    CardView csManageCV;
    TextView csTypeCSTitle, csDate, csStatus;

    public CSViewHolder(@NonNull View itemView) {
        super(itemView);
        csTypeCSTitle = itemView.findViewById(R.id.csTypeCSTitle);
        csDate = itemView.findViewById(R.id.csDate);
        csManageCV = itemView.findViewById(R.id.csManageCV);
        csStatus = itemView.findViewById(R.id.csStatus);
    }
}
