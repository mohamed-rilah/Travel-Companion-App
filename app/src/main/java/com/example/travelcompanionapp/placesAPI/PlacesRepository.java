package com.example.travelcompanionapp.placesAPI;

import retrofit2.Call;

public class PlacesRepository {

    private GetNearbyPlaces nearbyPlacesService;

    public PlacesRepository(GetNearbyPlaces placesService) {
        this.nearbyPlacesService = placesService;
    }

    public Call<PlacesList> getNearbyPlaces(String latLon, int radius, String type) {

        String fields = "name,icon,place_id,geometry";
        return nearbyPlacesService.getNearbyPlaces(latLon, radius, type, fields, SingletonData.getInstance().getPlacesKey());
    }
}
