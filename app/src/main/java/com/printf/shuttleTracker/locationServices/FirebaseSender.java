package com.printf.shuttleTracker.locationServices;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseSender {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference latitudeContainer = database.getReference("commericalLocationA").child("latitude");
    public DatabaseReference longitudeContainer = database.getReference("commericalLocationA").child("longitude");
    private double latitude;
    private double longitude;

    FirebaseSender(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        latitudeContainer.setValue(this.latitude);
        longitudeContainer.setValue(this.longitude);

    }




}
