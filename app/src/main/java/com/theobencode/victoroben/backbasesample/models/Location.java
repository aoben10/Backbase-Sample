package com.theobencode.victoroben.backbasesample.models;


import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Location {

    @SerializedName("country")
    private String country;

    @SerializedName("coord")
    private Coord coord;

    @SerializedName("name")
    private String cityName;

    @SerializedName("_id")
    private int id;

    public String getCountry() {
        return country;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getCityName() {
        return cityName;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        final Location location = (Location) o;
        // TODO - Fix these issues
        return id == location.id &&
                Objects.equals(country, location.country) &&
                Objects.equals(coord, location.coord) &&
                Objects.equals(cityName, location.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, coord, cityName, id);
    }
}