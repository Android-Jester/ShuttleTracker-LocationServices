package com.printf.shuttleTracker.locationServices;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseSender {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference latitudeContainer = database.getReference("commericalLocationA").child("latitude");
    public DatabaseReference longitudeContainer = database.getReference("commericalLocationA").child("longitude");
    public DatabaseReference activeContainer = database.getReference("commericalLocationA").child("isActve");

    FirebaseSender(double latitude, double longitude, boolean isActive){
        latitudeContainer.setValue(String.valueOf(latitude));
        longitudeContainer.setValue(String.valueOf(longitude));
    }




}
