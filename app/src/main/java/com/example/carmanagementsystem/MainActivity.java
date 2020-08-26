package com.example.carmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.myContainer);
        if(fragment!=null){
            fragment=new HomeFragment();
            fragmentManager.beginTransaction().add(R.id.myContainer,fragment).commit();
        }
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId()){
                case R.id.HomeButton: fragment=new HomeFragment();
                break;
                case R.id.mapButton: fragment=new MapsFragment();
                break;
                case R.id.history: fragment=new HistoryFragment();
                break;
                case R.id.stats: fragment=new StatsFragment();
                break;}
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                return true;

            }


    };
}