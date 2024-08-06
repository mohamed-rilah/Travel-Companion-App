package com.example.travelcompanionapp.placesAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetNearbyPlaces {

    @GET("json?")
    Call<PlacesList> getNearbyPlaces(@Query("location") String latLon,
                                     @Query("radius") int radius,
                                     @Query("type") String type,
                                     @Query("fields") String fields,
                                     @Query("key") String apikey);
}
