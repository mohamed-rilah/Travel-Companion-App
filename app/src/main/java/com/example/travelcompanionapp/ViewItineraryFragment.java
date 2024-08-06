package com.example.travelcompanionapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelcompanionapp.database.ItineraryViewModel;
import com.example.travelcompanionapp.databinding.FragmentViewItineraryBinding;

public class ViewItineraryFragment extends Fragment {
    private FragmentViewItineraryBinding binding;
    private ItineraryViewModel mItineraryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewItineraryBinding.inflate(inflater, container, false);
        mItineraryViewModel = new ViewModelProvider(this).get(ItineraryViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonBackFromViewItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ViewItineraryFragment.this)
                        .navigate(R.id.action_viewItineraryFragment_to_itineraryFragment);
            }
        });

        int itineraryID = getArguments().getInt("id");
        String itineraryTitle = getArguments().getString("title");
        String itineraryStart = getArguments().getString("startLoc");
        String itineraryPlan = getArguments().getString("plan");
        String itineraryNotes = getArguments().getString("notes");

        binding.itineraryTitle.setText(itineraryTitle);
        binding.itineraryStartingLocation.setText(itineraryStart);
        binding.itineraryPlan.setText(itineraryPlan);
        binding.itineraryNotes.setText(itineraryNotes);

        binding.buttonDeleteItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItineraryViewModel.delete(itineraryID);

                Toast.makeText(getContext(), "Itinerary titled: " + itineraryTitle +
                        " has been deleted", Toast.LENGTH_LONG).show();

                NavHostFragment.findNavController(ViewItineraryFragment.this)
                        .navigate(R.id.action_viewItineraryFragment_to_itineraryFragment);
            }
        });

        binding.buttonUpdateItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTitle = binding.itineraryTitle.getText().toString();
                String updatedStart = binding.itineraryStartingLocation.getText().toString();
                String updatedPlan = binding.itineraryPlan.getText().toString();
                String updatedNotes = binding.itineraryNotes.getText().toString();

                mItineraryViewModel.update(itineraryID, updatedTitle, updatedStart, updatedPlan, updatedNotes);

                Toast.makeText(getContext(), "Itinerary titled: " + updatedTitle +
                        " has been updated", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}