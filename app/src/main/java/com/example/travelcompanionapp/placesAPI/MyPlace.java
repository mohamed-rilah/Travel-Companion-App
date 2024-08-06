package com.example.travelcompanionapp.placesAPI;

import androidx.annotation.NonNull;

public class MyPlace {

    private String name;

    private String place_id;
    private Geometry geometry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Place called " + name + " at " + geometry.toString();
    }


    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

}
