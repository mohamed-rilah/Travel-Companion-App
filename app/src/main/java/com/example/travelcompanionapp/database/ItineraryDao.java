package com.example.travelcompanionapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItineraryDao {
    @Insert
    public void insertItinerary(Itinerary itinerary);

    @Query("SELECT * FROM itineraries")
    public LiveData<List<Itinerary>> getItineraries();

    @Query("UPDATE itineraries SET itineraryTitle = :newTitle, startingLocation = :newLocation, plan = :newPlan, notes = :newNotes WHERE itineraryID = :id")
    public void updateItinerary(int id, String newTitle, String newLocation, String newPlan, String newNotes);

    @Query("DELETE FROM itineraries WHERE itineraryID = :id")
    public void deleteItinerary(int id);

}
