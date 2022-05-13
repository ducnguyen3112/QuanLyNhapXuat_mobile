package com.example.quanlynhapxuat.activity.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.fragment.ExportFragment;
import com.example.quanlynhapxuat.fragment.HomeFragment;
import com.example.quanlynhapxuat.fragment.ImportFragment;
import com.example.quanlynhapxuat.fragment.MoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    HomeFragment homeFragment=new HomeFragment();
    ExportFragment exportFragment=new ExportFragment();
    ImportFragment importFragment = new ImportFragment();
    MoreFragment moreFragment = new MoreFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,homeFragment).commit();

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,homeFragment).commit();
                        return true;
                    case R.id.menu_export:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,exportFragment).commit();
                        return true;
                    case R.id.menu_import:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main,importFragment).commit();
                        return true;
                    case R.id.menu_more:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, moreFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
    private void initViews(){
        bottomNav=findViewById(R.id.bottom_navigation);
    }
}