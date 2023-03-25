package com.example.cafein;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private ArrayList<Store> mList;

    public class StoreViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;

        public StoreViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.storeName);
        }
    }

    public StoreAdapter(ArrayList<Store> list) {
        this.mList = list;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.store_item, viewGroup, false);

        StoreViewHolder viewHolder = new StoreViewHolder(view);

        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder viewHolder, int position) {

        viewHolder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewHolder.name.setGravity(Gravity.CENTER);
        viewHolder.name.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
