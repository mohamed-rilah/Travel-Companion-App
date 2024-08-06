package com.example.travelcompanionapp.weatherAPI;

import com.example.travelcompanionapp.model.LocationViewModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

// Template code taken from Week 4 Lab
public interface GetWeather {
    @GET
    Call<WeatherList> getWeather(@Url String url);
}
