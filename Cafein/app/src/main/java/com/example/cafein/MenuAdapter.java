package com.example.cafein;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, viewGroup, false);
        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item, null);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i) {
        Menu current = lstMenu.get(i);
        String price = current.getMenuPrice() + "원";

        menuViewHolder.menuName.setText(current.getMenuName());
        menuViewHolder.menuPrice.setText(price);
        Picasso.with(context).load(current.getMenuImg()).into(menuViewHolder.menuImg);

        menuViewHolder.menuCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, MenuDetail.class);
                detailIntent.putExtra("menuNum", lstMenu.get(i).getMenuNum());
                detailIntent.putExtra("name", lstMenu.get(i).getMenuName());
                detailIntent.putExtra("img", lstMenu.get(i).getMenuImg());
                detailIntent.putExtra("price", lstMenu.get(i).getMenuPrice());
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
        return lstMenu.size();
    }
}
