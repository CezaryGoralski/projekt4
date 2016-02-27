package com.example.cezar.projekt4.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cezar.projekt4.ImageTransform.CircularTransform;
import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.ion.Ion;

/**
 * Created by Marcin on 27.02.2016.
 */
public class PlaceActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView titleTextView,
            descriptionTextView,
            categoryTextView,
            addressTextView,
            timeOneTextView,
            timeTwoTextView;

    private ImageView headerImage;

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_place);

        marker = (Marker) getIntent().getSerializableExtra("Marker");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(marker.getName());

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        MapView map = (MapView) findViewById(R.id.map);
        map.getMapAsync(this);
        map.onCreate(savedInstanceState);

        MapsInitializer.initialize(this);

        titleTextView = (TextView) findViewById(R.id.place_title);
        descriptionTextView = (TextView) findViewById(R.id.description_text);
        categoryTextView = (TextView) findViewById(R.id.category_test);
        addressTextView = (TextView) findViewById(R.id.address_text);
        timeOneTextView = (TextView) findViewById(R.id.week_hours);
        timeTwoTextView = (TextView) findViewById(R.id.weekend_hours);

        headerImage = (ImageView) findViewById(R.id.circular_header);

        titleTextView.setText(marker.getName());
        descriptionTextView.setText(marker.getDescription());
        categoryTextView.setText(marker.getCategory());
        addressTextView.setText(marker.getAddress());
        timeTwoTextView.setText("09:00 - 16:00");
        timeOneTextView.setText("08:00 - 20:00");

        Ion.with(headerImage)
                .transform(new CircularTransform())
                .load(marker.getImg_url());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.place_menu, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        LatLng warszau = new LatLng(marker.getLatitude(), marker.getLongitude());
        googleMap.addMarker(new MarkerOptions()
                .position(warszau)
                .title(marker.getName())
                .snippet(marker.getAddress()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(warszau, 14));
    }
}
