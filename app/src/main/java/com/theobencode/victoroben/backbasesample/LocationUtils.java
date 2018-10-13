package com.theobencode.victoroben.backbasesample;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LocationUtils {

    private LocationUtils() {}
    private static final String TAG = LocationUtils.class.getSimpleName();
    private static final String CITIES_JSON_FILE_NAME = "cities.json";

    static String getCitiesJsonString() {
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

}
