package com.digitalistic.co.restaurant_userapp.Apis.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewFeatured {

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
        @SerializedName("Restaurant_owner_id")
        @Expose
        private String restaurantOwnerId;
        @SerializedName("Restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("Lat")
        @Expose
        private String lat;
        @SerializedName("Lng")
        @Expose
        private String lng;
        @SerializedName("Code")
        @Expose
        private String code;
        @SerializedName("Qr_code_path")
        @Expose
        private String qrCodePath;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("__v")
        @Expose
        private Integer v;
        @SerializedName("featured")
        @Expose
        private String featured;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRestaurantOwnerId() {
            return restaurantOwnerId;
        }

        public void setRestaurantOwnerId(String restaurantOwnerId) {
            this.restaurantOwnerId = restaurantOwnerId;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getQrCodePath() {
            return qrCodePath;
        }

        public void setQrCodePath(String qrCodePath) {
            this.qrCodePath = qrCodePath;
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

        public String getFeatured() {
            return featured;
        }

        public void setFeatured(String featured) {
            this.featured = featured;
        }
    }
}
