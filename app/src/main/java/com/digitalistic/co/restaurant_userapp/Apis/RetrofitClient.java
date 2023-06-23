package com.digitalistic.co.restaurant_userapp.Apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static Retrofit retrofitWithDifBase = null;

    private static String BaseUrl = "http://digitalistic.co:5000/api/";
    //private static String BaseUrl = "https://digi-restro.herokuapp.com/api/";
    private static Retrofit retrofitWithNoIntercepter = null;


    private RetrofitClient() {
    }

    public static Retrofit noInterceptor() {
        if (retrofitWithNoIntercepter == null) {
            retrofitWithNoIntercepter = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitWithNoIntercepter;
    }
}
