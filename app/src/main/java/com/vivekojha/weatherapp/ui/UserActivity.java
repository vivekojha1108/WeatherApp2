package com.vivekojha.weatherapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vivekojha.weatherapp.MainActivity;
import com.vivekojha.weatherapp.R;

public class UserActivity extends AppCompatActivity {

    Button start;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        start = findViewById(R.id.btn_start);
        editText = findViewById(R.id.et_name);


        final LocationManager manager = (LocationManager) UserActivity.this.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps(); //for setting GPS on

        }


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.toString().isEmpty()) {
                    Toast.makeText(UserActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(UserActivity.this, MainActivity.class);

                    intent.putExtra("userName", editText.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setMessage("Your GPS Location seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}