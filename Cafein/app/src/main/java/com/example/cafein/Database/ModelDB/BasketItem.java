package com.example.cafein.Database.ModelDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Basket")
public class BasketItem {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "img")
    public String img;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "amount")
    public int amount;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "temperature")
    public String temperature;

    @ColumnInfo(name = "size")
    public String size;

    @ColumnInfo(name = "take")
    public String take;

    @ColumnInfo(name = "option")
    public int option;


}
