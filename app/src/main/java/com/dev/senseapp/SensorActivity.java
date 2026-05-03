package com.dev.senseapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tvLight;
    private TextView tvAccel;
    private Button btnInitBackgroundProcess;
    private Button btnKeepScreenOn;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accelSensor;

    private PowerManager powerManager;
    private PowerManager.WakeLock screenWakeLock;
    private PowerManager.WakeLock processWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvLight = findViewById(R.id.tvLight);
        tvAccel = findViewById(R.id.tvAccel);
        btnInitBackgroundProcess = findViewById(R.id.btnInitBackgroundProcess);
        btnKeepScreenOn = findViewById(R.id.btnKeepScreenOn);

        btnKeepScreenOn.setOnClickListener(v -> keepScreenOn(btnKeepScreenOn));
        //btnKeepScreenOn.setOnClickListener(v -> initWakeLock());

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);

        initManagers();
    }

    private void initManagers() {
        if (sensorManager == null) {
            tvLight.setText("No se inicializó el sensor manager");
            return;
        }

        if (powerManager == null) {
            return;
        }

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        screenWakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "SenseApp:ScreenWakeLock");
        processWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SenseApp:ProcessWakeLock");

        if (lightSensor == null) {
            tvLight.setText("El dispositivo no tiene sensor de luz.");
            return;
        }

        if (accelSensor == null) {
            tvAccel.setText("El equipo no tiene acelerometro");
        }

        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            float luz = sensorEvent.values[0];
            tvLight.setText("Luz ambiental: " + luz + " lux");
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            tvAccel.setText("Acelerometro: \nx:" + x + "\ny:" + y + "\nz:" + z);
        }

    }

    private void keepScreenOn(Button button) {
        if (screenWakeLock == null) {
            return;
        }

        if (screenWakeLock.isHeld()) {
            screenWakeLock.release();
            button.setText("Mantener la pantalla encendida");
            return;
        }

        screenWakeLock.acquire();
        button.setText("Liberar WakeLock de pantalla");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("SensorActiviy", "Se ha pausado la aplicación");
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("SensorActiviy", "Se ha reanudado la aplicación");
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //finishWakeLock();
    }
}
