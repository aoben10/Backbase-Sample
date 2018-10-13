package com.theobencode.victoroben.backbasesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    private static final Comparator<Location> ALPHABETICAL_CITY_NAME_COMPARATOR = new Comparator<Location>() {
//        @Override
//        public int compare(final Location location1, final Location location2) {
//            return location1.getCityName().compareTo(location2.getCityName());
//        }
//    };


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
            public boolean onQueryTextChange(final String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
