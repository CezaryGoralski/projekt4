package com.example.cezar.projekt4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddMarkerActivity extends AppCompatActivity {

    private double latitude;
    private double lognitude;
    private EditText markerNameText;
    private EditText markerDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);


        latitude = getIntent().getDoubleExtra("Latitude", 0);
        lognitude = getIntent().getDoubleExtra("Lognitude", 0);
        markerNameText = (EditText) findViewById(R.id.nameText);
        markerDescriptionText = (EditText) findViewById(R.id.descriptionText);

    }

    public void addMarker(View view) {
        String name = String.valueOf(markerNameText.getText());
        String description =  String.valueOf(markerDescriptionText.getText());
        new Marker(lognitude,latitude,description,name);

        Marker.writeMarkers();
        Intent openSecondActivity = new Intent(this, MapsActivity1.class);
        startActivity(openSecondActivity);

    }

    public void clearList(View view){
        Marker.clearMarkers();
        Marker.writeMarkers();

    }
}
