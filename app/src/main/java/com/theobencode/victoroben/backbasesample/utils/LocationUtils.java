package com.theobencode.victoroben.backbasesample.utils;

import android.util.Log;
import com.theobencode.victoroben.backbasesample.models.Location;
import com.theobencode.victoroben.backbasesample.ui.BackbaseApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LocationUtils {

    private LocationUtils() {}
    private static final String TAG = LocationUtils.class.getSimpleName();
    private static final String CITIES_JSON_FILE_NAME = "cities.json";

    public static String getCitiesJsonString() {
        final StringBuilder builder = new StringBuilder();
        String tempStr;

        try {
            final InputStream inputStream = BackbaseApp.getContext().getAssets().open(CITIES_JSON_FILE_NAME);
            final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((tempStr = in.readLine()) != null) {
                builder.append(tempStr);
            }
            in.close();

        } catch (final IOException e) {
            Log.e(TAG, "Error reading from cities json file ", e);
        }
        return builder.toString();
    }

    public static List<Location> filter(final List<Location> originalLocationsList, final CharSequence query) {

        final List<Location> filteredLocations;

        if (query == null || query.length() == 0) {
            //if search text is empty, then return the original array
            filteredLocations = originalLocationsList;
        } else {
            filteredLocations = new ArrayList<>();
            final String lowerCaseQuery = query.toString().toLowerCase();
            for (int i = 0; i < originalLocationsList.size(); i++) {
                final Location location = originalLocationsList.get(i);

                final String cityName = location.getCityName().toLowerCase();
                if (cityName.startsWith(lowerCaseQuery)) {
                    filteredLocations.add(location);
                }
            }
        }
        return filteredLocations;
    }
}
