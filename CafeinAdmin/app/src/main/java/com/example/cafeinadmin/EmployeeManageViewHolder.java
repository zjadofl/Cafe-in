package com.example.cafeinadmin;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EmployeeManageViewHolder extends RecyclerView.ViewHolder {
    CardView epManageCV;
    TextView epName, epPos, epTime;

    public EmployeeManageViewHolder(@NonNull View itemView) {
        super(itemView);
        epName = itemView.findViewById(R.id.epName);
        epPos = itemView.findViewById(R.id.epPos);
        epTime = itemView.findViewById(R.id.epTime);
        epManageCV = (CardView) itemView.findViewById(R.id.epManageCV);
    }
}
