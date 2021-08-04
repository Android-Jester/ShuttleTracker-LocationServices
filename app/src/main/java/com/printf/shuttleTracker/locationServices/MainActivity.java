package com.printf.shuttleTracker.locationServices;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.printf.shuttleTracker.locationServices.R;

public class MainActivity extends AppCompatActivity {
       private final static int PERMISSION_FINE_LOCATION = 1; //permission request number, identifies the permission
       private Button startOperation; // used to switch the service on and off
        private boolean isActive;


@Override
protected void onCreate(Bundle savedInstanceState) {

    isActive = true;
    super.onCreate(savedInstanceState);

    Log.d("Button Status", ": Button Clicked");
    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
    } else {
        startLocationService();
    }
}


public void startLocationService(){
    if (!isLocationServiceRunning()){
        Intent intent = new Intent(getApplicationContext(), LocationServiceReceiver.class);
        startService(intent);
//        ContextCompat.startForegroundService(this, intent);
        Toast.makeText(this, "Location Services has started", Toast.LENGTH_SHORT).show();
    }
}

    private boolean isLocationServiceRunning() {
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null) {
            for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if(LocationServiceReceiver.class.getName().equals(service.service.getClassName())) {
                    if(service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        // if permission is granted
        if(requestCode == PERMISSION_FINE_LOCATION)

        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // start location service
                startLocationService();
            } else {
                // didn't get permission, notify user
                Toast.makeText(this, "You must enable these permissions inorder to to use this app", Toast.LENGTH_SHORT);
            }
        }
    }


    @Override
    protected void onDestroy() {
        isActive = false;
        super.onDestroy();
    }
}