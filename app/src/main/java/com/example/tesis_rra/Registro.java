package com.example.tesis_rra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText TextUsuarioR;
    private EditText TextEmailR;
    private EditText TextContrasenaR;
    private Button ButtonRegistrarseR;

    //Variables para los datos que se registran
    private String nombre =  " ";
    private String email = " " ;
    private String contrasena = " ";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();  //Realtime Database

        TextUsuarioR = (EditText) findViewById(R.id.editTextUsuarioR);
        TextEmailR = (EditText) findViewById(R.id.editTextEmailR);
        TextContrasenaR = (EditText) findViewById(R.id.editTextContrasenaR);
        ButtonRegistrarseR = (Button) findViewById(R.id.buttonRegistrarseR);

        ButtonRegistrarseR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = TextUsuarioR.getText().toString();
                email = TextEmailR.getText().toString();
                contrasena = TextContrasenaR.getText().toString();

                if(!nombre.isEmpty() &&  !email.isEmpty() && !contrasena.isEmpty()){
                    if(contrasena.length() >= 6){
                        registrarUsuario();
                    }else{
                        Toast.makeText(Registro.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(Registro.this,"Debe completar todos los campos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void registrarUsuario(){
        mAuth.createUserWithEmailAndPassword(email,contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", nombre);
                    map.put("email", email);
                    map.put("password", contrasena);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.getRoot().child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(Registro.this, Loguin.class));
                                Toast.makeText(Registro.this, "Usuario Creado Correctamente", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Registro.this,"Intentalo nuevamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Registro.this,"No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
