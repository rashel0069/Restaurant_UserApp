package com.digitalistic.co.restaurant_userapp.Apis.model;

import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderCart;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckoutOrder {

    @SerializedName("orderItems")
    @Expose
    private List<OrderCart> orderItems = null;

    public List<OrderCart> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderCart> orderItems) {
        this.orderItems = orderItems;
    }

}
