package com.vivekojha.weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.vivekojha.weatherapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AppLauncher appLauncher = new AppLauncher();
        appLauncher.start();

    }


    private class AppLauncher extends Thread {
        public void run() {
            try {
                int SLEEP_TIMER = 1;
                sleep(1000 * SLEEP_TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(SplashActivity.this, UserActivity.class));
            finish();
        }
    }
}