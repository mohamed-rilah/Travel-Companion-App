package com.example.travelcompanionapp.placesAPI;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelcompanionapp.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesViewModel extends ViewModel {
    private MutableLiveData<List<MyPlace>> allPlaces;

    public PlacesViewModel() {
        super();
        allPlaces = new MutableLiveData<List<MyPlace>>(new ArrayList<MyPlace>());
    }
    public LiveData<List<MyPlace>> getAllPlaces() {
        return allPlaces;
    }

    public void requestNearbyPlaces(PlacesRepository placesRepository, String loc, int radius, String type) {
        if (allPlaces.getValue().size() == 0) {
            Call<PlacesList> userCall = placesRepository.getNearbyPlaces(loc, radius, type);
            Log.i(MainActivity.TAG, "Call: " + userCall.request().toString());
            userCall.enqueue(new Callback<PlacesList>() {
                @Override
                public void onResponse(Call<PlacesList> call, Response<PlacesList> response) {
                    if (response.isSuccessful()) {
                        Log.i(MainActivity.TAG, "The response is " + response.body().toString());
                        addAll(response.body());
                    }
                }

                @Override
                public void onFailure(Call<PlacesList> call, Throwable t) {
                    // show error message to user
                    Log.i(MainActivity.TAG, "Error: " + t.toString());
                }
            });
        } else {
            Log.i(MainActivity.TAG, "Already got a list of places, not getting any more");
        }
    }

    private void addAll(PlacesList list) {
        allPlaces.getValue().addAll(list.getResults());
        allPlaces.setValue(allPlaces.getValue());
        Log.i(MainActivity.TAG, "Printing " + allPlaces.getValue().size() + " Places");
        for (MyPlace user : allPlaces.getValue()) {
            Log.i(MainActivity.TAG, user.toString());
        }
    }

    public void removeAll() {
        allPlaces.getValue().removeAll(allPlaces.getValue());
        allPlaces.setValue(allPlaces.getValue());
        Log.i(MainActivity.TAG, "Printing weather: " + allPlaces.getValue().size());
        for (MyPlace place: allPlaces.getValue()) {
            Log.i(MainActivity.TAG, place.toString());
        }
    }
}
