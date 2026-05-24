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
    Button btnTelephony;

    Button btnWifi;

    Button btnAudio;
    Button btnTest;

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
        btnTelephony = findViewById(R.id.btnTelephony);
        btnAudio = findViewById(R.id.btnAudio);
        btnWifi =findViewById(R.id.btnWifi);
        btnTest=findViewById(R.id.btnTest);


        btnHardware.setOnClickListener(v -> goToActivity(HardwareActivity.class));
        btnSensor.setOnClickListener(v -> goToActivity(SensorActivity.class));
        btnTelephony.setOnClickListener(V -> goToActivity(TelephonyActivity.class));
        btnAudio.setOnClickListener(V -> goToActivity(AudioActivity.class));
        btnWifi.setOnClickListener(V ->goToActivity(WifiActiviy.class));
        btnTest.setOnClickListener(V->goToActivity(TestActivity.class));
    }

    private void goToActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    /*
    private void goToHardwareActivity() {
        Intent intent = new Intent(this, HardwareActivity.class);
        startActivity(intent);
    }

    private void goToSensorActivity() {
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }

    private void goToTelephonyActivity() {
        Intent intent = new Intent(this, TelephonyActivity.class);
        startActivity(intent);
    }


    private void goToAudioActivity() {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
    }
    */
}