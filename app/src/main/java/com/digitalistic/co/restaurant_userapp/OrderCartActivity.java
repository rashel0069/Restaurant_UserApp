package com.digitalistic.co.restaurant_userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalistic.co.restaurant_userapp.Apis.UserService;
import com.digitalistic.co.restaurant_userapp.Apis.model.CheckoutOrder;
import com.digitalistic.co.restaurant_userapp.Apis.model.OrderItem;
import com.digitalistic.co.restaurant_userapp.LocalDB.utils.adapter.CartAdapter;
import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderCart;
import com.digitalistic.co.restaurant_userapp.LocalDB.viewmodel.CartViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderCartActivity extends AppCompatActivity implements Interceptor, CartAdapter.CartClickedListeners {
    public static final String MY_User = "com.digitalistic.restaurant_app";
    String token, userId, resId;
    private RecyclerView recyclerView;
    private CartViewModel cartViewModel;
    private TextView totalCartPriceTv, textView;
    private int checkoutPrice;
    private CardView cardView;
    private AppCompatButton checkoutBtn;
    private CartAdapter cartAdapter;
    HashMap<String, String> cartOrderData;
    CheckoutOrder checkoutOrder = new CheckoutOrder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);
        SharedPreferences prefs = getSharedPreferences(MY_User, MODE_PRIVATE);
        token = prefs.getString("token", null);
        userId = prefs.getString("user_id", null);


        initilizeVariable();
        //get all cart items from local db
        cartViewModel.getAllCartItems().observe(this, new Observer<List<OrderCart>>() {
            @Override
            public void onChanged(List<OrderCart> orderCarts) {
                if (!orderCarts.isEmpty()) {
                    //cartOrderData = new HashMap<>();
                    int price = 0;
                    resId = orderCarts.get(0).getMenu_Id();
                    cartAdapter.setOrderCartList(orderCarts);

                    for (int i = 0; i < orderCarts.size(); i++) {
                        price = price + orderCarts.get(i).getTotalItemPrice();
//                        cartOrderData.put("res_Id",orderCarts.get(i).getRes_Id());
//                        cartOrderData.put("menu_Id",orderCarts.get(i).getMenu_Id());
//                        cartOrderData.put("item_name",orderCarts.get(i).getItem_name());
//                        cartOrderData.put("itemPrice", String.valueOf(orderCarts.get(i).getItemPrice()));
//                        cartOrderData.put("quantity", String.valueOf(orderCarts.get(i).getQuantity()));
//                        cartOrderData.put("totalItemPrice", String.valueOf(orderCarts.get(i).getTotalItemPrice()));
                    }
                    totalCartPriceTv.setText(String.valueOf(price));
                    checkoutOrder.setOrderItems(orderCarts);
                    Toast.makeText(OrderCartActivity.this, ""+checkoutOrder.getOrderItems().size(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Cart Empty", Toast.LENGTH_SHORT).show();
                    totalCartPriceTv.setText("0");
                    cartAdapter.setOrderCartList(orderCarts);
                }
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckOutCart();
            }
        });

    }

    private void CheckOutCart() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(this::intercept)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(UserService.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();

        Toast.makeText(getApplicationContext(), ""+resId+"\n"+userId, Toast.LENGTH_SHORT).show();

        UserService userService = retrofit.create(UserService.class);
        Call<CheckoutOrder> call = userService.orderCheckout(resId, userId, checkoutOrder);
        call.enqueue(new Callback<CheckoutOrder>() {
            @Override
            public void onResponse(Call<CheckoutOrder> call, retrofit2.Response<CheckoutOrder> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Order Confirm", Toast.LENGTH_SHORT).show();
                    cartViewModel.deleteAllCartItems();
                    textView.setVisibility(View.INVISIBLE);
                    checkoutBtn.setVisibility(View.INVISIBLE);
                    totalCartPriceTv.setVisibility(View.INVISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckoutOrder> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });


        //Call<OrderCart> call = userService.orderCheckout(resId,userId,cartOrderData);
//        Call<OrderCart> call = userService.checkout(resId,userId,checkoutOrder);
//        call.enqueue(new Callback<OrderCart>() {
//            @Override
//            public void onResponse(Call<OrderCart> call, retrofit2.Response<OrderCart> response) {
//                if (response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "Success....", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(), "Unsuccessful....", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrderCart> call, Throwable t) {
//                        cartViewModel.deleteAllCartItems();
//                        textView.setVisibility(View.INVISIBLE);
//                        checkoutBtn.setVisibility(View.INVISIBLE);
//                        totalCartPriceTv.setVisibility(View.INVISIBLE);
//                        cardView.setVisibility(View.VISIBLE);
//            }
//        });


//        if (!orderCarts.isEmpty()){
//            Call<OrderCart> call = userService.orderCheckout(resId,userId, cartOrderData);
//            call.enqueue(new Callback<OrderCart>() {
//                @Override
//                public void onResponse(Call<OrderCart> call, retrofit2.Response<OrderCart> response) {
//                    if (response.isSuccessful()){
//                        Toast.makeText(getApplicationContext(), "Order Successful", Toast.LENGTH_SHORT).show();
//                        cartViewModel.deleteAllCartItems();
//                        textView.setVisibility(View.INVISIBLE);
//                        checkoutBtn.setVisibility(View.INVISIBLE);
//                        totalCartPriceTv.setVisibility(View.INVISIBLE);
//                        cardView.setVisibility(View.VISIBLE);
//                    }else {
//                        Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<OrderCart> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }


    }

    private void initilizeVariable() {
        cartAdapter = new CartAdapter(this);
        textView = findViewById(R.id.textView2);
        cardView = findViewById(R.id.cartActivityCardView);
        totalCartPriceTv = findViewById(R.id.cartActivityTotalPriceTv);
        checkoutBtn = findViewById(R.id.cartActivityCheckoutBtn);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        recyclerView = findViewById(R.id.cart_recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);
    }


    @Override
    public void onDeleteClicked(OrderCart orderCart) {
        cartViewModel.deleteCartItem(orderCart);
    }

    @Override
    public void onPlusClicked(OrderCart orderCart) {

        int quantity = orderCart.getQuantity() + 1;
        cartViewModel.updateQuantity(orderCart.getId(), quantity);
        cartViewModel.updatePrice(orderCart.getId(), quantity * orderCart.getItemPrice());
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMinusClicked(OrderCart orderCart) {
        int quantity = orderCart.getQuantity() - 1;
        if (quantity != 0) {
            cartViewModel.updateQuantity(orderCart.getId(), quantity);
            cartViewModel.updatePrice(orderCart.getId(), quantity * orderCart.getItemPrice());
            cartAdapter.notifyDataSetChanged();
        } else {
            cartViewModel.deleteCartItem(orderCart);
            cartAdapter.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();
        return chain.proceed(newRequest);
    }
}