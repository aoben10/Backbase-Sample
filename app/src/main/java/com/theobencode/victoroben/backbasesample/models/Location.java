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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return id == location.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}