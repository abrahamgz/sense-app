package com.dev.senseapp;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
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

import com.dev.senseapp.databinding.ActivityHardwareBinding;

public class HardwareActivity extends AppCompatActivity {

    private ActivityHardwareBinding binding;

    private CameraManager cameraManager;
    private String cameraId;

    private Vibrator vibrator;
    private VibratorManager vibratorManager;
    private ConnectivityManager connectivityManager;

    private boolean isLanternOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityHardwareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initManagers();
    }

    private void initViews() {
        binding.cardLantern.setOnClickListener(v -> toggleLantern());
        binding.cardNetwork.setOnClickListener(v-> checkInternetConnection());
    }

    private void initManagers() {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

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
            binding.tvStateLantern.setText("Encendida");
            binding.tvStateLantern.setTextColor(0xFF008F39);
        } else {
            binding.tvStateLantern.setText("Apagada");
            binding.tvStateLantern.setTextColor(0xFFD32F2F);
        }
    }

    private void vibrate() {
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    private void checkInternetConnection() {
        vibrate();

        if (connectivityManager == null) {
            return;
        }

        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);

        if (capabilities == null || !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            binding.tvStateNetwork.setText("Sin conexión");
            binding.tvStateNetwork.setTextColor(0xFFD32F2F);
            return;
        }

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            binding.tvStateNetwork.setText("Conectado (WiFi)");
            binding.tvStateNetwork.setTextColor(0xFF008F39);
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            binding.tvStateNetwork.setText("Conectado (Datos Móviles)");
            binding.tvStateNetwork.setTextColor(0xFF1976D2);
        } else {
            binding.tvStateNetwork.setText("Conectado (Otro)");
            binding.tvStateNetwork.setTextColor(0xFF008F39);
        }
    }
}
