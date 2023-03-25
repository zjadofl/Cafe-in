package com.example.cafein;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    private ArrayList<OrderHistoryData> lstOrder;
    private Context context;

    public OrderAdapter() {}

    public OrderAdapter(ArrayList<OrderHistoryData> lstOrder, Context context) {
        this.lstOrder = lstOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //recycler View에 반복될 아이템 레이아웃 연결.
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, viewGroup, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orViewHolder, final int i) {
        OrderHistoryData current = lstOrder.get(i);

        String orderNum = current.getOrderNum();
        String orderMnName = current.getOrderMnName();
        String orderDate = current.getOrderDate();
        String orderAmnt = current.getOrderAmnt();
        String orderPrice = current.getOrderPrice();
        String option = current.getOption();


        orViewHolder.orderNum.setText(orderNum);
        orViewHolder.menuName.setText(orderMnName);
        orViewHolder.orderDate.setText(orderDate);
        orViewHolder.amount.setText(orderAmnt);
        orViewHolder.orderPrice.setText(orderPrice);
        orViewHolder.option.setText(option);

        orViewHolder.orderCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, HistoryOrderDetail.class);
                detailIntent.putExtra("name", lstOrder.get(i).getOrderMnName());
                detailIntent.putExtra("menuNum", lstOrder.get(i).getMenuNum());
                detailIntent.putExtra("type", lstOrder.get(i).getMenuType());
                detailIntent.putExtra("img", lstOrder.get(i).getMenuImg());
                detailIntent.putExtra("price", lstOrder.get(i).getMenuPrice());


                //Toast.makeText(context, lstMenu.get(i).getMenuName(), Toast.LENGTH_SHORT).show();

                //액티비티가 아닌 곳에서 startActivity()를 하면 에러가 생긴다. 그걸 해결해주는 코드.
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(detailIntent);
                //Toast.makeText(context, "Item " + i + " is clicked.", Toast.LENGTH_SHORT).show();
                //.makeText(getActivity(), "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
            }
        });






    }

    @Override
    public int getItemCount() {
        return lstOrder.size();
    }
}