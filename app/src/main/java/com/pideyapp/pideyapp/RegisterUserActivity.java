package com.pideyapp.pideyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pideyapp.pideyapp.modelo.RegisterUser;

import java.util.ArrayList;
import java.util.List;

public class RegisterUserActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private EditText nombres;
    private EditText apellidos;
    private EditText telefono;
    private EditText email;
    private EditText contraseña;
    private ListView listv_registeruser;
    private final List<RegisterUser> listRegisterUser = new ArrayList<>();
    ArrayAdapter<RegisterUser> arrayAdapterRegisterUser;
    RegisterUser registeruserSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        nombres=(EditText) findViewById(R.id.txtRegisterNombres);
        apellidos=(EditText) findViewById(R.id.txtRegisterApellidos);
        telefono=(EditText) findViewById(R.id.txtRegisterTelefono);
        email=(EditText) findViewById(R.id.txtRegisterEmail);
        contraseña=(EditText) findViewById(R.id.txtRegisterPassword);
        listv_registeruser=(ListView) findViewById(R.id.RegistroUser);
        inicializarfirebase();
    }

    private void inicializarfirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        Toast.makeText(this, "Base de datos Inicializada", Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        String nombre = nombres.getText().toString();
        String apellido = apellidos.getText().toString();
        String tel = telefono.getText().toString();
        String mail = email.getText().toString();
        String password = contraseña.getText().toString();

    }


}