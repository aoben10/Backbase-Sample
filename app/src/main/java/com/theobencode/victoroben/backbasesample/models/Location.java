package com.theobencode.victoroben.backbasesample.models;


import com.google.gson.annotations.SerializedName;

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

        if (id != location.id) return false;
        if (country != null ? !country.equals(location.country) : location.country != null) return false;
        if (coord != null ? !coord.equals(location.coord) : location.coord != null) return false;
        return cityName != null ? cityName.equals(location.cityName) : location.cityName == null;
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (coord != null ? coord.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}