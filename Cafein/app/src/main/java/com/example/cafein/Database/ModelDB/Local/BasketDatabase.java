package com.example.cafein.Database.ModelDB.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import com.example.cafein.BasketData;
import com.example.cafein.Database.ModelDB.BasketItem;

@Database(entities = {BasketItem.class}, version = 1, exportSchema = false)
public abstract class BasketDatabase extends RoomDatabase {
    public abstract BasketDAO basketDAO();
    private static BasketDatabase instance;

    public static BasketDatabase getInstance(Context context) {
        if(instance == null)
            instance = Room.databaseBuilder(context, BasketDatabase.class, "cafeinDB")
                        .allowMainThreadQueries()
                        .build();
        return instance;
    }
}
