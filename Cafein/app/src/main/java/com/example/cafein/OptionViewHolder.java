package com.example.cafein;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;



public class OptionViewHolder extends RecyclerView.ViewHolder {

    TextView optionName;

    public OptionViewHolder(@NonNull View itemView) {
        super(itemView);

        optionName = (TextView) itemView.findViewById(R.id.optionName);
    }
}
