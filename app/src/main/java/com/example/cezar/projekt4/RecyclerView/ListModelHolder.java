package com.example.cezar.projekt4.RecyclerView;

/**
 * Created by Marcin on 19.01.2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.cezar.projekt4.Activites.MapsActivity1;
import com.example.cezar.projekt4.Activites.PlaceActivity;
import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.R;

import java.util.ArrayList;

public class ListModelHolder extends RecyclerView.ViewHolder {

    public TextView nameTextView;
    public TextView descriptionTextView;
    public TextView numberTextView;
    private double latitude;
    private double longitude;
    private final Context activity;
    private ArrayList<Marker> mMarkers;

    public ListModelHolder(View itemView) {
        super(itemView);

        activity = itemView.getContext();

        nameTextView = (TextView) itemView.findViewById(R.id.list_title);
        descriptionTextView = (TextView) itemView.findViewById(R.id.list_description);
        numberTextView = (TextView) itemView.findViewById(R.id.list_item_number);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MapsActivity1.class);
                Marker.setList(new ArrayList<Marker>(mMarkers));
                Marker.writeMarkers();
                intent.putExtra("refresh", true);
                intent.putExtra("download", false);
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Lognitude", longitude);
                activity.startActivity(intent);
            }
        });
    }

    public void setLatLong(double latitude, double longitude, ArrayList<Marker> markers){
        this.latitude = latitude;
        this.longitude = longitude;
        mMarkers = new ArrayList<Marker>(markers);
    }

}
