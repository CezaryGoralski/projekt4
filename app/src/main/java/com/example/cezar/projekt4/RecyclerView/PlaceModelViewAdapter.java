package com.example.cezar.projekt4.RecyclerView;

/**
 * Created by Marcin on 27.02.2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cezar.projekt4.ImageTransform.CircularTransform;
import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class PlaceModelViewAdapter extends RecyclerView.Adapter<PlaceModelHolder> {

    private final ArrayList<Marker> itemsList;
    private Context activity;

    public PlaceModelViewAdapter(ArrayList<Marker> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public PlaceModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        activity = parent.getContext();
        final View view = inflater.inflate(R.layout.place_item, null);
        return new PlaceModelHolder(view);

    }

    @Override
    public void onBindViewHolder(PlaceModelHolder holder, final int position) {
        final Marker item = itemsList.get(position);

        holder.nameTextView.setText(item.getName());
        holder.addressTextView.setText(item.getAddress());
        holder.tagTextView.setText(item.getCategory());

        Ion.with(holder.iconView)
                .transform(new CircularTransform())
                .load(item.getImg_url());

        holder.setMarker(item, activity);
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }

    public Marker removeItem(int position) {
        final Marker model = itemsList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Marker model) {
        itemsList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Marker model = itemsList.remove(fromPosition);
        itemsList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<Marker> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Marker> newModels) {
        for (int i = itemsList.size() - 1; i >= 0; i--) {
            final Marker model = itemsList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Marker> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Marker model = newModels.get(i);
            if (!itemsList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Marker> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Marker model = newModels.get(toPosition);
            final int fromPosition = itemsList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

}