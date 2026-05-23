package com.dev.senseapp;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class WifiActiviy extends AppCompatActivity {
    private static final String TAG = "WIFI_ACTIVITY";

    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wifi_activiy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initManager();

        Boolean isWifiEnabled = wifiManager.isWifiEnabled();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        //List<ScanResult> scanResults = wifiManager.getScanResults();
        Log.i(TAG, "isWifiEnabled" + isWifiEnabled);
        Log.i(TAG, "wifiInfo" + wifiInfo);

    }

    private void initManager() {
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    }
}