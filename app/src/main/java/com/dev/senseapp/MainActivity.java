package com.dev.senseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnHardware;
    Button btnSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnHardware = findViewById(R.id.btnHardware);
        btnSensor = findViewById(R.id.btnSensor);

        btnHardware.setOnClickListener(v -> goToHardwareActivity());
        btnSensor.setOnClickListener(v -> goToSensorActivity());

        //btnSensor.setOnClickListener(v -> goToActivity(SensorActivity));
    }

    private void goToHardwareActivity() {
        Intent intent = new Intent(this, HardwareActivity.class);
        startActivity(intent);
    }

    private void goToSensorActivity() {
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }

/*    private void goToActivity(AppCompatActivity activity) {
        Intent intent = new Intent(this, activity.class);
        startActivity(intent);
    }*/
}