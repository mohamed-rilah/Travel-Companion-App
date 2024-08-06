package com.example.travelcompanionapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "itineraries")
public class Itinerary {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int itineraryID;
    public String itineraryTitle;
    public String startingLocation;
    public String plan;
    public String notes;

    public Itinerary(String itineraryTitle, String startingLocation, String plan, String notes) {
        this.itineraryTitle = itineraryTitle;
        this.startingLocation = startingLocation;
        this.plan = plan;
        this.notes = notes;
    }

    public int getItineraryID() {
        return itineraryID;
    }

    public void setItineraryID(int itineraryID) {
        this.itineraryID = itineraryID;
    }

    @NonNull
    public String getItineraryTitle() {
        return itineraryTitle;
    }

    public void setItineraryTitle(@NonNull String itineraryTitle) {
        this.itineraryTitle = itineraryTitle;
    }

    public String getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(String startingLocation) {
        this.startingLocation = startingLocation;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Ignore
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getItineraryTitle());
        sb.append("\n");
        sb.append(getStartingLocation());
        sb.append("\n");
        sb.append(getPlan());
        sb.append("\n");
        sb.append(getNotes());

        return sb.toString();
    }
}
