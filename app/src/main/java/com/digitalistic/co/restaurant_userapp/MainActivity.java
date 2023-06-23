package com.digitalistic.co.restaurant_userapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.digitalistic.co.restaurant_userapp.Fragment.HomeFragment;
import com.digitalistic.co.restaurant_userapp.Fragment.MoreFragment;
import com.digitalistic.co.restaurant_userapp.Fragment.OfferFragment;
import com.digitalistic.co.restaurant_userapp.Fragment.OrderFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {
    public static final String MY_User = "com.digitalistic.co.restaurant_userapp";
    Button logoutButton;
    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_nav);

        if (savedInstanceState == null){
            bottomNav.setItemSelected(R.id.home,true);

            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,homeFragment)
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.order:
                        fragment = new OrderFragment();
                        break;
                    case R.id.offer:
                        fragment = new OfferFragment();
                        break;
                    case R.id.more:
                        fragment = new MoreFragment();
                        break;

                }

                if (fragment != null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }else {
                    Log.e("TAG", "Error in creating fragment");
                }
            }
        });


    }


}