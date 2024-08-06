package com.example.travelcompanionapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelcompanionapp.database.Itinerary;
import com.example.travelcompanionapp.database.ItineraryListAdapter;
import com.example.travelcompanionapp.database.ItineraryViewModel;
import com.example.travelcompanionapp.databinding.FragmentItinerariesBinding;

import java.util.ArrayList;
import java.util.List;

public class ItinerariesFragment extends Fragment {
    private FragmentItinerariesBinding binding;
    private ItineraryViewModel mItineraryViewModel;
    private RecyclerView mRecyclerView;
    private ItineraryListAdapter mAdapter;
    private EditText searchText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItinerariesBinding.inflate(inflater, container, false);

        mItineraryViewModel = new ViewModelProvider(requireActivity()).get(ItineraryViewModel.class);
        searchText = binding.searchTitle;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.ItinerariesRecyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ItineraryListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mItineraryViewModel.getAllItineraries().observe(getViewLifecycleOwner(), new Observer<List<Itinerary>>() {
            @Override
            public void onChanged(@Nullable List<Itinerary> itineraryList) {
                mAdapter.updateData(itineraryList);
            }
        });

        mAdapter.setOnItemClickListener(new ItineraryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Itinerary itinerary) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", itinerary.getItineraryID());
                bundle.putString("title", itinerary.getItineraryTitle());
                bundle.putString("startLoc", itinerary.getStartingLocation());
                bundle.putString("plan", itinerary.getPlan());
                bundle.putString("notes", itinerary.getNotes());

                NavHostFragment.findNavController(ItinerariesFragment.this)
                        .navigate(R.id.action_itineraryFragment_to_viewItineraryFragment, bundle);
            }
        });

        binding.buttonCreateItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(MainActivity.TAG, "Create itinerary button clicked");
                NavHostFragment.findNavController(ItinerariesFragment.this)
                        .navigate(R.id.action_itineraryFragment_to_createItineraryFragment);
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Itinerary> searchedList = new ArrayList<>();
                for (Itinerary itinerary : mItineraryViewModel.getAllItineraries().getValue()) {
                    if (itinerary.getItineraryTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                        searchedList.add(itinerary);
                    }
                }
                mAdapter.searchItineraries(searchedList);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}