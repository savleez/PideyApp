package com.pideyapp.pideyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);
    }
    public void usuario(View view){
        Intent user = new Intent(RegisterMenuActivity.this,RegisterUserActivity.class);
        startActivity(user);
    }

    public void restaurante(View view){
        Intent restau = new Intent(RegisterMenuActivity.this,RegisterRestaurantActivity.class);
        startActivity(restau);
    }
}