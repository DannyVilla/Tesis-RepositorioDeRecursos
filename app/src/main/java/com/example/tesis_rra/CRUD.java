package com.example.tesis_rra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tesis_rra.ListViewAlumnos.Adaptador;
import com.example.tesis_rra.ListViewAlumnos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CRUD extends AppCompatActivity {

    RecyclerView rv;
    List<Usuario> usuarios;

    Adaptador adaptador;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_r_u_d);

        rv = findViewById(R.id.recyclerviewAlumnos);

        rv.setLayoutManager(new LinearLayoutManager(this));

        usuarios = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adaptador = new Adaptador(usuarios);
        rv.setAdapter(adaptador);



        database.getReference().getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarios.removeAll(usuarios);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    usuarios.add(usuario);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}
