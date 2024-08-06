package com.example.travelcompanionapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelcompanionapp.databinding.FragmentMapBinding;
import com.example.travelcompanionapp.model.LocationViewModel;
import com.example.travelcompanionapp.placesAPI.MyPlace;
import com.example.travelcompanionapp.placesAPI.PlacesViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private FragmentMapBinding binding;
    private LocationViewModel model;
    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private PlacesViewModel placesViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        placesViewModel = new ViewModelProvider(requireActivity()).get(PlacesViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // The following code is used to obtain the users location, to be displayed on the map
        model = new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        model.getUserSelectedLocation().observe(getViewLifecycleOwner(), loc -> {
            if (loc != null) {
                latitude = loc.getLatitude();
                longitude = loc.getLongitude();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng loc = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(loc)
                .title("User Location"));

        for (MyPlace place : placesViewModel.getAllPlaces().getValue()) {
            LatLng placesLoc = new LatLng(place.getGeometry().getLocation().getLat(), place.getGeometry().getLocation().getLng());
            mMap.addMarker(new MarkerOptions()
                    .position(placesLoc)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .title(place.getName()));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}