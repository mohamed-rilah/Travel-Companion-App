package com.example.travelcompanionapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelcompanionapp.databinding.FragmentPlacesBinding;
import com.example.travelcompanionapp.model.LocationViewModel;
import com.example.travelcompanionapp.placesAPI.GetNearbyPlaces;
import com.example.travelcompanionapp.placesAPI.MyPlace;
import com.example.travelcompanionapp.placesAPI.PlaceListAdapter;
import com.example.travelcompanionapp.placesAPI.PlacesRepository;
import com.example.travelcompanionapp.placesAPI.PlacesViewModel;
import com.example.travelcompanionapp.weatherAPI.WeatherRepository;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacesFragment extends Fragment {
    private FragmentPlacesBinding binding;
    private LocationViewModel model;
    private PlacesViewModel placesViewModel;
    private RecyclerView mRecyclerView;
    private PlaceListAdapter placeListAdapter;
    private TextView enteredPlaceType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlacesBinding.inflate(inflater, container, false);

        placesViewModel = new ViewModelProvider(requireActivity()).get(PlacesViewModel.class);
        enteredPlaceType = binding.placesType;

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

                    String addressFormatted = city;
                    binding.currentLocationText.append(" " + addressFormatted);

                } catch (IOException e) {
                    Log.e(MainActivity.TAG, "Error getting address \n" + e.getMessage());
                }
            }
        });

        // This code observes the list, and updates when changes are recognized
        final Observer<List<MyPlace>> placesListObserver = new Observer<List<MyPlace>>() {
            @Override
            public void onChanged(@Nullable final List<MyPlace> myPlaces) {
                placeListAdapter.updateData(myPlaces);
            }
        };

        // Code to allow recycler view and adapter to function as expected
        mRecyclerView = view.findViewById(R.id.placesRecyclerView);
        placeListAdapter = new PlaceListAdapter(getContext(), placesViewModel.getAllPlaces().getValue());
        mRecyclerView.setAdapter(placeListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sets the observer with the Live Data List
        placesViewModel.getAllPlaces().observe(getViewLifecycleOwner(), placesListObserver);


        // Code allowing the data to be fetched
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetNearbyPlaces service = retrofit.create(GetNearbyPlaces.class);

        // The following code allows the data to be requested when the button is clicked
        binding.buttonFetchPlacesFromAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = enteredPlaceType.getText().toString();

                placesViewModel.requestNearbyPlaces(new PlacesRepository(service),
                        model.getUserSelectedLocation().getValue().getLatitude() + "," +
                                model.getUserSelectedLocation().getValue().getLongitude(),
                        15000, "" + type);
            }
        });

        // The following code removes contents from the list and re-fetches data from API
        binding.buttonClearPlacesFromAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placesViewModel.removeAll();

                String type = enteredPlaceType.getText().toString();

                placesViewModel.requestNearbyPlaces(new PlacesRepository(service),
                        model.getUserSelectedLocation().getValue().getLatitude() + "," +
                                model.getUserSelectedLocation().getValue().getLongitude(),
                        15000, "" + type);
            }
        });

        // Allows the update button to be visible, once the list contains content
        if (placesViewModel.getAllPlaces().getValue().size() > 0) {
            binding.buttonClearPlacesFromAPI.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}