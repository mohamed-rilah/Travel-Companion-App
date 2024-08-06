package com.example.travelcompanionapp.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItineraryViewModel extends AndroidViewModel {
    private ItineraryRepository mItineraryRepository;
    private final LiveData<List<Itinerary>> mAllItineraries;

    public ItineraryViewModel(Application application) {
        super(application);
        mItineraryRepository = new ItineraryRepository(application);
        mAllItineraries = mItineraryRepository.getAllItineraries();
    }

    public LiveData<List<Itinerary>> getAllItineraries() {
        return mAllItineraries;
    }

    public void insert(Itinerary itinerary) {
        mItineraryRepository.insert(itinerary);
    }

    public void update(int id, String newTitle, String newStart, String newPlan, String newNotes) {
        mItineraryRepository.update(id, newTitle, newStart, newPlan, newNotes);
    }

    public void delete(int id) {
        mItineraryRepository.delete(id);
    }

}
