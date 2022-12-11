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
import com.google.firebase.database.Query;
import com.pideyapp.pideyapp.model.Usuario;

public class RegisterUserActivity extends Activity {

    private EditText txtNombres;
    private EditText txtApellidos;
    private EditText txtDocumento;
    private EditText txtTelefono;
    private EditText txtEmail;
    private EditText txtPassword;

    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        this.txtNombres = findViewById(R.id.txtRegisterNombres);
        this.txtApellidos = findViewById(R.id.txtRegisterApellidos);
        this.txtDocumento = findViewById(R.id.txtRegisterDocumento);
        this.txtTelefono = findViewById(R.id.txtRegisterTelefono);
        this.txtEmail = findViewById(R.id.txtRegisterEmail);
        this.txtPassword = findViewById(R.id.txtRegisterPassword);

        this.mAuth = FirebaseAuth.getInstance();
        this.initDB();
    }

    public void initDB() {
        FirebaseApp.initializeApp(this);
        this.firebaseDataBase = FirebaseDatabase.getInstance();
        this.databaseReference = this.firebaseDataBase.getReference();

        Toast.makeText(this, "Inicializando la base de datos.", Toast.LENGTH_SHORT).show();
    }

    public void btnRegisterCrear(View view){
        //Toast.makeText(this, "CLICK EN CREAR USUARIO", Toast.LENGTH_SHORT).show();

        String nombres = this.txtNombres.getText().toString();
        String apellidos = this.txtApellidos.getText().toString();
        String documento = this.txtDocumento.getText().toString();
        String telefono = this.txtTelefono.getText().toString();
        String email = this.txtEmail.getText().toString();
        String password = this.txtPassword.getText().toString();

        if (nombres.isEmpty() || (apellidos.isEmpty()) || (telefono.isEmpty()) || (email.isEmpty()) || (password.isEmpty())) {
            this.validacion();
            return;
        } else {
            Usuario objRegisterUser = new Usuario();
            objRegisterUser.setApellidos(apellidos);
            objRegisterUser.setNombres(nombres);
            objRegisterUser.setDocumento(documento);
            objRegisterUser.setTelefono(telefono);
            objRegisterUser.setEmail(email);
            objRegisterUser.setPassword(password);

            this.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    databaseReference.child("User").child(mAuth.getCurrentUser().getUid()).setValue(objRegisterUser);
                    finish();
                    startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterUserActivity.this, "Error creando nuevo usuario", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void validacion() {
        String nombres = this.txtNombres.getText().toString();
        String apellidos = this.txtApellidos.getText().toString();
        String documento = this.txtDocumento.getText().toString();
        String telefono = this.txtTelefono.getText().toString();
        String email = this.txtEmail.getText().toString();
        String password = this.txtPassword.getText().toString();

        if (nombres.isEmpty()) {
            this.txtNombres.setError("Los nombres son requeridos");
        }
        if (apellidos.isEmpty()) {
            this.txtApellidos.setError("Los apellidos son requeridos");
        }
        if (documento.isEmpty()) {
            this.txtDocumento.setError("El documento es requerido");
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
        this.txtNombres.setText("");
        this.txtApellidos.setText("");
        this.txtDocumento.setText("");
        this.txtEmail.setText("");
        this.txtTelefono.setText("");
        this.txtPassword.setText("");
    }

}