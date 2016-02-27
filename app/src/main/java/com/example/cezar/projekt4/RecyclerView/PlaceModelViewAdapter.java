package com.example.cezar.projekt4.RecyclerView;

/**
 * Created by Marcin on 27.02.2016.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cezar.projekt4.ImageTransform.CircularTransform;
import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.Model.PlaceModel;
import com.example.cezar.projekt4.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class PlaceModelViewAdapter extends RecyclerView.Adapter<PlaceModelHolder> {

    private final ArrayList<Marker> itemsList;

    public PlaceModelViewAdapter(ArrayList<Marker> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public PlaceModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }

}