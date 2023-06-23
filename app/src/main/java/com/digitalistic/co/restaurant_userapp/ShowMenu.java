package com.digitalistic.co.restaurant_userapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalistic.co.restaurant_userapp.Apis.UserService;
import com.digitalistic.co.restaurant_userapp.Apis.model.ViewMenu;
import com.digitalistic.co.restaurant_userapp.LocalDB.utils.model.OrderCart;
import com.digitalistic.co.restaurant_userapp.LocalDB.viewmodel.CartViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowMenu extends AppCompatActivity implements Interceptor {
    public static final String MY_User = "com.digitalistic.restaurant_app";
    String token,userId,resId,menuId;
    RecyclerView recyclerView;
    recyclerAdapter adapter;
    LinearLayout noMenufound;
    ProgressBar progressBar;
    private CartViewModel viewModel;
    private List<OrderCart> orderCartsList;
    TextView addCart_count;
    FloatingActionButton viewCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);
        SharedPreferences prefs = getSharedPreferences(MY_User,MODE_PRIVATE);
        addCart_count = findViewById(R.id.add_cart_text_count);
        viewCartButton = findViewById(R.id.fab_cart_id);
        token = prefs.getString("token",null);
        userId = prefs.getString("user_id",null);
        Intent intent = getIntent();
        resId = intent.getStringExtra("Res_ID");
        menuId = intent.getStringExtra("Menu_ID");
        noMenufound = findViewById(R.id.no_menu_found);
        progressBar = findViewById(R.id.s_loading_progress);
        // recyler view
        recyclerView = findViewById(R.id.recyclerMenu_id);
        showMenus();
        initilizeVariable();
        //get all cart items from local db
        viewModel.getAllCartItems().observe(this, new Observer<List<OrderCart>>() {
            @Override
            public void onChanged(List<OrderCart> orderCarts) {
                orderCartsList.clear();
                orderCartsList.addAll(orderCarts);
                addCart_count.setText(String.valueOf(orderCartsList.size()));
            }
        });


        //floating button
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewCart = new Intent(getApplicationContext(),OrderCartActivity.class);
                startActivity(viewCart);
            }
        });

        //recylerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initilizeVariable() {
        orderCartsList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void showMenus() {
        progressBar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(this::intercept)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(UserService.BaseUrl+"res"+"/"+menuId+"/")
                .addConverterFactory(GsonConverterFactory.create()).build();


        Toast.makeText(getApplicationContext(), "User ID"+menuId, Toast.LENGTH_SHORT).show();

        final  UserService userService = retrofit.create(UserService.class);
        Call<ViewMenu> call = userService.getAllMenu();
        call.enqueue(new Callback<ViewMenu>() {
            @Override
            public void onResponse(Call<ViewMenu> call, Response<ViewMenu> response) {
                if (response.isSuccessful()){
                    if (!response.body().getData().isEmpty()){
                        noMenufound.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        adapter = new recyclerAdapter(response.body().getData());
                        recyclerView.setAdapter(adapter);
                    }else {
                        noMenufound.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewMenu> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    @NonNull
    @Override
    public okhttp3.Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .header("Authorization","Bearer "+ token)
                .build();
        return chain.proceed(newRequest);
    }
    class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MenuViewHolder>{
        List<ViewMenu.Datum> datumList;
        public recyclerAdapter(List<ViewMenu.Datum> datumList){
            this.datumList = datumList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public recyclerAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menulistview,null);
            recyclerAdapter.MenuViewHolder viewHolder = new recyclerAdapter.MenuViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull recyclerAdapter.MenuViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.menuItem.setText(datumList.get(position).getItemName());
            holder.menuPrice.setText(String.valueOf(datumList.get(position).getPrice()));

            holder.addCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderCart orderCart = new OrderCart();
                    orderCart.setRes_Id(menuId);
                    orderCart.setMenu_Id(datumList.get(position).getId());
                    orderCart.setItem_name(datumList.get(position).getItemName());
                    orderCart.setItemPrice(Integer.parseInt(String.valueOf(datumList.get(position).getPrice())));


                    final int[] quantity = {1};
                    final int[] id = new int[1];
                    if (!orderCartsList.isEmpty()){
                        for (int i = 0; i<orderCartsList.size(); i++){
                            if (orderCart.getItem_name().equals(orderCartsList.get(i).getItem_name())){
                               quantity[0] = orderCartsList.get(i).getQuantity();
                               quantity[0]++;
                               id[0] = orderCartsList.get(i).getId();
                            }
                        }
                    }
                    if (quantity[0]==1){
                        orderCart.setQuantity(quantity[0]);
                        orderCart.setTotalItemPrice(quantity[0]* orderCart.getItemPrice());
                        viewModel.insertCartItem(orderCart);
                        Toast.makeText(getApplicationContext(), "Add to Cart", Toast.LENGTH_SHORT).show();
                    }else {
                        viewModel.updateQuantity(id[0],quantity[0]);
                        viewModel.updatePrice(id[0],(quantity[0]*orderCart.getItemPrice()));
                        Toast.makeText(getApplicationContext(), "Add to Cart", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return datumList.size();
        }

        class MenuViewHolder extends RecyclerView.ViewHolder{

            TextView menuPrice, menuCategory,menuItem,menuAvailable;
            ImageButton addCartButton;

            public MenuViewHolder(@NonNull View itemView) {
                super(itemView);
                menuItem = itemView.findViewById(R.id.menu_view_checkbox_id);
                menuPrice = itemView.findViewById(R.id.menu_view_price_id);
                addCartButton = itemView.findViewById(R.id.add_cart_id);
            }
        }
    }
}