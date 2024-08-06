package com.example.travelcompanionapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Itinerary.class}, version = 1)
public abstract class ItineraryDatabase extends RoomDatabase {
    public abstract ItineraryDao getItineraryDao();

    private static volatile ItineraryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static ItineraryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItineraryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ItineraryDatabase.class, "itineraries")
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
