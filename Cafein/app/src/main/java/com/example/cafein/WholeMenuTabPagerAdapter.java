package com.example.cafein;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class WholeMenuTabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount; // 탭의 개수

    public WholeMenuTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        //최근 탭 반환
        switch (position) {
            case 0:
                CoffeeTab coffeeTab = new CoffeeTab();
                return coffeeTab;
            case 1:
                NonCoffeeTab nonCoffeeTab = new NonCoffeeTab();
                return nonCoffeeTab;
            case 2:
                BakeryTab bakeryTab = new BakeryTab();
                return bakeryTab;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }




}
