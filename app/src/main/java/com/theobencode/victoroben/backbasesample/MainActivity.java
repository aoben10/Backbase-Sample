package com.theobencode.victoroben.backbasesample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theobencode.victoroben.backbasesample.databinding.ActivityMainBinding;
import com.theobencode.victoroben.backbasesample.models.Location;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CITIES_JSON_FILE_NAME = "cities.json";

    @Nullable
    private LocationRecyclerAdapter locationRecyclerAdapter;
    @Nullable
    private Gson gson;


    private static final Comparator<Location> ALPHABETICAL_CITY_NAME_COMPARATOR = new Comparator<Location>() {
        @Override
        public int compare(final Location location1, final Location location2) {
            return location1.getCityName().compareTo(location2.getCityName());
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        gson = new Gson();
        locationRecyclerAdapter = new LocationRecyclerAdapter(ALPHABETICAL_CITY_NAME_COMPARATOR);
        binding.locationRecyclerView.setAdapter(locationRecyclerAdapter);
        final StringBuilder builder = new StringBuilder();
        String tempStr;

        // TODO - move this to background thread
        try {
            final InputStream json = getAssets().open(CITIES_JSON_FILE_NAME);
            final BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            while ((tempStr = in.readLine()) != null) {
                builder.append(tempStr);
            }
            in.close();
            onCityJsonDataReceived(builder.toString());

        } catch (final IOException e) {
            Log.e(TAG, "Error reading from cities json file ", e);
        }

    }

    private void onCityJsonDataReceived(final String citiesJsonString) {
        if (gson != null) {
            final List<Location> locationList = gson.fromJson(citiesJsonString, new TypeToken<List<Location>>() {
            }.getType());

            if (locationRecyclerAdapter != null) {
                locationRecyclerAdapter.setData(locationList);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
//                final List<ExampleModel> filteredModelList = filter(locations, query);
//                mAdapter.replaceAll(filteredModelList);
//                mBinding.recyclerView.scrollToPosition(0);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
