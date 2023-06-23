package com.digitalistic.co.restaurant_userapp.LocalDB.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.digitalistic.co.restaurant_userapp.LocalDB.dao.CartDao;
import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderCart;

@Database(entities = {OrderCart.class}, version = 2)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDao cartDao();
    private static CartDatabase instance;

    public static synchronized CartDatabase getInstance(Context context){
        if (instance == null){
           instance = Room.databaseBuilder(context.getApplicationContext(),
                   CartDatabase.class,"OrderDatabase")
                   .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
