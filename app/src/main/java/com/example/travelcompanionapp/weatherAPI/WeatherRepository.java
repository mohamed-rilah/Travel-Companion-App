package com.example.travelcompanionapp.weatherAPI;

import retrofit2.Call;

// Template code taken from Week 4 Lab
public class WeatherRepository {
    private GetWeather getWeatherService;

    public WeatherRepository(GetWeather weatherService) {
        this.getWeatherService = weatherService;
    }

    public Call<WeatherList> getListOfWeather(String url) {
        return getWeatherService.getWeather(url);
    }
}
