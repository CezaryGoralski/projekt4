package com.example.cezar.projekt4.RecyclerView;

/**
 * Created by Marcin on 27.02.2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cezar.projekt4.Activites.PlaceActivity;
import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.R;

public class PlaceModelHolder extends RecyclerView.ViewHolder {

    public TextView nameTextView;
    public TextView addressTextView;
    public TextView tagTextView;

    public ImageView iconView;

    private final View itemView;

    public PlaceModelHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;

        nameTextView = (TextView) itemView.findViewById(R.id.place_title);
        addressTextView = (TextView) itemView.findViewById(R.id.place_address);
        tagTextView = (TextView) itemView.findViewById(R.id.place_type);

        iconView = (ImageView) itemView.findViewById(R.id.place_image);

    }

    public void setMarker(Marker marker, final Context activity) {
        final Intent newActivity = new Intent(activity, PlaceActivity.class);
        newActivity.putExtra("Marker", marker);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(newActivity);
            }
        });
    }

}
