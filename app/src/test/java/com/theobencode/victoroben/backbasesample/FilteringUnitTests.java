package com.theobencode.victoroben.backbasesample;

import com.theobencode.victoroben.backbasesample.models.Location;
import com.theobencode.victoroben.backbasesample.utils.LocationUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FilteringUnitTests {

    @Test
    public void filtering_with_empty_string_returns_original_list() {
        final List<Location> locations = getLocationsList();
        final List<Location> filteredLocations = LocationUtils.filter(locations,"");
        assertEquals(locations, filteredLocations);
    }

    @Test
    public void filtering_with_no_match_string_returns_empty_list() {
        final List<Location> locations = getLocationsList();
        final List<Location> filteredLocations = LocationUtils.filter(locations,"bac");
        assertEquals(0, filteredLocations.size());
    }

    @Test
    public void filtering_with_match_string_filters_out_cities() {
        final List<Location> locations = getLocationsList();
        final List<Location> filteredLocations = LocationUtils.filter(locations,"a");

        final Location location1 = new Location();
        location1.setCityName("Atlanta");
        location1.setCountry("US");

        final Location location3 = new Location();
        location3.setCityName("Asberg");
        location3.setCountry("AL");

        final ArrayList<Location> expectedResult = new ArrayList<>();
        expectedResult.add(location1);
        expectedResult.add(location3);

        assertEquals(expectedResult, filteredLocations);
    }

    @Test
    public void filtering_with_match_string_filters_cities_by_prefix() {
        final List<Location> locations = getLocationsList();
        final List<Location> filteredLocations = LocationUtils.filter(locations,"nio");

        final Location location1 = new Location();
        location1.setCityName("NioSan");
        location1.setCountry("US");

        final ArrayList<Location> expectedResult = new ArrayList<>();
        expectedResult.add(location1);

        assertEquals(expectedResult, filteredLocations);
    }


    private List<Location> getLocationsList() {
        final Location location1 = new Location();
        location1.setCityName("Atlanta");
        location1.setCountry("US");

        final Location location2 = new Location();
        location2.setCityName("SanAntonio");
        location2.setCountry("US");

        final Location location4 = new Location();
        location4.setCityName("NioSan");
        location4.setCountry("US");

        final Location location3 = new Location();
        location3.setCityName("Asberg");
        location3.setCountry("AL");

        final ArrayList<Location> result = new ArrayList<>();
        result.add(location1);
        result.add(location2);
        result.add(location3);
        result.add(location4);

        return result;
    }

}
