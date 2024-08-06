package com.example.travelcompanionapp.weatherAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelcompanionapp.R;

import org.w3c.dom.Text;

import java.util.List;

// Template code taken from Week 4 Lab
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder> {
    private List<Weather> mWeatherList;
    private final LayoutInflater mInflater;

    public WeatherListAdapter(Context context, List<Weather> weatherList) {
        mInflater = LayoutInflater.from(context);
        this.mWeatherList = weatherList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.weather_item, parent, false);
        return new WeatherViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Weather weather = mWeatherList.get(position);
        holder.weather = weather;

        String dateAndTime = weather.getLocalObsDateTime();
        String currentTemperature = weather.getTemp_C() + "°C";

        String feelsLikeIntro = "Feels Like";
        String feelsLikeTemperatures = weather.getFeelsLikeC() + "°C";

        String precipitationIntro = "Precipitation";
        String precipitation = weather.getPrecipMM() + " mm";

        String visibilityIntro = "Visibility";
        String visibility = weather.getVisibilityMiles() + " miles";

        String humidityIntro = "Humidity";
        String humidity = weather.getHumidity() + "%";

        String uvIntro = "UV Index";
        String uv = "" + weather.getUvIndex();

        String windspeedIntro = "Wind Speed";
        String windspeed = weather.getWindspeedKmph() + " km/h";

        holder.dateAndTimeView.setText(dateAndTime);
        holder.temperatureView.setText(currentTemperature);

        holder.feelsLikeTitle.setText(feelsLikeIntro);
        holder.feelsLike.setText(feelsLikeTemperatures);

        holder.precipitationTitle.setText(precipitationIntro);
        holder.precipitation.setText(precipitation);

        holder.visibilityTitle.setText(visibilityIntro);
        holder.visibility.setText(visibility);

        holder.humidityTitle.setText(humidityIntro);
        holder.humidity.setText(humidity);

        holder.uvTitle.setText(uvIntro);
        holder.uv.setText(uv);

        holder.windspeedTitle.setText(windspeedIntro);
        holder.windspeed.setText(windspeed);
    }

    @Override
    public int getItemCount() {
        return this.mWeatherList.size();
    }

    public void updateData(List<Weather> list) {
        this.mWeatherList = list;
        notifyDataSetChanged();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        public final TextView dateAndTimeView;
        public final TextView temperatureView;
        public final TextView feelsLikeTitle;
        public final TextView feelsLike;
        public final TextView precipitationTitle;
        public final TextView precipitation;
        public final TextView visibilityTitle;
        public final TextView visibility;
        public final TextView humidityTitle;
        public final TextView humidity;
        public final TextView uvTitle;
        public final TextView uv;
        public final TextView windspeedTitle;
        public final TextView windspeed;

        final WeatherListAdapter mAdapter;
        public Weather weather;

        public WeatherViewHolder(@NonNull View itemView, WeatherListAdapter adapter){
            super(itemView);
            dateAndTimeView = itemView.findViewById(R.id.dateAndTime);
            temperatureView = itemView.findViewById(R.id.temperature);

            feelsLikeTitle = itemView.findViewById(R.id.feelsLikeTitle);
            feelsLike = itemView.findViewById(R.id.feelsLike);

            precipitationTitle = itemView.findViewById(R.id.precipitationTitle);
            precipitation = itemView.findViewById(R.id.precipitation);

            visibilityTitle = itemView.findViewById(R.id.visibilityTitle);
            visibility = itemView.findViewById(R.id.visibility);

            humidityTitle = itemView.findViewById(R.id.humidityTitle);
            humidity = itemView.findViewById(R.id.humidity);

            uvTitle = itemView.findViewById(R.id.uvIndexTitle);
            uv = itemView.findViewById(R.id.uvIndex);

            windspeedTitle = itemView.findViewById(R.id.windspeedTitle);
            windspeed = itemView.findViewById(R.id.windspeed);

            this.mAdapter = adapter;
        }
    }
}
