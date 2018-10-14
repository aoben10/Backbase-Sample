package com.theobencode.victoroben.backbasesample;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.*;
import android.widget.Filter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theobencode.victoroben.backbasesample.databinding.FragmentLocationListBinding;
import com.theobencode.victoroben.backbasesample.models.Location;

import java.util.Comparator;
import java.util.List;

public class LocationListFragment extends Fragment implements Filter.FilterListener{

    FragmentLocationListBinding binding;
    @Nullable
    private Gson gson;
    private LocationRecyclerAdapter locationRecyclerAdapter;

    private static final Comparator<Location> ALPHABETICAL_CITY_NAME_COMPARATOR = new Comparator<Location>() {
        @Override
        public int compare(final Location location1, final Location location2) {
            return location1.getCityName().compareTo(location2.getCityName());
        }
    };

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        binding.progressCircular.setVisibility(View.VISIBLE);
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
                binding.locationListCount.setText(getString(R.string.location_list_size, locationList.size()));

            }
        }
        binding.progressCircular.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                locationRecyclerAdapter.getFilter().filter(query, LocationListFragment.this);
                binding.locationRecyclerView.scrollToPosition(0);
                return true;
            }
        });

    }

    public static LocationListFragment newInstance() {
        return new LocationListFragment();
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