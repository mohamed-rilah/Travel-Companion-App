package com.example.travelcompanionapp;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.example.travelcompanionapp.database.Itinerary;

public class TravelCompanionAppTest {

    @Test
    public void itinerary_create() {
        Itinerary itinerary = new Itinerary("Test Title",
                "Aston University", "Main Building", "Mobile Design");

        assertEquals("Test Title", itinerary.getItineraryTitle());
        assertEquals("Aston University", itinerary.getStartingLocation());
        assertEquals("Main Building", itinerary.getPlan());
        assertEquals("Mobile Design", itinerary.getNotes());
    }

    @Test
    public void itinerary_update() {
        Itinerary itinerary = new Itinerary("Test Title",
                "Aston University", "Main Building", "Mobile Design");

        itinerary.setItineraryTitle("Birmingham Trip");
        itinerary.setStartingLocation("Birmingham New Street");
        itinerary.setPlan("Selfridges Birmingham");
        itinerary.setNotes("Book train tickets");

        assertEquals("Birmingham Trip", itinerary.getItineraryTitle());
        assertEquals("Birmingham New Street", itinerary.getStartingLocation());
        assertEquals("Selfridges Birmingham", itinerary.getPlan());
        assertEquals("Book train tickets", itinerary.getNotes());
    }
    
}