package com.example.travelcompanionapp.placesAPI;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelcompanionapp.MainActivity;
import com.example.travelcompanionapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceViewHolder> {

    private List<MyPlace> mPlaceList;
    private final LayoutInflater mInflater;

    public PlaceListAdapter(Context context, List<MyPlace> placeList) {
        mInflater = LayoutInflater.from(context);
        this.mPlaceList = placeList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.places_item, parent, false);
        return new PlaceViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        MyPlace myPlace = mPlaceList.get(position);
        holder.place = myPlace;
        String name = myPlace.getName();
        holder.placeNameView.setText(name);
    }

    @Override
    public int getItemCount() {
        return this.mPlaceList.size();
    }

    public void updateData(List<MyPlace> list) {
        this.mPlaceList = list;
        notifyDataSetChanged();
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {
        public final TextView placeNameView;
        final PlaceListAdapter mAdapter;
        public MyPlace place;

        public PlaceViewHolder(@NonNull View itemView, PlaceListAdapter adapter) {
            super(itemView);
            placeNameView = itemView.findViewById(R.id.placeNameFromAPI);
            this.mAdapter = adapter;
        }
    }

}
