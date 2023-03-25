package com.example.cafeinadmin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private ArrayList<Menu> lstMenu;
    private Context context;

    public MenuAdapter() {}

    public MenuAdapter(ArrayList<Menu> lstMenu, Context context) {
        this.lstMenu = lstMenu;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //recycler View에 반복될 아이템 레이아웃 연결.
        View view = LayoutInflater.from(context).inflate(R.layout.menu_manage_item, viewGroup, false);
        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item, null);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, final int i) {
        Menu current = lstMenu.get(i);
        String price = current.getMenuPrice() + "원";

        menuViewHolder.menuName.setText(current.getMenuName());
        menuViewHolder.menuPrice.setText(price);

        menuViewHolder.menuCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, MenuManageDetail.class);
                detailIntent.putExtra("name", lstMenu.get(i).getMenuName());
                detailIntent.putExtra("price", lstMenu.get(i).getMenuPrice());
                detailIntent.putExtra("img", lstMenu.get(i).getMenuImage());


                //액티비티가 아닌 곳에서 startActivity()를 하면 에러가 생긴다. 그걸 해결해주는 코드.
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(detailIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lstMenu.size();
    }
}
