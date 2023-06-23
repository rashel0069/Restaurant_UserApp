package com.digitalistic.co.restaurant_userapp.Apis;

import com.digitalistic.co.restaurant_userapp.Apis.model.CheckoutOrder;
import com.digitalistic.co.restaurant_userapp.Apis.model.OrderItem;
import com.digitalistic.co.restaurant_userapp.Apis.model.RegisterJson;
import com.digitalistic.co.restaurant_userapp.Apis.model.RegisterPost;
import com.digitalistic.co.restaurant_userapp.Apis.model.User;
import com.digitalistic.co.restaurant_userapp.Apis.model.ViewFeatured;
import com.digitalistic.co.restaurant_userapp.Apis.model.ViewMenu;
import com.digitalistic.co.restaurant_userapp.Apis.model.ViewOrder;
import com.digitalistic.co.restaurant_userapp.Apis.model.ViewTable;
import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderCart;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {


    String BaseUrl = "http://digitalistic.co:5000/api/";


    @POST("user/login/")
    Call<User> login(@Body HashMap userLogin);

    @POST("user/register/")
    Call<RegisterPost> signup(@Body RegisterJson registerJson);

    @POST("res/{res_id}/table/{table_id}/order/")
    Call<CheckoutOrder> orderCheckout(@Path("res_id") String Restaurant_id, @Path("table_id") String Table_id, @Body CheckoutOrder orderCart);

    @POST("res/{res_id}/table/{table_id}/order/")
    Call<OrderCart> checkout(@Path("res_id") String Restaurant_id, @Path("table_id") String Table_id, @Body CheckoutOrder checkoutOrder);

    @GET("res/featured/")
    Call<ViewFeatured> getFeaturedRes();

    //order list
   @GET("1/order/pending_and_accepted/")
   Call<ViewOrder>getAllOrder();
   //Complete order
   @GET("1/order/completed/")
   Call<ViewOrder>getAllOrderComplete();
   //Cancel order
   @GET("1/order/cancelled/")
   Call<ViewOrder>getAllOrderCancel();
    //Table list
   @GET("table/")
   Call<ViewTable>getAllTable();

   //menu list
   @GET("menu/")
   Call<ViewMenu>getAllMenu();



}
