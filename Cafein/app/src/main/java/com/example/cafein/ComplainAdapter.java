package com.example.cafein;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainViewHolder> {

    private ArrayList<Complain> lstComplain;
    private Context context;

    public ComplainAdapter() {}


    public ComplainAdapter(ArrayList<Complain> lstComplain, Context context) {
        this.lstComplain = lstComplain;
        this.context = context;
    }

    @NonNull
    @Override
    public ComplainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //recycler View에 반복될 아이템 레이아웃 연결.
        View view = LayoutInflater.from(context).inflate(R.layout.complain_item, viewGroup, false);

        return new ComplainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplainViewHolder complainViewHolder, final int i) {
        Complain current = lstComplain.get(i);

        String typeTitle = "[" + current.getComplainType() + "] " + current.getComplainName();

        complainViewHolder.complainTitle.setText(typeTitle);
        complainViewHolder.complainWriteDate.setText(current.getComplainDate());

        if (!current.getComplainReply().equals("")) {
            complainViewHolder.complainStatus.setText("답변완료");
        }
        complainViewHolder.complainCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("클릭", "했다");
                Intent detailIntent = new Intent(context, ComplainDetail.class);
                detailIntent.putExtra("name", lstComplain.get(i).getComplainName());
                detailIntent.putExtra("content", lstComplain.get(i).getComplainContent());
                detailIntent.putExtra("date", lstComplain.get(i).getComplainDate());
                detailIntent.putExtra("type", lstComplain.get(i).getComplainType());
                detailIntent.putExtra("wDate", lstComplain.get(i).getComplainWriteDate());
                detailIntent.putExtra("reply", lstComplain.get(i).getComplainReply());

                //액티비티가 아닌 곳에서 startActivity()를 하면 에러가 생긴다. 그걸 해결해주는 코드.
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(detailIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lstComplain.size();
    }

}
