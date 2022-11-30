package com.pideyapp.pideyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarfirebase();
    }

    public void registrarme(View view){
        Intent registro = new Intent(LoginActivity.this, RegisterMenuActivity.class);
        startActivity(registro);
    }

    public void iniciousuario(View view){
        Intent inusu = new Intent(LoginActivity.this,RestaurantActivity.class);
        startActivity(inusu);
    }

    private void inicializarfirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        Toast.makeText(this, "Base de datos Inicializada", Toast.LENGTH_SHORT).show();
    }
}