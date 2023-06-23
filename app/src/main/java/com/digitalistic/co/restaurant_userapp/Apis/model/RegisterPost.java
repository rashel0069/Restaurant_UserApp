package com.digitalistic.co.restaurant_userapp.Apis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterPost {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private RegisterBody registerBody;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RegisterBody getRegisterBody() {
        return registerBody;
    }

    public void setRegisterBody(RegisterBody registerBody) {
        this.registerBody = registerBody;
    }
}
