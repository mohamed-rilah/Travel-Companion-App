package com.example.travelcompanionapp.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItineraryRepository {
    private ItineraryDao mItineraryDao;
    private LiveData<List<Itinerary>> mAllItineraries;

    ItineraryRepository(Application application) {
        ItineraryDatabase db  = ItineraryDatabase.getDatabase(application);
        mItineraryDao = db.getItineraryDao();
        mAllItineraries = mItineraryDao.getItineraries();
    }

    LiveData<List<Itinerary>> getAllItineraries() {
        return mAllItineraries;
    }

    void insert(Itinerary itinerary) {
        ItineraryDatabase.databaseWriteExecutor.execute( () -> {
            mItineraryDao.insertItinerary(itinerary);
        });
    }

    void update(int id, String newTitle, String newStart, String newPlan, String newNotes) {
        ItineraryDatabase.databaseWriteExecutor.execute(() -> {
            mItineraryDao.updateItinerary(id, newTitle, newStart, newPlan, newNotes);
        });
    }

    void delete(int id) {
        ItineraryDatabase.databaseWriteExecutor.execute( () -> {
            mItineraryDao.deleteItinerary(id);
        });
    }

}
