package com.example.cafeinadmin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CSAdapter extends RecyclerView.Adapter<CSViewHolder> {
    private ArrayList<CSManageData> lstCS;
    private Context context;

    public CSAdapter() {}

    public CSAdapter(ArrayList<CSManageData> lstComplain, Context context) {
        this.lstCS = lstComplain;
        this.context = context;
    }

    @NonNull
    @Override
    public CSViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //recycler View에 반복될 아이템 레이아웃 연결.
        View view = LayoutInflater.from(context).inflate(R.layout.cs_manage_item, viewGroup, false);

        return new CSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CSViewHolder complainViewHolder, final int i) {
        CSManageData current = lstCS.get(i);

        String typeTitle = "[" + current.getCsType() + "] " + current.getCsWDate();

        complainViewHolder.csTypeCSTitle.setText(typeTitle);
        complainViewHolder.csDate.setText(current.getCsDate());
        if (!current.getCsReply().equals("")) {
            complainViewHolder.csStatus.setText("답변완료");
        }


        complainViewHolder.csManageCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, CSManageDetail.class);
                detailIntent.putExtra("id", lstCS.get(i).getId());
                detailIntent.putExtra("userID", lstCS.get(i).getUserName());
                detailIntent.putExtra("name", lstCS.get(i).getCsName());
                detailIntent.putExtra("content", lstCS.get(i).getCsContent());
                detailIntent.putExtra("wDate", lstCS.get(i).getCsWDate());
                detailIntent.putExtra("date", lstCS.get(i).getCsDate());
                detailIntent.putExtra("type", lstCS.get(i).getCsType());
                detailIntent.putExtra("reply", lstCS.get(i).getCsReply());


                //액티비티가 아닌 곳에서 startActivity()를 하면 에러가 생긴다. 그걸 해결해주는 코드.
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(detailIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lstCS.size();
    }
}
