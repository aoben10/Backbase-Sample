package com.theobencode.victoroben.backbasesample;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.*;
import android.widget.Filter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theobencode.victoroben.backbasesample.databinding.FragmentLocationListBinding;
import com.theobencode.victoroben.backbasesample.models.Location;

import java.util.Comparator;
import java.util.List;

public class LocationListFragment extends Fragment implements Filter.FilterListener, LocationRecyclerAdapter.LocationClickListener {

    FragmentLocationListBinding binding;
    @Nullable
    private Gson gson;
    private LocationRecyclerAdapter locationRecyclerAdapter;
    public static final String EXTRA_STATE_FILTER_QUERY = "EXTRA_FILTER_QUERY";

    private CharSequence filterQuery;

    private static final Comparator<Location> ALPHABETICAL_CITY_NAME_COMPARATOR =
            (location1, location2) -> location1.getCityName().compareTo(location2.getCityName());

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
        locationRecyclerAdapter = new LocationRecyclerAdapter(this, ALPHABETICAL_CITY_NAME_COMPARATOR);
        binding.locationRecyclerView.setAdapter(locationRecyclerAdapter);
        binding.locationRecyclerView
                .addItemDecoration(new DividerItemDecoration(binding.locationRecyclerView.getContext(),
                        DividerItemDecoration.VERTICAL));

        onCityJsonDataReceived(LocationUtils.getCitiesJsonString());
        if (savedInstanceState != null) {
            filterQuery = savedInstanceState.getCharSequence(EXTRA_STATE_FILTER_QUERY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
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
    public void onLocationClick(final Location location) {
        final double latitude = location.getCoord().getLatitude();
        final double longitude = location.getCoord().getLongitude();

        getFragmentManager() // TODO - Move this to activity, maybe
                .beginTransaction()
                .replace(R.id.fragment_container, MapFragment.newInstance(latitude, longitude), "MapFragment")
                .addToBackStack("MapFragment")
                .commit();
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
                filterQuery = query;
                locationRecyclerAdapter.getFilter().filter(query, LocationListFragment.this);
                binding.locationRecyclerView.scrollToPosition(0);
                return true;
            }
        });

        if (!TextUtils.isEmpty(filterQuery)) {
            //re-apply filter after restoring state
            searchView.requestFocus();
            searchView.setQuery(filterQuery, false);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(filterQuery)) {
            outState.putCharSequence(EXTRA_STATE_FILTER_QUERY, filterQuery);
        }
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
