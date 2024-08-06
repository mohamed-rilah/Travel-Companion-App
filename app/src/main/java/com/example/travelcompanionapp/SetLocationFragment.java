package com.example.travelcompanionapp;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelcompanionapp.databinding.FragmentHomeBinding;
import com.example.travelcompanionapp.databinding.FragmentSetLocationBinding;
import com.example.travelcompanionapp.model.LocationViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SetLocationFragment extends Fragment {
    private FragmentSetLocationBinding binding;
    private LocationViewModel model;
    private TextView enteredCityName;
    private TextView enteredCountryName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSetLocationBinding.inflate(inflater, container, false);

        enteredCityName = binding.locationCity;
        enteredCountryName = binding.locationCountry;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);

        binding.buttonSetEnteredLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> addresses;

                try {
                    String collatedAddress = enteredCityName.getText().toString() + ", " + enteredCountryName.getText().toString();
                    addresses = geocoder.getFromLocationName(collatedAddress, 1);

                    String city = addresses.get(0).getSubAdminArea();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();

                    String addressFormatted = city + ", " + state + ", " + country;

                    if (addresses != null) {
                        double latitude = addresses.get(0).getLatitude();
                        double longitude = addresses.get(0).getLongitude();

                        Location userSelectedLocation = new Location(" ");
                        userSelectedLocation.setLatitude(latitude);
                        userSelectedLocation.setLongitude(longitude);

                        model.setUserSelectedLocation(userSelectedLocation);

                        Toast.makeText(getContext(), "New destination location set as: " +
                                addressFormatted, Toast.LENGTH_LONG).show();

                        Log.i(MainActivity.TAG, "New location set as: (" +
                                userSelectedLocation.getLatitude() + ", " +
                                userSelectedLocation.getLongitude() + ")");

                    }
                } catch (IOException e) {
                    Log.e(MainActivity.TAG, "Error getting address\n" +
                            e.getMessage());
                }

                NavHostFragment.findNavController(SetLocationFragment.this)
                        .navigate(R.id.action_setLocationFragment_to_homeFragment);
            }
        });

        binding.buttonBackFromSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SetLocationFragment.this)
                        .navigate(R.id.action_setLocationFragment_to_homeFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


