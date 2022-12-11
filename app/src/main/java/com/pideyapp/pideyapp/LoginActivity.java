package com.pideyapp.pideyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pideyapp.pideyapp.model.Usuario;

public class LoginActivity extends Activity {

    EditText txtEmail, txtPassword;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.txtEmail = findViewById(R.id.txtEmail);
        this.txtPassword = findViewById(R.id.txtPassword);
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = this.mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        }
    }

    private void validacion() {
        String email = this.txtEmail.getText().toString();
        String password = this.txtPassword.getText().toString();

        if (email.isEmpty()) {
            this.txtEmail.setError("El email es requerido");
        }
        if (password.isEmpty()) {
            this.txtPassword.setError("La contraseña es requerido");
        }
    }

    public void btn_login(View view) {
        String email = this.txtEmail.getText().toString();
        String password = this.txtPassword.getText().toString();
        if ((email.isEmpty()) || (password.isEmpty())) {
            this.validacion();
            return;
        } else {
            this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        finish();
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error iniciando sesion", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void btn_register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterMenuActivity.class));
        finish();
    }
}