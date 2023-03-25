package com.example.cafein.Database.ModelDB.DataSource;

import com.example.cafein.Basket;
import com.example.cafein.BasketData;
import com.example.cafein.Database.ModelDB.BasketItem;

import java.util.List;

import io.reactivex.Flowable;

public class BasketRepository implements IBasketDataSource {

    private IBasketDataSource iBasketDataSource;

    public BasketRepository(IBasketDataSource iBasketDataSource) {
        this.iBasketDataSource = iBasketDataSource;
    }

    private static BasketRepository instance;

    public static BasketRepository getInstance(IBasketDataSource iBasketDataSource) {
        if(instance == null)
            instance = new BasketRepository(iBasketDataSource);
        return instance;
    }

    @Override
    public Flowable<List<BasketItem>> getBasketItems() {
        return iBasketDataSource.getBasketItems();
    }

    @Override
    public Flowable<List<BasketItem>> getBasketItemById(int basketItemId) {
        return iBasketDataSource.getBasketItemById(basketItemId);
    }

    @Override
    public int countBasketItems() {
        return iBasketDataSource.countBasketItems();
    }

    @Override
    public void emptyBasket() {
        iBasketDataSource.emptyBasket();
    }

    @Override
    public void insertToBasket(BasketItem... baskets) {
        iBasketDataSource.insertToBasket(baskets);
    }

    @Override
    public void updateBasket(BasketItem... baskets) {
        iBasketDataSource.updateBasket(baskets);
    }

    @Override
    public void deleteBasketItem(BasketItem... baskets) {
        iBasketDataSource.deleteBasketItem(baskets);
    }
}
