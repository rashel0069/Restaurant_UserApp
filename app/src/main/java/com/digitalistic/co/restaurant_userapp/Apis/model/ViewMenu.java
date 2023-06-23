package com.digitalistic.co.restaurant_userapp.Apis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewMenu {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("Restaurant_id")
        @Expose
        private String restaurantId;
        @SerializedName("Category_Name")
        @Expose
        private String categoryName;
        @SerializedName("Item_name")
        @Expose
        private String itemName;
        @SerializedName("Price")
        @Expose
        private Integer price;
        @SerializedName("Quantity")
        @Expose
        private String quantity;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("__v")
        @Expose
        private Integer v;
        @SerializedName("AvailableQuantity")
        @Expose
        private Integer totalAvailable;

        @SerializedName("Status")
        @Expose
        private Integer status_Val;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

        public Integer getTotalAvailable() {
            return totalAvailable;
        }

        public void setTotalAvailable(Integer totalAvailable) {
            this.totalAvailable = totalAvailable;
        }

        public Integer getStatus_Val() {
            return status_Val;
        }

        public void setStatus_Val(Integer status_Val) {
            this.status_Val = status_Val;
        }
    }
}
