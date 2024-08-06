package com.example.travelcompanionapp.weatherAPI;

import java.util.ArrayList;

// Template code taken from Week 4 Lab
public class WeatherList {
    private ArrayList<Weather> current_condition;

    public ArrayList<Weather> getWeather() {
        return current_condition;
    }

    public void setCurrent_condition(ArrayList<Weather> current_condition) {
        this.current_condition = current_condition;
    }
}
