package com.digitalistic.co.restaurant_userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_Login extends AppCompatActivity {
    TextView regButton;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        regButton = findViewById(R.id.register_button_id);
        loginButton = findViewById(R.id.login_button_id);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        });
        regButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),User_Registration.class);
            startActivity(intent);
        });
    }
}