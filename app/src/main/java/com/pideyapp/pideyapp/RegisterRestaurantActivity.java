package com.pideyapp.pideyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegisterRestaurantActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);
    }

    public void btnRegisterCrearR(View view){
        Toast.makeText(this, "CLICK EN CREAR RESTAURANTE", Toast.LENGTH_SHORT).show();
    }
}