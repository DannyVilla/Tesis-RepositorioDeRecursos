package com.example.tesis_rra.ListViewObjetos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tesis_rra.Ejemplos;
import com.example.tesis_rra.ListActivity;
import com.example.tesis_rra.R;


import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivity listActivity;
    List<ModelObjeto> modelObjetoList;
    Context context;


    public CustomAdapter(ListActivity listActivity, List<ModelObjeto> modelObjetoList) {
        this.listActivity = listActivity;
        this.modelObjetoList = modelObjetoList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.modelobjeto_layout, viewGroup, false);


        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item click here
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user click item


                //show data in toast on clicking
                String titulo = modelObjetoList.get(position).getTitulo();
                String descripcion = modelObjetoList.get(position).getDescripcion();
                String link = modelObjetoList.get(position).getLink();
                Toast.makeText(listActivity, titulo+"\n"+descripcion+"\n"+link, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, final int position) {
                //this will be called when user long click item

                //Creando un AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(listActivity);
                //Options to display in dialog
                String[] options = {"Actualizar", "Eliminar"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            //Actualizar
                            String id = modelObjetoList.get(position).getId();
                            String title = modelObjetoList.get(position).getTitulo();
                            String description = modelObjetoList.get(position).getDescripcion();
                            String link = modelObjetoList.get(position).getLink();

                            //intent to start activity
                            Intent intent = new Intent(listActivity, Ejemplos.class);
                            //put data in intent
                            intent.putExtra("pId", id);
                            intent.putExtra("pTitle", title);
                            intent.putExtra("pDescription", description);
                            intent.putExtra("pLink", link);
                            //start activity
                            listActivity.startActivity(intent);
                        }
                        if (which ==1){
                            //Eliminar
                            listActivity.EliminarObjeto(position);


                        }
                    }
                }).create().show();
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //bind views / set data
        viewHolder.eTitleOb.setText(modelObjetoList.get(i).getTitulo());
        viewHolder.eDescritionOb.setText(modelObjetoList.get(i).getDescripcion());
        viewHolder.eLinkOb.setText(modelObjetoList.get(i).getLink());

    }

    @Override
    public int getItemCount() {
        return modelObjetoList.size();
    }
}
