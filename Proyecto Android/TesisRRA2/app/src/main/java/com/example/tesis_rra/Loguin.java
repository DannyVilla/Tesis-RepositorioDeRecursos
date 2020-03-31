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

public class Loguin extends AppCompatActivity {

    private EditText TextEmail;
    private EditText TextContrasena;
    private Button btnIniciarAlumno;
    private Button btnIniciarMaestro;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    //Declaración de un objeto FirebaseAuth
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loguin);

        //Inicializamos el objeto FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        TextEmail = (EditText) findViewById(R.id.editTextEmail1);
        TextContrasena = (EditText) findViewById(R.id.editTextContrasena);

        btnIniciarAlumno = (Button) findViewById(R.id.buttonIniciarSesion);
        btnIniciarMaestro = (Button) findViewById(R.id.buttonIniciarSesionMaestro);
        btnRegistrar = (Button) findViewById(R.id.buttonRegistrarte);

        progressDialog = new ProgressDialog(this);

       // btnRegistrar.setOnClickListener(this);




    }

    private void registrarUsuario(){
        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String contrasena = TextContrasena.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacias
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un Email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(contrasena)){
            Toast.makeText(this,"Se debe ingresar una contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Loguin.this,"Se ha registrado el email",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Loguin.this,"No se pudo registrar el usuario",Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();
                    }
                });

    }

    public void onClick(View view){
        registrarUsuario();

    }



    public void Siguiente(View view){
        Intent siguiente = new Intent(this, Home.class);
        startActivity(siguiente);
    }



}
