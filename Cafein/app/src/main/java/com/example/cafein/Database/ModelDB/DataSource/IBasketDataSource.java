package com.example.cafein.Database.ModelDB.DataSource;

import com.example.cafein.Basket;
import com.example.cafein.BasketData;
import com.example.cafein.Database.ModelDB.BasketItem;

import java.util.List;

import io.reactivex.Flowable;

public interface IBasketDataSource {
    Flowable<List<BasketItem>> getBasketItems();
    Flowable<List<BasketItem>> getBasketItemById(int basketItemId);
    int countBasketItems();
    void emptyBasket();
    void insertToBasket(BasketItem...baskets);
    void updateBasket(BasketItem...baskets);
    void deleteBasketItem(BasketItem...baskets);
}
