package com.theobencode.victoroben.backbasesample.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Coord {

    @SerializedName("lon")
    private double longitude;

    @SerializedName("lat")
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coord)) return false;
        Coord coord = (Coord) o;
        return Double.compare(coord.longitude, longitude) == 0 &&
                Double.compare(coord.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }
}