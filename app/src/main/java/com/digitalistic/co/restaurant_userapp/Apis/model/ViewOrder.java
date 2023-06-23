package com.digitalistic.co.restaurant_userapp.Apis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewOrder {
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
        @SerializedName("Order_id")
        @Expose
        private String orderId;
        @SerializedName("Table_id")
        @Expose
        private String tableId;
        @SerializedName("orderItems")
        @Expose
        private List<OrderItem> orderItems = null;
        @SerializedName("orderDate")
        @Expose
        private String orderDate;
        @SerializedName("Accepted_at")
        @Expose
        private Object acceptedAt;
        @SerializedName("Completed_at")
        @Expose
        private Object completedAt;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("Serving_time")
        @Expose
        private String servingTime;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("__v")
        @Expose
        private Integer v;
        @SerializedName("Table_number")
        @Expose
        private Integer tableNumber;

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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTableId() {
            return tableId;
        }

        public void setTableId(String tableId) {
            this.tableId = tableId;
        }

        public List<OrderItem> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public Object getAcceptedAt() {
            return acceptedAt;
        }

        public void setAcceptedAt(Object acceptedAt) {
            this.acceptedAt = acceptedAt;
        }

        public Object getCompletedAt() {
            return completedAt;
        }

        public void setCompletedAt(Object completedAt) {
            this.completedAt = completedAt;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getServingTime() {
            return servingTime;
        }

        public void setServingTime(String servingTime) {
            this.servingTime = servingTime;
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

        public Integer getTableNumber() {
            return tableNumber;
        }

        public void setTableNumber(Integer tableNumber) {
            this.tableNumber = tableNumber;
        }

    }
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
        @SerializedName("_id")
        @Expose
        private String id;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
}
