package com.digitalistic.co.restaurant_userapp.LocalDB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderCart;
import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderItem;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface CartDao {

    @Insert
    void insertOrder(OrderCart orderCart);

    @Query("SELECT * FROM order_table")
    LiveData<List<OrderCart>>getAllCartItems();


    @Delete
    void deleteCartItem(OrderCart orderCart);

    @Query("UPDATE order_table SET quantity=:quantity WHERE id=:id")
    void updateQuantity(int id, int quantity);

    @Query("UPDATE order_table SET totalItemPrice=:totalItemPrice WHERE id=:id")
    void updatePrice(int id, int totalItemPrice);

    @Query("DELETE FROM order_table")
    void deleteALLItems();
}
