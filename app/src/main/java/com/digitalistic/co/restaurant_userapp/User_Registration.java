package com.digitalistic.co.restaurant_userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalistic.co.restaurant_userapp.Apis.RetrofitClient;
import com.digitalistic.co.restaurant_userapp.Apis.UserService;
import com.digitalistic.co.restaurant_userapp.Apis.model.RegisterJson;
import com.digitalistic.co.restaurant_userapp.Apis.model.RegisterPost;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Registration extends AppCompatActivity {
    TextView goLogin;
    EditText user_name, user_mobile, user_mail,user_password,user_conf_password;
    Button create_button;
    ProgressBar progressBar;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        goLogin = findViewById(R.id.go_login_page_id);
        user_name = findViewById(R.id.user_name_id);
        user_mobile = findViewById(R.id.user_mobile_id);
        user_mail = findViewById(R.id.user_mail_id);
        user_password = findViewById(R.id.user_password_id);
        user_conf_password = findViewById(R.id.user_confirm_pass_id);
        progressBar = findViewById(R.id.progressBar_id);
        create_button = findViewById(R.id.user_signup_button);
        
        
        userService = RetrofitClient.noInterceptor().create(UserService.class);
        
        //button create user 
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(user_name.getText().toString()) && !TextUtils.isEmpty(user_mobile.getText().toString().trim())
                        && !TextUtils.isEmpty(user_mail.getText().toString().trim())
                        && !TextUtils.isEmpty(user_password.getText().toString()) && !TextUtils.isEmpty(user_conf_password.getText().toString())){
                    if (user_mobile.getText().length() == 11 &&
                            user_mobile.getText().toString().contains("017") || user_mobile.getText().toString().contains("018") ||
                            user_mobile.getText().toString().contains("019") || user_mobile.getText().toString().contains("015") ||
                            user_mobile.getText().toString().contains("014") || user_mobile.getText().toString().contains("013") ||
                            user_mobile.getText().toString().contains("016")){
                        if (user_password.getText().toString().matches(user_conf_password.getText().toString())){
                            // api call to create restaurant

                            SignUp();

                        }else {
                            Toast.makeText(getApplicationContext(), "Password did not matches !!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Enter valid number", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });



        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),User_Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void SignUp() {
        progressBar.setVisibility(View.VISIBLE);
        String u_name = user_name.getText().toString();
        String u_mobile = user_mobile.getText().toString().trim();
        String u_email = user_mail.getText().toString().trim();
        String u_password = user_password.getText().toString();

        RegisterJson registerJson = new RegisterJson(u_email,u_password,u_name,u_mobile);
        userService.signup(registerJson).enqueue(new Callback<RegisterPost>() {
            @Override
            public void onResponse(Call<RegisterPost> call, Response<RegisterPost> response) {
                if(response.isSuccessful()){
                    Log.d("signup", "onResponse: "+response.body().getMessage());
                    progressBar.setVisibility(View.GONE);
                    if (response.body().getMessage()!= null){
                        Toast.makeText(getApplicationContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Congratulations!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),User_Login.class);
                        intent.putExtra("userMobile",u_mobile);
                        intent.putExtra("userPass",u_password);
                        startActivity(intent);
                        user_name.setText("");
                        user_mobile.setText("");
                        user_mail.setText("");
                        user_password.setText("");
                        user_conf_password.setText("");
                        finish();
                    }
                }else {
                    try {
                        progressBar.setVisibility(View.GONE);
                        Log.d("signup", "onResponse: "+response.errorBody().string());
                        Toast.makeText(getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterPost> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Network Error...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}