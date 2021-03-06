package com.example.tesis_rra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Lista de Objetos de Aprendizaje");




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
                        modelObjetoList.clear();
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

    public void EliminarObjeto(int index){
        //set titulo of progress dialog
        pd.setTitle("Eliminando Datos");
        //show progress dialog
        pd.show();

        db.collection("Documents").document(modelObjetoList.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when deleted successfully
                        Toast.makeText(ListActivity.this, "Eliminando...", Toast.LENGTH_SHORT).show();
                        //Actualizando datos
                        showData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Called when there is any error
                        //get and show error message
                        pd.dismiss();
                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu_main.xml
        getMenuInflater().inflate(R.menu.manu_main,menu);
        //SearchView
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Called when we press search button
                searchData(s); //finction call with string entered in searchview as parametes
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Called as and when we type even a single letter

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void searchData(String s) {
        pd.setTitle("Buscando...");
        pd.show();

        db.collection("Documents").whereEqualTo("search", s.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //called when searching is succedded
                        modelObjetoList.clear();;
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
                        //when theres is any error
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(ListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handle other menu item click here
        if (item.getItemId() == R.id.action_settings){
            Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}