/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.cezar.projekt4;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location. To
 * track changes to the users location on the map, we request updates from the
 * {@link com.google.android.gms.location.FusedLocationProviderApi}.
 */
public class MapsActivity1 extends FragmentActivity
        implements
        ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener,
        OnMyLocationButtonClickListener,
        OnMapReadyCallback {

    private GoogleApiClient mGoogleApiClient;
    private TextView mMessageView;
    private ArrayList<com.google.android.gms.maps.model.Marker> markersList = new ArrayList<com.google.android.gms.maps.model.Marker>();

    // These settings are the same as the settings for the map. They will in fact give you updates
    // at the maximal rates currently possible.
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);
        mMessageView = (TextView) findViewById(R.id.message_text);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        Marker.readMarkers();
        for(Marker m: Marker.getToDoList()){
            MarkerOptions marketOption = new MarkerOptions()
                    .position(new LatLng(m.getLatitude(), m.getLognitude()))
                    .title(m.getName());
          com.google.android.gms.maps.model.Marker mapMarker =   map.addMarker(marketOption);
            markersList.add(mapMarker);
        }
        Geocoder g = new Geocoder(this, Locale.getDefault());
        List<Address> a = new ArrayList<Address>();

        try {
         a = g.getFromLocationName("Warszawa",1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Address m: a){
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(m.getLatitude(), m.getLongitude()))
                    .title("warszawa"));
        }



        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                for (com.google.android.gms.maps.model.Marker marker : markersList) {
                    if (Math.abs(marker.getPosition().latitude - latLng.latitude) < 0.05 && Math.abs(marker.getPosition().longitude - latLng.longitude) < 0.05) {
                        // marker.remove();
                        // markersList.remove(marker);
                        Intent openSecondActivity = new Intent(MapsActivity1.this, MarkAsVisitedMarker.class);
                        openSecondActivity.putExtra("Name", marker.getTitle());
                        Toast.makeText(MapsActivity1.this, "got clicked", Toast.LENGTH_SHORT).show();
                        startActivity(openSecondActivity);
                        break;
                    }
                }

            }
        });
    }

      /**
       * Button to get current Location. This demonstrates how to get the current Location as required
       * without needing to register a LocationListener.
       */
      public void showMyLocation(View view) {
          if (mGoogleApiClient.isConnected()) {
              String msg = "Location = "
                      + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
              Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
          }
      }

      /**
       * Implementation of {@link LocationListener}.
       */
      @Override
      public void onLocationChanged(Location location) {
          mMessageView.setText("Location = " + location);
      }

      /**
       * Callback called when connected to GCore. Implementation of {@link ConnectionCallbacks}.
       */
      @Override
      public void onConnected(Bundle connectionHint) {
          LocationServices.FusedLocationApi.requestLocationUpdates(
                  mGoogleApiClient,
                  REQUEST,
                  this);  // LocationListener
      }

      /**
       * Callback called when disconnected from GCore. Implementation of {@link ConnectionCallbacks}.
       */
      @Override
      public void onConnectionSuspended(int cause) {
          // Do nothing
      }

      /**
       * Implementation of {@link OnConnectionFailedListener}.
       */
      @Override
      public void onConnectionFailed(ConnectionResult result) {
          // Do nothing
      }

      @Override
      public boolean onMyLocationButtonClick() {
          Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
          // Return false so that we don't consume the event and the default behavior still occurs
          // (the camera animates to the user's current position).
          return false;
      }

      public void showList(View view) {
          Intent openSecondActivity = new Intent(this, ListActivity.class);
          openSecondActivity.putExtra("Latitude", LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
          openSecondActivity.putExtra("Lognitude", LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());
          startActivity(openSecondActivity);
      }
  }
