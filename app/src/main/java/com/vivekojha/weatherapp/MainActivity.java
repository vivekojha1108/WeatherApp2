package com.vivekojha.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vivekojha.weatherapp.nav.CurrentTemperatureFragment;
import com.vivekojha.weatherapp.nav.SearchCityFragment;
import com.vivekojha.weatherapp.nav.SettingsFragment;
import com.vivekojha.weatherapp.nav.SevenDaysData;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigationBar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CurrentTemperatureFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_currentTemp:
                    selectedFragment = new CurrentTemperatureFragment();
                    break;
                case R.id.nav_search :
                    selectedFragment = new SearchCityFragment();
                    break;
                case R.id.nav_SevenDaysData:
                    selectedFragment = new SevenDaysData();
                    break;
                case R.id.nav_settings:
                    selectedFragment = new SettingsFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };


}