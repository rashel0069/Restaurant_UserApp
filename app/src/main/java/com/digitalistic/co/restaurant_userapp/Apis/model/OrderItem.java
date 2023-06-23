package com.digitalistic.co.restaurant_userapp.Apis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderItem {
    @SerializedName("menu_id")
    @Expose
    private String menuId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("item_name")
    @Expose
    private String itemName;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}

