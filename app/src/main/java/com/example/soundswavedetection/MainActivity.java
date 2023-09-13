package com.example.soundswavedetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int APP_PERMISSIONS_RECORD_AUDIO = 1;
    public static final String LOG_TAG = "main";
    private ScreenVisualization screenVisualization;
    private MediaRecorder recorder;
    private Handler handler = new Handler();
    TextView txtName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        screenVisualization = findViewById(R.id.svisualization);
        txtName = findViewById(R.id.labelName);

        screenVisualization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceListening();
            }
        });

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

    }
}