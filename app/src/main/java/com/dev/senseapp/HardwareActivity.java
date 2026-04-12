package com.dev.senseapp;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HardwareActivity extends AppCompatActivity {

    private CardView cardLantern;
    private TextView tvStateLantern;

    private CameraManager cameraManager;
    private String cameraId;

    private Vibrator vibrator;
    private VibratorManager vibratorManager;

    private boolean isLanternOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hardware);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initManagers();
    }

    private void initViews() {
        cardLantern = findViewById(R.id.cardLantern);
        tvStateLantern = findViewById(R.id.tvStateLantern);

        cardLantern.setOnClickListener(v -> toggleLantern());
    }

    private void initManagers() {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Log.d("InitManagers", "Version android 12 a más");
            vibratorManager = (VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            vibrator = vibratorManager.getDefaultVibrator();
        } else {
            Log.d("InitManagers", "Version android 11 a menos");
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }

        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void toggleLantern() {
        isLanternOn = !isLanternOn;
        try {
            cameraManager.setTorchMode(cameraId, isLanternOn);
            vibrate();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        if (isLanternOn) {
            tvStateLantern.setText("Encendida");
            tvStateLantern.setTextColor(0xFF008F39);
        } else {
            tvStateLantern.setText("Apagada");
            tvStateLantern.setTextColor(0xFFD32F2F);
        }
    }

    private void vibrate() {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
