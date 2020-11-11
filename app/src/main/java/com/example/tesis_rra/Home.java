package com.example.tesis_rra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.Deque;

public class Home extends AppCompatActivity {
    //Initialize variable
    //Implementación de la barra de navegación
    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque = new ArrayDeque<>(4);
    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize variable
        //Implementación de la barra de navegación
        //Assign variable
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Add home
        integerDeque.push(R.id.bn_home);
        //Load home fragment
        loadFragment(new HomeFragment());
        //Set home as default fragment
        bottomNavigationView.setSelectedItemId(R.id.bn_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //Get selected item id
                        int id = item.getItemId();
                        //Check condition
                        if (integerDeque.contains(id)){
                            //When deque list contains selected id
                            //Check condition
                            if (id == R.id.bn_home){
                                //When selected id is qual to home fragment id
                                //Check condition
                                if (integerDeque.size() != 1){
                                    //When deque list size is not equal to 1
                                    //Check condition
                                    if (flag){
                                        //When flag value is true
                                        //Add home fragment in deque list
                                        integerDeque.addFirst(R.id.bn_home);
                                        //Set flag us equal to false
                                        flag = false;
                                    }
                                }

                            }
                            //Remove selected id from deque list
                            integerDeque.remove(id);
                        }
                        //Push selected id in deque list
                        integerDeque.push(id);
                        //Load fragment
                        loadFragment(getFragment(item.getItemId()));
                        //return true
                        return true;
                    }
                }
        );



    }

    private Fragment getFragment(int itemId) {
        switch (itemId){
            case R.id.bn_home:
                //Set checked dashboard fragment
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                //return home fragment
                return new HomeFragment();
            //Perfil
            case R.id.bn_Perfil:
                //Set checked perfil fragment
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                //return perfil fragment
                return new PerfilFragment();
            //Ejemplos
            case R.id.bn_Repositorios:
                //Set checked ejemplos fragment
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                //return ejemplos fragment
                return new EjemplosFragment();
            //Información
            case R.id.bn_info:
                //Set checked info fragment
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                //return inofrmación fragment
                return new InfoFragment();
        }
        //Set checked default home fragment
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        //Return home fragment
        return  new HomeFragment();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment,fragment,fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        //Pop to previous fragment
        integerDeque.pop();
        //Check condition
        if (!integerDeque.isEmpty()){
            //When deque list is not empty
            //Load fragment
            loadFragment(getFragment(integerDeque.peek()));
        }else{
            //When deque list is empty
            //Finish activity
            finish();
        }
    }
}