package com.example.cafein;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class ECardMain extends Fragment implements View.OnClickListener {

    Button normalChargeBtn;
    TextView moneyText;

    DecimalFormat formatter;


    public ECardMain() {
        // Required empty public constructor
    }

    public static ECardMain newInstance() {
        return new ECardMain();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecard_main, container, false);
        setHasOptionsMenu(true);

        normalChargeBtn = view.findViewById(R.id.normalChargeBtn);
        moneyText = view.findViewById(R.id.moneyText);

        formatter = new DecimalFormat("#,###,##0");

        try {
            String money = formatter.format(Integer.parseInt(PreferenceUtils.getEMoney(getActivity())));
            //글자(숫자(글자))
            moneyText.setText(money + "원");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        normalChargeBtn.setOnClickListener(this);
        return view;

    }

    //툴바에 menu item 추가.
    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.refresh:
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("E 카드");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.normalChargeBtn:
                Intent normalChargeIntent = new Intent(getActivity(), NormalCharge.class);
                startActivity(normalChargeIntent);
                break;
        }
    } //onClick 끝

} //class 끝
