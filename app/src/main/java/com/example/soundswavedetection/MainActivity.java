package com.example.soundswavedetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final int APP_PERMISSIONS_RECORD_AUDIO = 1;
    public static final String LOG_TAG = "main";
    private ScreenVisualization screenVisualization;
    private MediaRecorder recorder;
    private Handler handler = new Handler();
    TextView txtName;
    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 50);
            if (recorder != null && screenVisualization != null) {
                int maxAmplitude = 0;
                try {
                    maxAmplitude = recorder.getMaxAmplitude();
                    txtName.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (maxAmplitude > 0)
                {
                    screenVisualization.addAmplitude(maxAmplitude);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        screenVisualization = findViewById(R.id.svisualization);
        txtName = findViewById(R.id.labelName);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            screenVisualization.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startVoiceListening();
                }
            });
        }

    }

    private void RequestPermissions(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)){
                Toast.makeText(this, getString(R.string.tAudioPermissions), Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO},APP_PERMISSIONS_RECORD_AUDIO);

        }
        else if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){

        }
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO,android.Manifest.permission.WRITE_EXTERNAL_STORAGE},APP_PERMISSIONS_RECORD_AUDIO);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case APP_PERMISSIONS_RECORD_AUDIO:{
                if (grantResults.length > 0){
                    boolean permissionsToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionsToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionsToRecord && permissionsToStore){
                        Toast.makeText(this, "Audio Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Audio Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private boolean chaeckPermissions(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.RECORD_AUDIO);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return  result2 == PackageManager.PERMISSION_GRANTED && result == PackageManager.PERMISSION_GRANTED;
    }

    private void startVoiceListening() {
        if(chaeckPermissions()){
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            File file = null;

            try {
                file = File.createTempFile("prefix", ".extension", getApplicationContext().getCacheDir());
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                    recorder.setOutputFile(file);
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            try {
                Toast.makeText(this, "Ready to list to voice", Toast.LENGTH_SHORT).show();

                recorder.prepare();

                Toast.makeText(this, "Trying listening to voice", Toast.LENGTH_SHORT).show();

                recorder.start();

                Toast.makeText(this, "Started listening to voice", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        else {
            RequestPermissions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updater);

        if (recorder != null) {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        handler.post(updater);

    }
}