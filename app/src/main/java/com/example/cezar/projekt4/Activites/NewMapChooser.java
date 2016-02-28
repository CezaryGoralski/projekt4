package com.example.cezar.projekt4.Activites;

/**
 * Created by Marcin on 28.02.2016.
 */

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.cezar.projekt4.HttpConnection;
import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.PathJSONParser;
import com.example.cezar.projekt4.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

/**
 * Created by Marcin on 27.02.2016.
 */
public class NewMapChooser extends AppCompatActivity implements OnMapReadyCallback {

    private MapView myMap;
    private ArrayList<Marker> mMarkers;

    private TextView numberTextView,
            distanceTextView;
    private GoogleMap mapa;
    private ArrayList<com.google.android.gms.maps.model.Marker> markersList = new ArrayList<com.google.android.gms.maps.model.Marker>();
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
    public void onMapReady(GoogleMap map) {
        mapa = map;
        for (Marker m : mMarkers) {
            MarkerOptions marketOption = new MarkerOptions()
                    .position(new LatLng(m.getLatitude(), m.getLongitude()))
                    .title(m.getName());
            com.google.android.gms.maps.model.Marker mapMarker = map.addMarker(marketOption);
            markersList.add(mapMarker);
        }
        System.out.println("Wczytywanie");
        if (mMarkers.size() > 1) {
            String url = getMapsApiDirectionsUrl(mMarkers);
            ReadTask readTask = null;
            try {
                ReadTask downloadTask = new ReadTask();
                downloadTask.execute(url).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Koniec wczytywania");
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

    private String getMapsApiDirectionsUrl(ArrayList<Marker> markersList) {

        String url = null;
        Log.e("size", String.valueOf(markersList.size()));
        if (markersList.size() > 1) {
            // String origin = "origin="+  markersList.get(markersList.size() - 1).getLatitude() + "," + markersList.get(markersList.size() - 1).getLongitude();
            String origin = "origin=" + markersList.get(0).getLatitude() + "," + markersList.get(0).getLongitude();
            String destination = "destination=" + markersList.get(markersList.size() - 1).getLatitude() + "," + markersList.get(markersList.size() - 1).getLongitude();
            String sensor = "sensor=false";
            String output = "json";
            String params = origin + "&" + destination + "&" + sensor;
            // markersList.remove(0);
            markersList.remove(0);
            markersList.remove(markersList.size() - 1);
            if (markersList.size() > 0) {
                String waypoints = "waypoints=optimize:true";
                for (Marker m : markersList) {
                    waypoints = waypoints + "|" + m.getLatitude() + "," + m.getLongitude();
                }
                params = origin + "&" + destination + "&" + waypoints + "&" + sensor;
            }
            url = "https://maps.googleapis.com/maps/api/directions/"
                    + output + "?" + params;
        }
        System.out.println(url);
        return url;
    }

    private class ReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                HttpConnection http = new HttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            Log.e("data", "data readed");
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }


    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                PathJSONParser parser = new PathJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            Log.e("data", String.valueOf(routes.size()));
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    //   Log.e("data", position.toString());
                    points.add(position);
                    //  mypoints.add(position);
                }

                polyLineOptions.addAll(points);
                polyLineOptions.width(10);
                polyLineOptions.color(Color.BLUE);
            }
            Log.e("data", "transformed");
            mapa.addPolyline(polyLineOptions);

        }

        //    Intent loginIntent = new Intent(this, LoginActivity.class);
        //    startActivity(loginIntent);


    }
}
