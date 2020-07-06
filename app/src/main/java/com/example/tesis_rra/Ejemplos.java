package com.example.tesis_rra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Ejemplos extends AppCompatActivity {


    EditText eTitulo, eDescripcion, eLink;
    Button eGuardarBtn;
    Button eListBtn;

    ProgressDialog pd;

    //Firestore instance
    FirebaseFirestore db;

    String pId, pTitle, pDescription, pLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplos);
        //ActionBar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Agregar Objeto de Aprendizaje");



        eTitulo = findViewById(R.id.titleEt);
        eDescripcion = findViewById(R.id.descriptionEt);
        eLink =findViewById(R.id.linkEt);

        eGuardarBtn = findViewById(R.id.saveBtn);
        eListBtn = findViewById(R.id.listBtn);

        //Actualizar Elementos
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //Actualizar

            actionBar.setTitle("Actualizar Objeto");
            eGuardarBtn.setText("Actualizar");
            //get data
            pId = bundle.getString("pId");
            pTitle = bundle.getString("pTitle");
            pDescription = bundle.getString("pDescription");
            pLink = bundle.getString("pLink");
            //set data
            eTitulo.setText(pTitle);
            eDescripcion.setText(pDescription);
            eLink.setText(pLink);

        }else{
            //New Data
            actionBar.setTitle("Agregar Objeto");
            eGuardarBtn.setText("Guardar");
        }

        //progress dialog
        pd = new ProgressDialog(this);

        //firestore
        db = FirebaseFirestore.getInstance();

        //Button
        eGuardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                if(bundle != null){
                    //Actualizando
                    //input data
                    String id = pId;
                    String title = eTitulo.getText().toString().trim();
                    String description = eDescripcion.getText().toString().trim();
                    String link = eLink.getText().toString().trim();
                    //finction call to update data

                    updateData(id, title, description, link);

                }else{
                    //Agregando nuevo objeto

                    //input data
                    String titulo = eTitulo.getText().toString().trim();
                    String descripcion = eDescripcion.getText().toString().trim();
                    String link = eLink.getText().toString().trim();

                    //función para llamar al metodo cargar datos
                    CrearObjeto(titulo,descripcion,link);

                }



            }
        });

        //Boton List
        eListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ejemplos.this, ListActivity.class));
                finish();
            }
        });



    }

    private void updateData(String id, String title, String description, String link) {
        pd.setTitle("Actualizando Objeto...");
        pd.show();

        db.collection("Documents").document(id)
                .update("titulo", title, "search", title.toLowerCase(),
                        "descripción", description, "link", link)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when update successfully
                        pd.dismiss();
                        Toast.makeText(Ejemplos.this, "Actualizado...", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error
                        pd.dismiss();
                        Toast.makeText(Ejemplos.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });

    }

    private void CrearObjeto(String titulo, String descripcion,String link) {

        pd.setTitle("Añadiendo Objeto a la Base de Datos");
        pd.show();

        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id); //Id del dato
        doc.put("titulo", titulo);
        doc.put("search", titulo.toLowerCase());
        doc.put("descripción", descripcion);
        doc.put("link",link);

        //Agregar datos a firestore
        db.collection("Documents").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(Ejemplos.this, "Subido", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(Ejemplos.this, e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

    }


    //menu

}
