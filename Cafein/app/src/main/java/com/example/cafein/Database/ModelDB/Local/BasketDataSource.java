package com.example.cafein.Database.ModelDB.Local;

import com.example.cafein.Basket;
import com.example.cafein.BasketData;
import com.example.cafein.Database.ModelDB.BasketItem;
import com.example.cafein.Database.ModelDB.DataSource.IBasketDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class BasketDataSource implements IBasketDataSource {

    private BasketDAO basketDAO;
    private static BasketDataSource instance;

    public BasketDataSource(BasketDAO basketDAO) {
        this.basketDAO = basketDAO;
    }

    public static BasketDataSource getInstance(BasketDAO basketDAO) {
        if(instance == null)
            instance = new BasketDataSource(basketDAO);
        return instance;
    }

    @Override
    public Flowable<List<BasketItem>> getBasketItems() {
        return basketDAO.getBasketItems();
    }

    @Override
    public Flowable<List<BasketItem>> getBasketItemById(int basketItemId) {
        return basketDAO.getBasketItemById(basketItemId);
    }

    @Override
    public int countBasketItems() {
        return basketDAO.countBasketItems();
    }

    @Override
    public void emptyBasket() {
        basketDAO.emptyBasket();
    }

    @Override
    public void insertToBasket(BasketItem... baskets) {
        basketDAO.insertToBasket(baskets);
    }

    @Override
    public void updateBasket(BasketItem... baskets) {
        basketDAO.updateBasket(baskets);
    }

    @Override
    public void deleteBasketItem(BasketItem... baskets) {
        basketDAO.deleteBasketItem(baskets);
    }
}
