package com.pideyapp.pideyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
        listarDatos();


        listv_registeruser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                registeruserSelected = (RegisterUser) adapterView.getItemAtPosition(i);
                apellidos.setText(registeruserSelected.getApellidos());
                nombres.setText(registeruserSelected.getNombres());
                telefono.setText(registeruserSelected.getTelefono());
                email.setText(registeruserSelected.getCorreo());
                contraseña.setText(registeruserSelected.getContraseña());
            }
        });
    }

    private void inicializarfirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        Toast.makeText(this, "Base de datos Inicializada", Toast.LENGTH_SHORT).show();
    }

    public void cliccrear (View view){
        String nombre = nombres.getText().toString();
        String apellido = apellidos.getText().toString();
        String tel = telefono.getText().toString();
        String mail = email.getText().toString();
        String password = contraseña.getText().toString();

        if (nombre.isEmpty() || (apellido.isEmpty()) || (tel.isEmpty()) || (mail.isEmpty()) || (password.isEmpty())) {
            validacion();

        }
        else {
            RegisterUser objRegisterUser = new RegisterUser();
            objRegisterUser.setApellidos(apellido);
            objRegisterUser.setNombres(nombre);
            objRegisterUser.setTelefono(Integer.parseInt(tel));
            objRegisterUser.setCorreo(mail);
            objRegisterUser.setContraseña(password);
            databaseReference.child("RegisterUser").child(objRegisterUser.getCorreo()).setValue(objRegisterUser);
            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
            limpiarCajas();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        String nombre = nombres.getText().toString();
        String apellido = apellidos.getText().toString();
        Integer tel = Integer.parseInt(telefono.getText().toString());
        String mail = email.getText().toString();
        String password = contraseña.getText().toString();

        switch (item.getItemId()) {
            case R.id.btnRegisterCrear:
                if (nombre.isEmpty() || (apellido.isEmpty()) || (mail.isEmpty()) || (password.isEmpty())) {
                    validacion();
                }
            else {
                RegisterUser objRegisterUser = new RegisterUser();
                objRegisterUser.setApellidos(apellido);
                objRegisterUser.setNombres(nombre);
                objRegisterUser.setTelefono(tel);
                objRegisterUser.setCorreo(mail);
                objRegisterUser.setContraseña(password);
                databaseReference.child("RegisterUser").child(objRegisterUser.getApellidos()).setValue(objRegisterUser);
                    Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
            break;



            case R.id.BotonRegresar:
                Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();
                Intent BotonRegresar = new Intent(this,RegisterMenuActivity.class);
                startActivity(BotonRegresar);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    public void limpiarCajas(){
        nombres.setText("");
        apellidos.setText("");
        telefono.setText("");
        email.setText("");
        contraseña.setText("");
    }

    public void validacion(){
            String nombre = nombres.getText().toString();
            String apellido = apellidos.getText().toString();
            String tel = telefono.getText().toString();
            String mail = email.getText().toString();
            String password = contraseña.getText().toString();
            if (nombre.isEmpty()){
                this.nombres.setError("Campo Obligatorio");
            }
            else if (apellido.isEmpty()){
                this.apellidos.setError("Campo Obligatorio");
            }

            else if (tel.isEmpty()){
                this.telefono.setError("Campo Obligatorio");
            }

            else if (mail.isEmpty()){
                this.email.setError("Campo Obligatorio");
            }

            else if (password.isEmpty()){
                this.contraseña.setError("Campo Obligatorio");
            }
    }

    public void listarDatos() {
        databaseReference.child("RegisterUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listRegisterUser.clear();
                for (DataSnapshot objSnapShot : snapshot.getChildren()) {
                    RegisterUser e = objSnapShot.getValue(RegisterUser.class);
                    listRegisterUser.add(e);
                    arrayAdapterRegisterUser = new ArrayAdapter<RegisterUser>(RegisterUserActivity.this, android.R.layout.simple_list_item_1, listRegisterUser);
                    listv_registeruser.setAdapter(arrayAdapterRegisterUser);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
