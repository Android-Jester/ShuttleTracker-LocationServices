package com.printf.shuttleTracker.locationServices;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseSender {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference latitudeContainer = database.getReference("commericalLocationA").child("latitude");
    public DatabaseReference longitudeContainer = database.getReference("commericalLocationA").child("longitude");
    public DatabaseReference activeContainer = database.getReference("commericalLocationA").child("isActive");

    FirebaseSender(double latitude, double longitude, boolean isActive){
        latitudeContainer.setValue(latitude);
        longitudeContainer.setValue(longitude);
        activeContainer.setValue(isActive, (error, ref) -> {
            if(error != null){
            Log.d("Firebase Error", String.valueOf(error));
        }});
    }




}
