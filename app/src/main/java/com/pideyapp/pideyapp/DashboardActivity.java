package com.pideyapp.pideyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pideyapp.pideyapp.model.Restaurant;
import com.pideyapp.pideyapp.util.AdapterRestaurants;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    SearchView searchView;

    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    RecyclerView recyclerView;
    AdapterRestaurants adapterRestaurants;
    ArrayList<Restaurant> restaurantArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        this.searchView = findViewById(R.id.svSearch);

        this.mAuth = FirebaseAuth.getInstance();
        this.initDB();

        recyclerView = findViewById(R.id.rvRestaurantList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantArrayList = new ArrayList<>();
        adapterRestaurants = new AdapterRestaurants(this, restaurantArrayList);
        recyclerView.setAdapter(adapterRestaurants);

        this.databaseReference.child("Restaurant").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                    restaurantArrayList.add(restaurant);
                }
                adapterRestaurants.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "Error cargando restaurantes", Toast.LENGTH_SHORT).show();
            }
        });

        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return true;
            }
        });
    }

    public void initDB() {
        FirebaseApp.initializeApp(this);
        this.firebaseDataBase = FirebaseDatabase.getInstance();
        this.databaseReference = this.firebaseDataBase.getReference();

        Toast.makeText(this, "Inicializando la base de datos.", Toast.LENGTH_SHORT).show();
    }

    public void buscar(String s) {
        ArrayList<Restaurant> restaurantsTemp = new ArrayList<>();

        for (Restaurant obj : restaurantArrayList) {
            if (obj.getCiudad().toLowerCase().contains(s.toLowerCase())) {
                restaurantsTemp.add(obj);
            }
        }
        AdapterRestaurants adapter = new AdapterRestaurants(this, restaurantsTemp);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dashboard_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String userId = this.mAuth.getCurrentUser().getUid();
        int idItem = item.getItemId();

        switch (idItem) {
            case R.id.menuOpcionPerfil:

                DatabaseReference usuarios = databaseReference.child("User").child(userId);
                DatabaseReference restaurantes = databaseReference.child("Restaurant").child(userId);

                usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            //Toast.makeText(DashboardActivity.this, "Es usuario", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DashboardActivity.this, PerfilActivity.class));
                        } else {
                            restaurantes.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        //Toast.makeText(DashboardActivity.this, "Es restaurante", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(DashboardActivity.this, PerfilRActivity.class));
                                    } else {
                                        Toast.makeText(DashboardActivity.this, "Error. No se encuenta ningun usuario ni restaurante", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(DashboardActivity.this, "Error consultando restaurantes", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DashboardActivity.this, "Error consultando usuarios", Toast.LENGTH_SHORT).show();
                    }
                });

                //startActivity(new Intent(DashboardActivity.this, PerfilActivity.class));
                break;

            case R.id.menuOpcionLogout:
                this.mAuth.signOut();
                finish();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}