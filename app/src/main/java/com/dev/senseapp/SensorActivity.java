package com.dev.senseapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tvLight;
    private TextView tvAccel;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accelSensor;

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
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        initManagers();
    }

    private void initManagers() {
        if (sensorManager == null) {
            tvLight.setText("No se inicializó el sensor manager");
            return;
        }

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

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

        if(sensorEvent.sensor.getType() ==Sensor.TYPE_ACCELEROMETER){
            float x=sensorEvent.values[0];
            float y=sensorEvent.values[1];
            float z=sensorEvent.values[2];

            tvAccel.setText("Acelerometro: \nx:"+x+"\ny:"+y+"\nz:"+z);
        }

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
            sensorManager.registerListener(this, lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}
