package com.example.travelcompanionapp.placesAPI;

import androidx.annotation.NonNull;

public class Geometry {

    private MyLocation location;

    public MyLocation getLocation() {
        return location;
    }

    public void setLocation(MyLocation location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return location.toString();
    }

}
