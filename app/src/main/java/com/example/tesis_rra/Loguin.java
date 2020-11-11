package com.example.tesis_rra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Loguin extends AppCompatActivity {

    private EditText TextEmail;
    private EditText TextContrasena;
    private Button btnIniciarSesion;
    private Button btnIniciarMaestro;
    private Button btnRegistrar;
    private Button btnHome;


    //Declaración de un objeto FirebaseAuth
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    //Variables para iniciar sesion
    private String email = " ";
    private String contrasena = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loguin);

        //Inicializamos el objeto FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        TextEmail = (EditText) findViewById(R.id.editTextEmailR);
        TextContrasena = (EditText) findViewById(R.id.editTextContrasenaR);
        btnIniciarSesion = (Button) findViewById(R.id.buttonIniciarSesion);
        btnRegistrar = (Button) findViewById(R.id.buttonRegistrarte);
        btnIniciarMaestro = (Button) findViewById(R.id.buttonIniciarSesionMaestro);

        btnHome = (Button) findViewById(R.id.buttonHome);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Loguin.this, Ejemplos.class));
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = TextEmail.getText().toString();
                contrasena = TextContrasena.getText().toString();

                if(!email.isEmpty() && !contrasena.isEmpty()){
                    loginUser();
                }
                else{
                    Toast.makeText(Loguin.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loginUser(){
        mAuth.signInWithEmailAndPassword(email,contrasena)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Loguin.this, Home.class));
                    Toast.makeText(Loguin.this,"Inicio de sesión correcto",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(Loguin.this,"Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickRegistrarse(View view){
        Intent NextRegistro = new Intent(this, Registro.class);
        startActivity(NextRegistro);
    }

}



