package com.example.travelcompanionapp.weatherAPI;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelcompanionapp.MainActivity;
import com.example.travelcompanionapp.databinding.FragmentWeatherBinding;
import com.example.travelcompanionapp.model.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Template code taken from Week 4 Lab
public class WeatherViewModel extends ViewModel {
    private MutableLiveData<List<Weather>> allWeather;
    private String locationForWeather;

    public WeatherViewModel() {
        super();
        allWeather = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Weather>> getAllWeather() {
        return allWeather;
    }

    public void setWeatherLocation(String loc) {
        this.locationForWeather = loc;
    }

    public void requestWeather(WeatherRepository weatherRepository) {
        if (allWeather.getValue().size() == 0) {

            Call<WeatherList> weatherCall = weatherRepository.getListOfWeather("/" + locationForWeather + "?format=j2");
            weatherCall.enqueue(new Callback<WeatherList>() {
                @Override
                public void onResponse(Call<WeatherList> call, Response<WeatherList> response) {
                    if (response.isSuccessful()) {
                        Log.i(MainActivity.TAG, response.body().toString());
                        addAll(response.body());
                    }
                }

                @Override
                public void onFailure(Call<WeatherList> call, Throwable t) {
                    Log.i(MainActivity.TAG, "Error: " + t.toString());
                }
            });

        } else {
            Log.i(MainActivity.TAG, "Already got API list");
        }
    }

    public void addAll(WeatherList list) {
        allWeather.getValue().addAll(list.getWeather());
        allWeather.setValue(allWeather.getValue());
        Log.i(MainActivity.TAG, "Printing weather: " + allWeather.getValue().size());
        for (Weather weather: allWeather.getValue()) {
            Log.i(MainActivity.TAG, weather.toString());
        }
    }

    public void removeAll() {
        allWeather.getValue().removeAll(allWeather.getValue());
        allWeather.setValue(allWeather.getValue());
        Log.i(MainActivity.TAG, "Printing weather: " + allWeather.getValue().size());
        for (Weather weather: allWeather.getValue()) {
            Log.i(MainActivity.TAG, weather.toString());
        }
    }

}
