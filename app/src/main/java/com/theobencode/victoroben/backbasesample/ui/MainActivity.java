package com.theobencode.victoroben.backbasesample.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.theobencode.victoroben.backbasesample.R;

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
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
