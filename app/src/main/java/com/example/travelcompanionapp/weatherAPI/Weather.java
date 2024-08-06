package com.example.travelcompanionapp.weatherAPI;

import java.io.Serializable;
import java.lang.reflect.Array;

public class Weather implements Serializable {
    private int FeelsLikeC;
    private int temp_C;
    private int humidity;
    private int uvIndex;
    private int visibilityMiles;
    private String localObsDateTime;
    private float precipMM;
    private int windspeedKmph;

    public int getFeelsLikeC() {
        return FeelsLikeC;
    }

    public void setFeelsLikeC(int feelsLikeC) {
        FeelsLikeC = feelsLikeC;
    }

    public int getTemp_C() {
        return temp_C;
    }

    public void setTemp_C(int temp_C) {
        this.temp_C = temp_C;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public int getVisibilityMiles() {
        return visibilityMiles;
    }

    public void setVisibilityMiles(int visibilityMiles) {
        this.visibilityMiles = visibilityMiles;
    }

    public String getLocalObsDateTime() {
        return localObsDateTime;
    }

    public void setLocalObsDateTime(String localObsDateTime) {
        this.localObsDateTime = localObsDateTime;
    }

    public float getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(float precipMM) {
        this.precipMM = precipMM;
    }

    public int getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(int windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTemp_C());
        sb.append("\n");
        sb.append(getFeelsLikeC());
        sb.append("\n");
        sb.append(getHumidity());
        sb.append("\n");
        sb.append(getUvIndex());
        sb.append("\n");
        sb.append(getVisibilityMiles());
        sb.append("\n");
        sb.append("\n");
        sb.append(getWindspeedKmph());
        sb.append("\n");
        sb.append(getLocalObsDateTime());
        sb.append("\n");
        sb.append(getPrecipMM());
        return sb.toString();
    }




}
