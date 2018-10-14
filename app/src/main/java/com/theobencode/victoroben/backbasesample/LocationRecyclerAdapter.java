package com.theobencode.victoroben.backbasesample;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import com.theobencode.victoroben.backbasesample.databinding.ListItemLocationBinding;
import com.theobencode.victoroben.backbasesample.models.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.LocationViewHolder> implements Filterable {

    private final SortedList<Location> sortedLocations = new SortedList<>(Location.class, new SortedList.Callback<Location>() {

        @Override
        public int compare(final Location a, final Location b) {
            return comparator.compare(a, b);
        }

        @Override
        public void onInserted(final int position, final int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(final int position, final int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(final int fromPosition, final int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(final int position, final int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(final Location oldItem, final Location newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(final Location item1, final Location item2) {
            return item1.getId() == item2.getId();
        }
    });

    private final LocationClickListener locationClickListener;
    private final Comparator<Location> comparator;
    @NonNull
    private List<Location> originalLocationsList = Collections.emptyList();
    private LocationFilter locationFilter;

    LocationRecyclerAdapter(final LocationClickListener locationClickListener, final Comparator<Location> comparator) {
        this.comparator = comparator;
        this.locationClickListener = locationClickListener;
    }

    public void setData(final List<Location> newData) {
        sortedLocations.addAll(newData);
        originalLocationsList = Collections.unmodifiableList(new ArrayList<>(newData));
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int position) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ListItemLocationBinding listItemLocationBinding = DataBindingUtil.inflate(inflater, R.layout.list_item_location, parent, false);
        final LocationViewHolder locationViewHolder = new LocationViewHolder(listItemLocationBinding);
        listItemLocationBinding.setOnLocationClick(v -> {
            final int pos = locationViewHolder.getAdapterPosition();
            if (pos != NO_POSITION) {
                final Location location = sortedLocations.get(pos);
                locationClickListener.onLocationClick(location);
            }
        });
        return locationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationViewHolder holder, final int position) {
        holder.binding.setLocation(sortedLocations.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return sortedLocations.size();
    }

    @Override
    public Filter getFilter() {
        if (locationFilter == null) {
            locationFilter = new LocationFilter();
        }
        return locationFilter;
    }

    private class LocationFilter extends Filter {

        @Override
        protected FilterResults performFiltering(final CharSequence query) {
            final FilterResults finalResults = new FilterResults();

            final List<Location> filteredLocations;

            if (TextUtils.isEmpty(query)) {
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

            finalResults.values = filteredLocations;
            finalResults.count = filteredLocations.size();

            return finalResults;
        }

        @Override
        protected void publishResults(final CharSequence constraint, final FilterResults results) {
            replaceAll((List<Location>) results.values);
        }

        private void replaceAll(final List<Location> filteredLocations) {
            sortedLocations.beginBatchedUpdates();
            sortedLocations.clear();
            sortedLocations.addAll(filteredLocations);
            sortedLocations.endBatchedUpdates();
        }

    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {

        private final ListItemLocationBinding binding;

        LocationViewHolder(final ListItemLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    interface LocationClickListener {
        void onLocationClick(Location location);
    }
}
