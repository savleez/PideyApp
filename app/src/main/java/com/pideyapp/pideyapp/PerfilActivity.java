package com.pideyapp.pideyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pideyapp.pideyapp.model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {

    private EditText txtNombres, txtApellidos, txtDocumento, txtTelefono, txtEmail, txtPassword;

    private FirebaseDatabase firebaseDataBase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        this.txtNombres = findViewById(R.id.txtPerfilNombres);
        this.txtApellidos = findViewById(R.id.txtPerfilApellidos);
        this.txtDocumento = findViewById(R.id.txtPerfilDocumento);
        this.txtTelefono = findViewById(R.id.txtPerfilTelefono);
        this.txtEmail = findViewById(R.id.txtPerfilEmail);
        this.txtPassword = findViewById(R.id.txtPerfilPassword);

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
        this.databaseReference.child("User").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombres = snapshot.child("nombres").getValue().toString();
                    String apellidos = snapshot.child("apellidos").getValue().toString();
                    String documento = snapshot.child("documento").getValue().toString();
                    String telefono = snapshot.child("telefono").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    userPassword = snapshot.child("password").getValue().toString();

                    txtNombres.setText(nombres);
                    txtApellidos.setText(apellidos);
                    txtDocumento.setText(documento);
                    txtTelefono.setText(telefono);
                    txtEmail.setText(email);
                    txtPassword.setText("*********");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PerfilActivity.this, "Error obteniendo informacion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnActualizar(View view) {
        // Toast.makeText(this, "Click en actualizar", Toast.LENGTH_SHORT).show();

        String userId = this.mAuth.getCurrentUser().getUid();
        String nombres = this.txtNombres.getText().toString();
        String apellidos = this.txtApellidos.getText().toString();
        String documento = this.txtDocumento.getText().toString();
        String telefono = this.txtTelefono.getText().toString();
        String email = this.txtEmail.getText().toString();
        String password = this.txtPassword.getText().toString();

        if (nombres.isEmpty() || (apellidos.isEmpty()) || (documento.isEmpty()) || (telefono.isEmpty()) || (email.isEmpty()) || (password.isEmpty()) ) {
            Toast.makeText(this, "Error al actualizar la informacion. Valide sus datos", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (password.equals("*********")) {
                password = userPassword;
            }
            Map<String, Object> usuarioMap = new HashMap<>();
            usuarioMap.put("nombres", nombres);
            usuarioMap.put("apellidos", apellidos);
            usuarioMap.put("documento", documento);
            usuarioMap.put("telefono", telefono);
            usuarioMap.put("email", email);
            usuarioMap.put("password", password);

            FirebaseUser user = this.mAuth.getCurrentUser();
            user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        databaseReference.child("User").child(userId).updateChildren(usuarioMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(PerfilActivity.this, "Información actualizada correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(PerfilActivity.this, "Error actualizando la información Database", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(PerfilActivity.this, "Error actualizando la informacion Authentication", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void btnEliminar(View view) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage("¿Seguro que desea eliminar el usuario?");
        alerta.setTitle("ALERTA");
        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                eliminarUsuario();
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

    public void eliminarUsuario() {
        String userId = this.mAuth.getCurrentUser().getUid();
        FirebaseUser user = this.mAuth.getCurrentUser();
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ELIMINAR REGISTRO DE REALTIME DATABASE
                databaseReference.child("User").child(userId).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(PerfilActivity.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PerfilActivity.this, "Error eliminando de Realtime Database", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PerfilActivity.this, "Error eliminando de Authentication", Toast.LENGTH_SHORT).show();
            }
        });
    }
}