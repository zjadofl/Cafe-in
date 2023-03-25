package com.example.cafein.Database.ModelDB.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.cafein.Basket;
import com.example.cafein.BasketData;
import com.example.cafein.Database.ModelDB.BasketItem;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface BasketDAO {

    @Query("SELECT * FROM Basket")
    Flowable<List<BasketItem>> getBasketItems();

    @Query("SELECT * FROM Basket WHERE id=:basketItemId")
    Flowable<List<BasketItem>> getBasketItemById(int basketItemId);

    @Query("SELECT COUNT(*) FROM Basket")
    int countBasketItems();

    @Query("DELETE FROM Basket")
    void emptyBasket();

    @Insert
    void insertToBasket(BasketItem...baskets);

    @Update
    void updateBasket(BasketItem...baskets);

    @Delete
    void deleteBasketItem(BasketItem...baskets);

}
