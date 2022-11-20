package com.pideyapp.pideyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void registrarme(View view){
        Intent registro = new Intent(LoginActivity.this,RegisterMenuActivity.class);
        startActivity(registro);
    }

    public void iniciousuario(View view){
        Intent inusu = new Intent(LoginActivity.this,InicioUsuarioActivity.class);
        startActivity(inusu);
}