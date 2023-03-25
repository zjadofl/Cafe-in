package com.example.cafeinadmin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeManageViewHolder> {
    private ArrayList<EmployeeManageData> lstEp;
    private Context context;

    public EmployeeAdapter() {}

    public EmployeeAdapter(ArrayList<EmployeeManageData> lstEp, Context context) {
        this.lstEp = lstEp;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeManageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //recycler View에 반복될 아이템 레이아웃 연결.
        View view = LayoutInflater.from(context).inflate(R.layout.employee_manage_item, viewGroup, false);

        return new EmployeeManageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeManageViewHolder emViewHolder, final int i) {
        EmployeeManageData current = lstEp.get(i);

        String epName = current.getEpName();
        String epPos = current.getEpPos();
        String epTime = current.getEpTime();

        emViewHolder.epName.setText(epName);
        emViewHolder.epPos.setText(epPos);
        emViewHolder.epTime.setText(epTime);



        emViewHolder.epManageCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, EmployeeManageDetail.class);
                detailIntent.putExtra("num", lstEp.get(i).getEpNumber());
                detailIntent.putExtra("name", lstEp.get(i).getEpName());
                detailIntent.putExtra("rank", lstEp.get(i).getEpPos());
                detailIntent.putExtra("time", lstEp.get(i).getEpTime());

                //액티비티가 아닌 곳에서 startActivity()를 하면 에러가 생긴다. 그걸 해결해주는 코드.
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(detailIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lstEp.size();
    }
}
