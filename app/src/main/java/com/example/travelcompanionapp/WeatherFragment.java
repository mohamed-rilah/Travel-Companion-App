package com.example.travelcompanionapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelcompanionapp.databinding.FragmentWeatherBinding;
import com.example.travelcompanionapp.model.LocationViewModel;
import com.example.travelcompanionapp.weatherAPI.Weather;
import com.example.travelcompanionapp.weatherAPI.WeatherListAdapter;
import com.example.travelcompanionapp.weatherAPI.WeatherViewModel;
import com.example.travelcompanionapp.weatherAPI.GetWeather;
import com.example.travelcompanionapp.weatherAPI.WeatherRepository;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private LocationViewModel model;
    private WeatherViewModel weatherViewModel;
    private RecyclerView mRecyclerView;
    private WeatherListAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // The following code is used to obtain the location set by the user, and displays it on the page
        model = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        model.getUserSelectedLocation().observe(getViewLifecycleOwner(), loc -> {
            if (loc != null) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

                    String city = addresses.get(0).getSubAdminArea();
                    String state = addresses.get(0).getAdminArea();

                    String addressFormatted = city + ", " + state ;
                    binding.currentLocationText.append(addressFormatted);

                    // Passes the location from which which the weather should be shown
                    weatherViewModel.setWeatherLocation(state);

                } catch (IOException e) {
                    Log.e(MainActivity.TAG, "Error getting address \n" + e.getMessage());
                }
            }
        });

        // This code observes the list, and updates when changes are recognized
        final Observer<List<Weather>> weatherListObserver = new Observer<List<Weather>>() {
            @Override
            public void onChanged(@Nullable final List<Weather> weatherList) {
                // Update the UI, in this case, a Toast.
                mAdapter.updateData(weatherList);
            }
        };

        // Sets the observer with the Live Data List
        weatherViewModel.getAllWeather().observe(getViewLifecycleOwner(), weatherListObserver);

        // Code to allow recycler view and adapter to function as expected
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mAdapter = new WeatherListAdapter(getContext(), weatherViewModel.getAllWeather().getValue());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Code allowing the data to be fetched
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wttr.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetWeather service = retrofit.create(GetWeather.class);
        weatherViewModel.requestWeather(new WeatherRepository(service));

        // The following code removes contents from the list and re-fetches data from API
        binding.buttonClearWeatherFromAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherViewModel.removeAll();
                weatherViewModel.requestWeather(new WeatherRepository(service));
            }
        });

        // Allows the update button to be visible, once the list contains content
        if (weatherViewModel.getAllWeather().getValue().size() > 0) {
            binding.buttonClearWeatherFromAPI.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}