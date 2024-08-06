package com.example.travelcompanionapp.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelcompanionapp.R;

import java.util.ArrayList;
import java.util.List;

public class ItineraryListAdapter extends RecyclerView.Adapter<ItineraryListAdapter.ItineraryViewHolder> {
    private List<Itinerary> mItineraryList = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public ItineraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itinerary_item, parent, false);
        return new ItineraryViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItineraryViewHolder holder, int position) {
        Itinerary itinerary = mItineraryList.get(position);
        holder.itinerary = itinerary;
        String title = itinerary.getItineraryTitle();
        String start = "Starting at: " + itinerary.getStartingLocation();
        String plan = "Places to visit: " + itinerary.getPlan();
        String notes = "Additional notes: " + itinerary.getNotes();
        holder.itineraryTitleView.setText(title);
        holder.itineraryStartView.setText(start);
        holder.itineraryPlanView.setText(plan);
        holder.itineraryNotesView.setText(notes);
    }

    @Override
    public int getItemCount() {
        return mItineraryList.size();
    }

    public void updateData(List<Itinerary> list) {
        this.mItineraryList = list;
        notifyDataSetChanged();
    }

    public void searchItineraries(List<Itinerary> list) {
        this.mItineraryList = list;
        notifyDataSetChanged();
    }

    class ItineraryViewHolder extends RecyclerView.ViewHolder {
        public final TextView itineraryTitleView;
        public final TextView itineraryStartView;
        public final TextView itineraryPlanView;
        public final TextView itineraryNotesView;

        public Itinerary itinerary;

        public ItineraryViewHolder(View itemView) {
            super(itemView);
            itineraryTitleView = itemView.findViewById(R.id.itineraryTitleFromDatabase);
            itineraryStartView = itemView.findViewById(R.id.itineraryStartFromDatabase);
            itineraryPlanView = itemView.findViewById(R.id.itineraryPlanFromDatabase);
            itineraryNotesView = itemView.findViewById(R.id.itineraryNotesFromDatabase);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mItineraryList.get(pos));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Itinerary itinerary);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
