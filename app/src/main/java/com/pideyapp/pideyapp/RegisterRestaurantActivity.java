package com.pideyapp.pideyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pideyapp.pideyapp.model.Restaurant;
import com.pideyapp.pideyapp.model.Usuario;

public class RegisterRestaurantActivity extends Activity {

    private EditText txtNombre;
    private EditText txtNit;
    private EditText txtTelefono;
    private EditText txtCiudad;
    private EditText txtEmail;
    private EditText txtPassword;

    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        this.txtNombre = findViewById(R.id.txtRegisterNombreR);
        this.txtNit = findViewById(R.id.txtRegisterNitR);
        this.txtTelefono = findViewById(R.id.txtRegisterTelefonoR);
        this.txtCiudad = findViewById(R.id.txtRegisterCiudadR);
        this.txtEmail = findViewById(R.id.txtRegisterEmailR);
        this.txtPassword = findViewById(R.id.txtRegisterPasswordR);

        this.mAuth = FirebaseAuth.getInstance();
        this.initDB();
    }

    public void initDB() {
        FirebaseApp.initializeApp(this);
        this.firebaseDataBase = FirebaseDatabase.getInstance();
        this.databaseReference = this.firebaseDataBase.getReference();

        Toast.makeText(this, "Inicializando la base de datos.", Toast.LENGTH_SHORT).show();
    }

    public void btnRegisterCrearR(View view){
        // Toast.makeText(this, "CLICK EN CREAR RESTAURANTE", Toast.LENGTH_SHORT).show();

        String nombre = this.txtNombre.getText().toString();
        String nit = this.txtNit.getText().toString();
        String ciudad = this.txtCiudad.getText().toString();
        String telefono = this.txtTelefono.getText().toString();
        String email = this.txtEmail.getText().toString();
        String password = this.txtPassword.getText().toString();

        if (nombre.isEmpty() || (nit.isEmpty()) || (ciudad.isEmpty())|| (telefono.isEmpty()) || (email.isEmpty()) || (password.isEmpty())) {
            this.validacion();
            return;
        } else {
            Restaurant objRegisterRestaurant = new Restaurant();
            objRegisterRestaurant.setNombre(nombre);
            objRegisterRestaurant.setNit(nit);
            objRegisterRestaurant.setCiudad(ciudad);
            objRegisterRestaurant.setTelefono(telefono);
            objRegisterRestaurant.setEmail(email);
            objRegisterRestaurant.setPassword(password);

            this.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    databaseReference.child("Restaurant").child(mAuth.getCurrentUser().getUid()).setValue(objRegisterRestaurant);
                    finish();
                    startActivity(new Intent(RegisterRestaurantActivity.this, LoginActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterRestaurantActivity.this, "Error creando nuevo restaurante", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private void validacion() {
        String nombre = this.txtNombre.getText().toString();
        String nit = this.txtNit.getText().toString();
        String ciudad = this.txtCiudad.getText().toString();
        String telefono = this.txtTelefono.getText().toString();
        String email = this.txtEmail.getText().toString();
        String password = this.txtPassword.getText().toString();

        if (nombre.isEmpty()) {
            this.txtNombre.setError("El nombre es requerido");
        }
        if (nit.isEmpty()) {
            this.txtNit.setError("El NIT es requerido");
        }
        if (ciudad.isEmpty()) {
            this.txtCiudad.setError("La ciudad es requerida");
        }
        if (telefono.isEmpty()) {
            this.txtTelefono.setError("El telefono es requerido");
        }
        if (email.isEmpty()) {
            this.txtEmail.setError("El email es requerido");
        }
        if (password.isEmpty()) {
            this.txtPassword.setError("La contraseña es requerido");
        }
    }

    private void cleanTxtBoxes() {
        this.txtNombre.setText("");
        this.txtNit.setText("");
        this.txtCiudad.setText("");
        this.txtEmail.setText("");
        this.txtTelefono.setText("");
        this.txtPassword.setText("");
    }
}