package com.example.travelcompanionapp.model;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// Template code taken from Week 3 Lab
public class LocationViewModel extends ViewModel {
    private MutableLiveData<Location> currentLocation;

    // The following variable has been added to allow the user to choose their destination location
    private MutableLiveData<Location> userSelectedLocation;

    private LocationViewModel() {
        super();
        currentLocation = new MutableLiveData<>(null);
        userSelectedLocation = new MutableLiveData<>(null);
    }

    public LiveData<Location> getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location mCurrentLocation) {
        this.currentLocation.setValue(mCurrentLocation);
    }

    // The following getter-setters have been added to access the private field created above
    public LiveData<Location> getUserSelectedLocation() {
        return userSelectedLocation;
    }

    public void setUserSelectedLocation(Location mUserSelectedLocation) {
        this.userSelectedLocation.setValue(mUserSelectedLocation);
    }

}
