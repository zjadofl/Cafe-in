package com.example.cafeinadmin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockManage extends Fragment {

    RecyclerView list;

    public StockManage() {
        // Required empty public constructor
    }

    public static StockManage newInstance() {
        return new StockManage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_manage, container, false);

        list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(new StockManageAdapter());

        return view;
    }


    class CommonViewHolder extends RecyclerView.ViewHolder {
        public CommonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class StockManageAdapter extends RecyclerView.Adapter<CommonViewHolder> {

        @NonNull
        @Override
        public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.item_stock_manage, parent, false);
            CommonViewHolder h = new CommonViewHolder(v);
            return h;
        }

        @Override
        public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("재고관리");
        }
    }

}
