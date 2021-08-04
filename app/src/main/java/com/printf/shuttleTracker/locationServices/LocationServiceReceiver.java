package com.printf.shuttleTracker.locationServices;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class LocationServiceReceiver extends Service {
    private String TAG = LocationServiceReceiver.class.getSimpleName();
    public static final int DEFAULT_INTERVAL = 4000; // default location update interval
    public static final int FASTEST_INTERVAL = 2000; // fastest location update interval
    private LocationCallback locationCallBack ; //Used for receiving notifications from the FusedLocationProviderApi
    // when the device location has changed or can no longer be determined
    private Location location; // last known location or updated location
    private LocationRequest locationRequest; //


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service Detector", "Started Created");
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                locationResult.getLastLocation();
                location = locationResult.getLastLocation();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                new FirebaseSender(latitude, longitude, true);

                Log.d(TAG ,latitude + ", " + longitude + ", ");
                Toast.makeText(getApplicationContext(), latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
            }
        };


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startLocationService();
        Toast.makeText(getApplicationContext(), "Location Started", Toast.LENGTH_SHORT).show();
        Log.d("Service Detector", "Started Service");


        return START_STICKY;
    }

    @SuppressLint("MissingPermission")
    private void startLocationService() {
        locationRequest = new LocationRequest();
        configLocationRequest();
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper());
    }


    public void configLocationRequest(){
        locationRequest.setInterval(DEFAULT_INTERVAL); // set interval in which we want to get location in
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    private String getUserAddress() {
        // get address from location and show it
        Geocoder geocoder = new Geocoder(LocationServiceReceiver.this);
        try
        {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            return (addressList.get(0).getAddressLine(0));

        }
        catch (Exception e)
        {
            return("Unable to get address");
        }
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "*****DESTROY****");
        stopLocationService();
    }

    @Override
    public boolean stopService(Intent name) {
        Log.d(TAG,"STOP SERVICE");
        stopLocationService();

        return super.stopService(name);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG,"ON TASK REMOVED");

        super.onTaskRemoved(rootIntent);
    }

    private void stopLocationService() {
        Log.d("location", "*****STOPPED****");
        // Remove all location updates for the given location result listener
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallBack);
        new FirebaseSender(0.0, 0.0, false);
        // stop the service and remove the notification
//        stopForeground(true);
        stopSelf();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}