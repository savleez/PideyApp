package com.pideyapp.pideyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);
    }

    public void btnUsuario(View view) {
        startActivity(new Intent(RegisterMenuActivity.this, RegisterUserActivity.class));
        finish();
    }

    public void btnRestaurante(View view) {
        startActivity(new Intent(RegisterMenuActivity.this, RegisterRestaurantActivity.class));
        finish();
    }

}