package com.theobencode.victoroben.backbasesample.models;

import com.google.gson.annotations.SerializedName;

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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Coord)) return false;

        final Coord coord = (Coord) o;

        if (Double.compare(coord.longitude, longitude) != 0) return false;
        return Double.compare(coord.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(longitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}