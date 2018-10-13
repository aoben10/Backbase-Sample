package com.theobencode.victoroben.backbasesample;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.theobencode.victoroben.backbasesample.databinding.ListItemLocationBinding;
import com.theobencode.victoroben.backbasesample.models.Location;

import java.util.Comparator;
import java.util.List;

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.LocationViewHolder> {

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

    private final Comparator<Location> comparator;

    LocationRecyclerAdapter(final Comparator<Location> comparator) {
        this.comparator = comparator;
    }

    public void setData(final List<Location> newData) {
        sortedLocations.addAll(newData);
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int position) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ListItemLocationBinding listItemLocationBinding = DataBindingUtil.inflate(inflater, R.layout.list_item_location, parent, false);
        return new LocationViewHolder(listItemLocationBinding);
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

    static class LocationViewHolder extends RecyclerView.ViewHolder {

        private final ListItemLocationBinding binding;

        LocationViewHolder(final ListItemLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
