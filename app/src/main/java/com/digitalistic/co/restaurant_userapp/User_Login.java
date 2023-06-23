package com.digitalistic.co.restaurant_userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalistic.co.restaurant_userapp.Apis.UserService;
import com.digitalistic.co.restaurant_userapp.Apis.model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User_Login extends AppCompatActivity {
    TextView regButton;
    Button loginButton;
    EditText lg_userName, lg_Password;
    ProgressBar progressBar;
    HashMap<String, String> sendData = new HashMap<>();
    public static final String MY_User = "com.digitalistic.co.restaurant_userapp";
    String token,user_id, mobile,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mobile = getIntent().getStringExtra("userMobile");
        pass = getIntent().getStringExtra("userPass");
        progressBar = findViewById(R.id.progressBar);
        //login users
        lg_userName = findViewById(R.id.lg_mobile_id);
        lg_Password = findViewById(R.id.lg_password_id);

        regButton = findViewById(R.id.register_button_id);
        loginButton = findViewById(R.id.login_button_id);

        loginButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(lg_userName.getText().toString().trim())
                    && !TextUtils.isEmpty(lg_Password.getText().toString().trim())) {
                //HashMap
                sendData.put("phone",lg_userName.getText().toString().trim());
                sendData.put("password",lg_Password.getText().toString().trim());
                SharedPreferences.Editor editor = getSharedPreferences(MY_User, MODE_PRIVATE).edit();
                editor.putString("phone",lg_userName.getText().toString().trim());
                editor.putString("password",lg_Password.getText().toString().trim());
                editor.commit();
                progressBar.setVisibility(View.VISIBLE);
                LoginOwner();
            }else {
                Toast.makeText(getApplicationContext(), "Enter Mobile and Password", Toast.LENGTH_SHORT).show();
            }
        });
        regButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),User_Registration.class);
            startActivity(intent);
        });
    }

    private void LoginOwner() {
        Retrofit retrofitRequest = new Retrofit.Builder()
                .baseUrl(UserService.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final UserService request = retrofitRequest.create(UserService.class);

        Call<User> call = request.login(sendData);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Log.d("TAG", "onResponse: "+response.body().getToken());
                    progressBar.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = getSharedPreferences(MY_User, MODE_PRIVATE).edit();
                    user_id = response.body().get_id();
                    editor.putString("user_id",response.body().get_id());
                    editor.putString("token",response.body().getToken());
                    editor.commit();
                    mainActivity();
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mainActivity() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("RES_ID",user_id);
        startActivity(intent);
        lg_Password.setText("");
        lg_userName.setText("");
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mobile = getIntent().getStringExtra("userMobile");
        pass = getIntent().getStringExtra("userPass");

        SharedPreferences prefs = getSharedPreferences(MY_User,MODE_PRIVATE);
        String phone = prefs.getString("phone",null);
        String password = prefs.getString("password",null);
        user_id = prefs.getString("user_id",null);
        token = prefs.getString("token",null);

        if (phone != null && password != null){
            sendData.put("phone",phone);
            sendData.put("password",password);
            progressBar.setVisibility(View.VISIBLE);
            LoginOwner();
        }else if (mobile != null && pass != null){
            sendData.put("phone",mobile);
            sendData.put("password",pass);
            progressBar.setVisibility(View.VISIBLE);
            LoginOwner();
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }
}