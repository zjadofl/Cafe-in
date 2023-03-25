package com.example.cafein;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionViewHolder> {

    private ArrayList<Option> lstOption;
    Context context;

    public OptionAdapter() {
    }

    public OptionAdapter(ArrayList<Option> lstOption, Context context) {
        this.lstOption = lstOption;
        this.context = context;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.option_item, viewGroup, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder optionViewHolder, int i) {
        Option current = lstOption.get(i);

        optionViewHolder.optionName.setText(current.getOptionName());

    }

    @Override
    public int getItemCount() {
        return lstOption != null ? lstOption.size() : 0;
    }
}
