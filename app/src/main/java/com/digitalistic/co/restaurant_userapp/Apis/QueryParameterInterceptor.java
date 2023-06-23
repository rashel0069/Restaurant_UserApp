package com.digitalistic.co.restaurant_userapp.Apis;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class QueryParameterInterceptor implements Interceptor {
    Context context;

    public QueryParameterInterceptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        return null;
    }
}
