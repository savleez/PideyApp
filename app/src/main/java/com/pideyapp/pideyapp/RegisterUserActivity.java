package com.pideyapp.pideyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegisterUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
    }

    public void btnRegisterCrear(View view){
        Toast.makeText(this, "CLICK EN CREAR USUARIO", Toast.LENGTH_SHORT).show();
    }
}