package com.pideyapp.pideyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PerfilRActivity extends AppCompatActivity {

    private EditText txtNombre, txtNit, txtCiudad, txtTelefono, txtEmail, txtPassword;

    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private String restaurantPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_ractivity);

        this.txtNombre = findViewById(R.id.txtPerfilRNombre);
        this.txtNit = findViewById(R.id.txtPerfilRNit);
        this.txtCiudad = findViewById(R.id.txtPerfilRCiudad);
        this.txtTelefono = findViewById(R.id.txtPerfilRTelefono);
        this.txtEmail = findViewById(R.id.txtPerfilREmail);
        this.txtPassword = findViewById(R.id.txtPerfilRPassword);

        this.mAuth = FirebaseAuth.getInstance();
        this.initDB();
        this.updateFields();
    }

    public void initDB() {
        FirebaseApp.initializeApp(this);
        this.firebaseDataBase = FirebaseDatabase.getInstance();
        this.databaseReference = this.firebaseDataBase.getReference();

        Toast.makeText(this, "Inicializando la base de datos.", Toast.LENGTH_SHORT).show();
    }

    public void updateFields() {
        String userId = this.mAuth.getCurrentUser().getUid();
        this.databaseReference.child("Restaurant").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String nit = snapshot.child("nit").getValue().toString();
                    String ciudad = snapshot.child("ciudad").getValue().toString();
                    String telefono = snapshot.child("telefono").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    restaurantPassword = snapshot.child("password").getValue().toString();

                    txtNombre.setText(nombre);
                    txtCiudad.setText(ciudad);
                    txtNit.setText(nit);
                    txtTelefono.setText(telefono);
                    txtEmail.setText(email);
                    txtPassword.setText("*********");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PerfilRActivity.this, "Error obteniendo informacion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnRActualizar(View view) {
        // Toast.makeText(this, "Click en actualizar", Toast.LENGTH_SHORT).show();

        String userId = this.mAuth.getCurrentUser().getUid();
        String nombre = this.txtNombre.getText().toString();
        String nit = this.txtNit.getText().toString();
        String ciudad = this.txtCiudad.getText().toString();
        String telefono = this.txtTelefono.getText().toString();
        String email = this.txtEmail.getText().toString();
        String password = this.txtPassword.getText().toString();

        if (nombre.isEmpty() || (nit.isEmpty()) || (ciudad.isEmpty()) || (telefono.isEmpty()) || (email.isEmpty()) || (password.isEmpty()) ) {
            Toast.makeText(this, "Error al actualizar la informacion. Valide sus datos", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (password.equals("*********")) {
                password = restaurantPassword;
            }
            Map<String, Object> restauranteMap = new HashMap<>();
            restauranteMap.put("nombre", nombre);
            restauranteMap.put("nit", nit);
            restauranteMap.put("ciudad", ciudad);
            restauranteMap.put("telefono", telefono);
            restauranteMap.put("email", email);
            restauranteMap.put("password", password);

            FirebaseUser restaurant = this.mAuth.getCurrentUser();
            restaurant.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        databaseReference.child("Restaurant").child(userId).updateChildren(restauranteMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(PerfilRActivity.this, "Información actualizada correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(PerfilRActivity.this, "Error actualizando la información Database", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(PerfilRActivity.this, "Error actualizando la informacion Authentication", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void btnREliminar(View view) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("¿Seguro que desea eliminar el restaurante?");
        alerta.setTitle("ALERTA");
        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminarRestaurante();
            }
        });

        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = alerta.create();
        dialog.show();
    }

    public void eliminarRestaurante() {
        String userId = this.mAuth.getCurrentUser().getUid();
        FirebaseUser restaurant = this.mAuth.getCurrentUser();
        restaurant.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ELIMINAR REGISTRO DE REALTIME DATABASE
                databaseReference.child("Restaurant").child(userId).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(PerfilRActivity.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PerfilRActivity.this, LoginActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PerfilRActivity.this, "Error eliminando de Realtime Database", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PerfilRActivity.this, "Error eliminando de Authentication", Toast.LENGTH_SHORT).show();
            }
        });
    }
}