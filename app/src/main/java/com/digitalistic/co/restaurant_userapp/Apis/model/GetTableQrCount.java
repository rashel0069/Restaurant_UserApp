package com.digitalistic.co.restaurant_userapp.Apis.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTableQrCount {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("total_scan")
        @Expose
        private Integer totalScan;

        public Integer getTotalScan() {
            return totalScan;
        }

        public void setTotalScan(Integer totalScan) {
            this.totalScan = totalScan;
        }

    }
}

