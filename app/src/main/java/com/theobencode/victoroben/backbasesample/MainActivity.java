package com.theobencode.victoroben.backbasesample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theobencode.victoroben.backbasesample.databinding.ActivityMainBinding;
import com.theobencode.victoroben.backbasesample.models.Location;

import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Filter.FilterListener {

    private LocationRecyclerAdapter locationRecyclerAdapter;
    @Nullable
    private Gson gson;

    private ActivityMainBinding binding;


    private static final Comparator<Location> ALPHABETICAL_CITY_NAME_COMPARATOR = new Comparator<Location>() {
        @Override
        public int compare(final Location location1, final Location location2) {
            return location1.getCityName().compareTo(location2.getCityName());
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        gson = new Gson();
        locationRecyclerAdapter = new LocationRecyclerAdapter(ALPHABETICAL_CITY_NAME_COMPARATOR);
        binding.locationRecyclerView.setAdapter(locationRecyclerAdapter);
        onCityJsonDataReceived(LocationUtils.getCitiesJsonString());
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
                locationRecyclerAdapter.getFilter().filter(query, MainActivity.this);
                binding.locationRecyclerView.scrollToPosition(0);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFilterComplete(final int count) {
        if (count == 0) {
            binding.noFilterResults.setVisibility(View.VISIBLE);
            binding.locationRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            binding.noFilterResults.setVisibility(View.INVISIBLE);
            binding.locationRecyclerView.setVisibility(View.VISIBLE);
        }

        binding.locationListCount.setText(getString(R.string.location_list_size, count));
    }

}
