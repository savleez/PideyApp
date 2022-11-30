package com.pideyapp.pideyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterMenuActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);
        inicializarfirebase();
    }

    public void usuario(View view){
        Intent user = new Intent(RegisterMenuActivity.this, RegisterUserActivity.class);
        startActivity(user);
    }

    public void restaurante(View view){
        Intent restau = new Intent(RegisterMenuActivity.this,RegisterRestaurantActivity.class);
        startActivity(restau);
    }

    private void inicializarfirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        Toast.makeText(this, "Base de datos Inicializada", Toast.LENGTH_SHORT).show();
    }
}