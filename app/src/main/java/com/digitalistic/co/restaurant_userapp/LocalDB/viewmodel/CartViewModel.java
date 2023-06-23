package com.digitalistic.co.restaurant_userapp.LocalDB.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.digitalistic.co.restaurant_userapp.LocalDB.repository.CartRepo;
import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderCart;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private CartRepo cartRepo;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartRepo = new CartRepo(application);
    }

    public LiveData<List<OrderCart>> getAllCartItems(){
        return cartRepo.getAllCartItemsLiveData();
    }
    public void insertCartItem(OrderCart orderCart){
        cartRepo.insertCartItem(orderCart);
    }
    public void updateQuantity(int id, int quantity){
        cartRepo.updateQuantity(id, quantity);
    }
    public void updatePrice(int id, int price){
        cartRepo.updatePrice(id, price);
    }

    public void deleteCartItem(OrderCart orderCart){
        cartRepo.deleteCartItem(orderCart);
    }
    public void deleteAllCartItems(){
        cartRepo.deleteAllCartItems();
    }
}
