package com.example.cezar.projekt4.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.R;

public class MarkAsVisitedMarker extends AppCompatActivity {
    private double latitude;
    private double lognitude;
    String markerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_as_visited_marker);
        lognitude = getIntent().getDoubleExtra("Lognitude", 0);
        latitude = getIntent().getDoubleExtra("Latitude", 0);
        markerName = getIntent().getStringExtra("Name");
        TextView textViewMarkerName = (TextView) findViewById(R.id.markerNameMarkAsVisited);
        textViewMarkerName.setText(markerName);
    }

    public void markAsVistied(View view) {
        Marker.readMarkers();
        Marker.markAsVisited(markerName);
        Marker.writeMarkers();
        Toast.makeText(this, "Marker marked as visited", Toast.LENGTH_SHORT).show();
        Intent openSecondActivity = new Intent(this, MapsActivity1.class);
        openSecondActivity.putExtra("Latitude", latitude);
        openSecondActivity.putExtra("Lognitude", lognitude);
        startActivity(openSecondActivity);
    }
}
