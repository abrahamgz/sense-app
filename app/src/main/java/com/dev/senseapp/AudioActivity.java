package com.dev.senseapp;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AudioActivity extends AppCompatActivity {

    private SeekBar sbMusic, sbAlarm;
    private TextView tvMusicVol, tvAlarmVol;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_audio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        initManager();
        initViews();
        configurateMusicSeekBar();
        configurateAlarmaSeekBar();

        /*
        int musicVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxMusicVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int minMusicVol = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC);

        int alarmVol = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        int maxAlarmVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        int minAlarmVol = audioManager.getStreamMinVolume(AudioManager.STREAM_ALARM);

        Log.d("AudioActivity", "El volumen de la musica es (" + musicVol + "/" + maxMusicVol + "), el mínimo es " + minMusicVol);
        Log.d("AudioActivity", "El volumen de la alarma es (" + alarmVol + "/" + maxAlarmVol + "), el mínimo es " + minAlarmVol);
        */
    }

    private void initManager() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

    }

    private void initViews() {
        tvMusicVol = findViewById(R.id.tvMusicVol);
        sbMusic = findViewById(R.id.sbMusic);
        tvAlarmVol = findViewById(R.id.tvAlarmVol);
        sbAlarm = findViewById(R.id.sbAlarm);
    }

    private void configurateMusicSeekBar() {
        int currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);


        sbMusic.setMax(maxVol);
        sbMusic.setProgress(currentVol);
        tvMusicVol.setText(currentVol + "/" + maxVol); // 7/15

        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int newVol, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVol, 0);
                tvMusicVol.setText(newVol + "/" + maxVol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    private void configurateAlarmaSeekBar() {
        int currentVol = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);

        sbAlarm.setMax(maxVol);
        sbAlarm.setProgress(currentVol);
        tvAlarmVol.setText(currentVol + "/" + maxVol);

        sbAlarm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                audioManager.setStreamVolume(AudioManager.STREAM_ALARM, progress, 0);
                tvAlarmVol.setText(progress + "/" + maxVol);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}