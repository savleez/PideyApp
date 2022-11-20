package com.pideyapp.pideyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void btnRegisterUser(View view) {
        Intent intentRegisterUser = new Intent(this, RegisterUserActivity.class);
        startActivity(intentRegisterUser);
    }

    public void btnRegisterRestaurant(View view) {
        Intent intentRegisterRestaurant = new Intent(this, RegisterRestaurantActivity.class);
        startActivity(intentRegisterRestaurant);
    }

}