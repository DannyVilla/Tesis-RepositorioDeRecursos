package com.example.tesis_rra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tesis_rra.ListViewObjetos.CustomAdapter;
import com.example.tesis_rra.ListViewObjetos.ModelObjeto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    List<ModelObjeto> modelObjetoList = new ArrayList<>();
    RecyclerView eRecyclerView;
    //layout manager for recycler

    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton eAddBtn;

    //firestore
    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);

        //inti firestore
        db = FirebaseFirestore.getInstance();

        //inicializamos views
        eRecyclerView = findViewById(R.id.recycler_view_objetos);
        eAddBtn = findViewById(R.id.addBtn);

        //set recyclerview propiedades
        eRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        eRecyclerView.setLayoutManager(layoutManager);

        //init progress Dialog
        pd = new ProgressDialog(this);
        
        //show data in recyclerView
        showData();
        eAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, Ejemplos.class ));
                finish();

            }
        });

    }

    private void showData() {
        //set titulo of progress dialog
        pd.setTitle("Cargando Datos");
        //show progress dialog
        pd.show();

        db.collection("Documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //called when data is retrieved
                        pd.dismiss();
                        //show data
                        for (DocumentSnapshot doc: task.getResult()){
                            ModelObjeto modelObjeto = new ModelObjeto(doc.getString("id"),
                                    doc.getString("titulo"),
                                    doc.getString("descripción"),
                                    doc.getString("link"));
                            modelObjetoList.add(modelObjeto);

                        }
                        //adapter
                        adapter = new CustomAdapter(ListActivity.this, modelObjetoList);

                        //set adapter to recyclerview
                        eRecyclerView.setAdapter(adapter);



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Called when there is any error while retireving
                        pd.dismiss();

                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}