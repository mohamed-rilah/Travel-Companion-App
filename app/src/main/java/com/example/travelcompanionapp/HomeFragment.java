package com.example.travelcompanionapp;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.travelcompanionapp.databinding.FragmentHomeBinding;
import com.example.travelcompanionapp.model.LocationViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private LocationViewModel model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);

        // The following code obtains the user's current location and displays it on the page
        model.getCurrentLocation().observe(getViewLifecycleOwner(), loc -> {
            if (loc != null) {
                // Update the UI.
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                    String city = addresses.get(0).getSubAdminArea();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();

                    String addressFormatted = city + ", " + state + ", " + country;
                    binding.currentLocationText.append(" " + addressFormatted);
                } catch (IOException e) {
                    Log.e(MainActivity.TAG, "Error getting address \n" +
                            e.getMessage());
                }
            }
        });

        // The following code obtains the location that has been set by the user, and displays it on the page
        model.getUserSelectedLocation().observe(getViewLifecycleOwner(), loc -> {
            if (loc != null) {
                // Update the UI.
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                    String city = addresses.get(0).getSubAdminArea();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();

                    String addressFormatted = city + ", " + state + ", " + country;
                    binding.newLocationText.append(" " + addressFormatted);
                } catch (IOException e) {
                    Log.e(MainActivity.TAG, "Error getting address \n" +
                            e.getMessage());
                }
            }
        });

        // The following code allows the user to set their current location
        binding.buttonSetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.getCurrentLocation().observe(getViewLifecycleOwner(), loc -> {
                    if (loc != null) {
                        model.setUserSelectedLocation(loc);
                        Location selectedLocation = model.getUserSelectedLocation().getValue();

                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(selectedLocation.getLatitude(),
                                    selectedLocation.getLongitude(), 1);

                            String city = addresses.get(0).getSubAdminArea();
                            String state = addresses.get(0).getAdminArea();
                            String country = addresses.get(0).getCountryName();

                            String addressFormatted = city + ", " + state + ", " + country;

                            Toast.makeText(getContext(), "Destination location set as: " +
                                    addressFormatted, Toast.LENGTH_LONG).show();


                        } catch (IOException e) {
                            Log.e(MainActivity.TAG, "Error getting address\n" +
                                    e.getMessage());
                        }
                    }
                });
            }
        });

        // The following code allows the select new location button to function, redirecting the user
        binding.buttonSelectNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_homeFragment_to_setLocationFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}