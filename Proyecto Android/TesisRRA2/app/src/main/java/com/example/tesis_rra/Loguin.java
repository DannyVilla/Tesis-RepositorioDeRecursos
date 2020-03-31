package com.example.tesis_rra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Loguin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_loguin);
    }
    public void Siguiente(View view){
        Intent siguiente = new Intent(this, Home.class);
        startActivity(siguiente);


    }

}
