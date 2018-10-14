package com.theobencode.victoroben.backbasesample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG_LOCATION_LIST_FRAGMENT = "LOCATION_LIST_FRAGMENT";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_LOCATION_LIST_FRAGMENT) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, LocationListFragment.newInstance(), FRAGMENT_TAG_LOCATION_LIST_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStack();
    }
}
