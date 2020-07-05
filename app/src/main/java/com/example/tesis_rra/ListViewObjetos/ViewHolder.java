package com.example.tesis_rra.ListViewObjetos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tesis_rra.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView eTitleOb, eDescritionOb, eLinkOb;
    View eView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        eView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());

            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

        //initialize vies with model_layout.xml
        eTitleOb = itemView.findViewById(R.id.rTitleOb);
        eDescritionOb = itemView.findViewById(R.id.rDescriptionOb);
        eLinkOb = itemView.findViewById(R.id.rLinkOb);
    }

    private ViewHolder.ClickListener mClickListener;
    //interface for click listener
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;


    }

}
