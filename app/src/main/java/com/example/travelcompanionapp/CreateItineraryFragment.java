package com.example.travelcompanionapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelcompanionapp.database.Itinerary;
import com.example.travelcompanionapp.database.ItineraryViewModel;
import com.example.travelcompanionapp.databinding.FragmentCreateItineraryBinding;

public class CreateItineraryFragment extends Fragment {

    private FragmentCreateItineraryBinding binding;
    private ItineraryViewModel mItineraryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateItineraryBinding.inflate(inflater, container, false);
        mItineraryViewModel = new ViewModelProvider(this).get(ItineraryViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBackFromCreateItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(MainActivity.TAG, "Create itinerary button clicked");
                NavHostFragment.findNavController(CreateItineraryFragment.this)
                        .navigate(R.id.action_createItineraryFragment_to_itineraryFragment);
            }
        });

        binding.buttonSaveCreatedItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = binding.itineraryTitle.getText().toString();
                String startingLocation = binding.itineraryStartingLocation.getText().toString();
                String plan = binding.itineraryPlan.getText().toString();
                String notes = binding.itineraryNotes.getText().toString();

                Itinerary itinerary = new Itinerary(title, startingLocation, plan, notes);

                mItineraryViewModel.insert(itinerary);

                Toast.makeText(getContext(), "New Itinerary Titled: " + title +
                                " has been saved", Toast.LENGTH_LONG).show();

                binding.itineraryTitle.getText().clear();
                binding.itineraryStartingLocation.getText().clear();
                binding.itineraryPlan.getText().clear();
                binding.itineraryNotes.getText().clear();

                NavHostFragment.findNavController(CreateItineraryFragment.this)
                        .navigate(R.id.action_createItineraryFragment_to_itineraryFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}