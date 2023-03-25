package com.example.cafein;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class EasyOrder extends Fragment {

    public EasyOrder() {
    }

    public static EasyOrder newInstance() {
        return new EasyOrder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_easy_order, container, false);

        Button viewMoreBtn = view.findViewById(R.id.view_more);
        viewMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "더보기 눌림", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    } //onCreateView 끝

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("이지오더");
        }
    }

}
