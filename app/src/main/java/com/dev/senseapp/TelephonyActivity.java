package com.dev.senseapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelephonyActivity extends AppCompatActivity {

    TelephonyManager telephonyManager;

    private TextView tvOperator;
    private TextView tvCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_telephony);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestPermissions();
        initManager();

        String operator = telephonyManager.getNetworkOperatorName();
        String country = telephonyManager.getNetworkCountryIso();


        tvOperator = findViewById(R.id.tvOperator);
        tvCountry = findViewById(R.id.tvCountry);

        tvOperator.setText(operator);
        tvCountry.setText(country);


    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1001);
        }
    }

    private void initManager() {
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    }
}