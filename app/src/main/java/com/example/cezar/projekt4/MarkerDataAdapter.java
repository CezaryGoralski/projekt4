package com.example.cezar.projekt4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cezar on 2015-12-07.
 */
public class MarkerDataAdapter extends ArrayAdapter<Marker> {

    public MarkerDataAdapter (Context context, ArrayList<Marker> markers) {
        super(context, 0, markers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Marker marker = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_marker, parent, false);
        }
        // Lookup view for data population
        TextView mName = (TextView) convertView.findViewById(R.id.mName);
        TextView mLognitude = (TextView) convertView.findViewById(R.id.mLognitude);
        TextView mLagnitude = (TextView) convertView.findViewById(R.id.mLagnitude);
        TextView mDescription = (TextView) convertView.findViewById(R.id.mDescription);
        TextView mVisited = (TextView) convertView.findViewById(R.id.mVisited);
        // Populate the data into the template view using the data object
        mName.setText(marker.getName());
        mLognitude.setText(String.valueOf(marker.getLognitude()));
        mLagnitude.setText(String.valueOf(marker.getLatitude()));
        mDescription.setText(marker.getDescription());
        mVisited.setText(String.valueOf(marker.isVisited()));
        // Return the completed view to render on screen
        return convertView;
    }
}
