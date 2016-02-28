package com.example.cezar.projekt4.Activites;

/**
 * Created by Marcin on 28.02.2016.
 */

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Marcin on 27.02.2016.
 */
public class NewMapChooser extends AppCompatActivity implements OnMapReadyCallback {

    private MapView myMap;
    private ArrayList<Marker> mMarkers;

    private TextView numberTextView,
            distanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_list_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mMarkers = (ArrayList<Marker>) getIntent().getExtras().getSerializable("markers");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        numberTextView = (TextView) findViewById(R.id.number_text);
        distanceTextView = (TextView) findViewById(R.id.distance_text);

        if (mMarkers != null) {
            numberTextView.setText(String.valueOf(mMarkers.size()));
            distanceTextView.setText(String.valueOf(4));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_chooser_menu, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    private int calculateDistance(ArrayList<Marker> markers) {
        int distance = 0;
        ListIterator<Marker> it = markers.listIterator();

        while (it.hasNext()) {
            if (it.hasPrevious()) {
                Marker previous = it.previous();
                Marker next = it.next();

                Location loc1 = new Location("");
                loc1.setLatitude(previous.getLatitude());
                loc1.setLongitude(previous.getLongitude());

                Location loc2 = new Location("");
                loc2.setLatitude(next.getLatitude());
                loc2.setLongitude(next.getLongitude());

                distance += (int) loc1.distanceTo(loc2);
            }
        }

        return distance;
    }
}
