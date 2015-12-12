package com.example.cezar.projekt4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MarkAsVisitedMarker extends AppCompatActivity {
    String markerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_as_visited_marker);
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
        startActivity(openSecondActivity);
    }
}
