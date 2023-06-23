package com.digitalistic.co.restaurant_userapp.LocalDB.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.digitalistic.co.restaurant_userapp.LocalDB.dao.CartDao;
import com.digitalistic.co.restaurant_userapp.LocalDB.database.CartDatabase;
import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderCart;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartRepo {
    private CartDao cartDao;
    private LiveData<List<OrderCart>> allCartItemsLiveData;
    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<OrderCart>> getAllCartItemsLiveData() {
        return allCartItemsLiveData;
    }

    public CartRepo(Application application){
        cartDao = CartDatabase.getInstance(application).cartDao();
        allCartItemsLiveData = cartDao.getAllCartItems();
    }

    public void insertCartItem(OrderCart orderCart){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.insertOrder(orderCart);
            }
        });
    }

    public void deleteCartItem(OrderCart orderCart){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.deleteCartItem(orderCart);
            }
        });
    }

    public void updateQuantity(int id, int quantity){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.updateQuantity(id,quantity);
            }
        });
    }

    public void updatePrice(int id, int price){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.updatePrice(id,price);
            }
        });
    }

    public void deleteAllCartItems(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.deleteALLItems();
            }
        });
    }

}
