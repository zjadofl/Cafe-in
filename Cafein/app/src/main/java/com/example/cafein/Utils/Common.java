package com.example.cafein.Utils;

import com.example.cafein.Database.ModelDB.DataSource.BasketRepository;
import com.example.cafein.Database.ModelDB.Local.BasketDatabase;
import com.example.cafein.Menu;

import java.util.ArrayList;

public class Common {

    //Database
    public static BasketDatabase basketDatabase;
    public static BasketRepository basketRepository;

    private static final String OPTION_ID=""; //JSON 옵션 리스트?

    public static ArrayList<Menu> optionList = new ArrayList<>();

}
