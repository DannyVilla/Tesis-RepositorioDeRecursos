package com.example.tesis_rra.ListViewAlumnos;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tesis_rra.R;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.AlumnosViewHolder>{

    List<Usuario> usuarios;

    public Adaptador(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public AlumnosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler, parent, false);
        AlumnosViewHolder holder = new AlumnosViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnosViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.textViewNombre.setText(usuario.getNombre());
        holder.textViewCorreo.setText(usuario.getCorreo());

    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class AlumnosViewHolder extends RecyclerView.ViewHolder{

        TextView textViewNombre;
        TextView textViewCorreo;

        public AlumnosViewHolder(View itemView){
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textView_nombre);
            textViewCorreo = itemView.findViewById(R.id.textView_correo);
        }
    }

}
