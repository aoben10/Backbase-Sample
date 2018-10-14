package com.theobencode.victoroben.backbasesample;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.theobencode.victoroben.backbasesample.databinding.FragmentMapBinding;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;

    double longitude = 0;
    double latitude = 0;

    private static final String EXTRA_LATITUDE = "LATITUDE";
    private static final String EXTRA_LONGITUDE = "LONGITUDE";

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        final Bundle args = getArguments();
        if (args != null) {
            latitude = args.getDouble(EXTRA_LATITUDE);
            longitude = args.getDouble(EXTRA_LONGITUDE);
        }

        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(this);
    }

    public static MapFragment newInstance(final Double latitude, final Double longitude) {
        final Bundle args = new Bundle();
        args.putDouble(EXTRA_LATITUDE, latitude);
        args.putDouble(EXTRA_LONGITUDE, longitude);
        final MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(args);
        return mapFragment;
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final LatLng city = new LatLng(latitude, longitude);

        final CameraPosition.Builder camBuilder = CameraPosition.builder();
        camBuilder.target(city);
        camBuilder.zoom(10);

        final CameraPosition cameraPosition = camBuilder.build();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(city);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }
}
