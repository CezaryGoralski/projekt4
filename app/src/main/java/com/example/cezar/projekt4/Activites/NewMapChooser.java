package com.example.cezar.projekt4.Activites;

/**
 * Created by Marcin on 28.02.2016.
 */

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.cezar.projekt4.HttpConnection;
import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.PathJSONParser;
import com.example.cezar.projekt4.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Marcin on 27.02.2016.
 */
public class NewMapChooser extends AppCompatActivity implements OnMapReadyCallback {

    private ArrayList<Marker> mMarkers;

    private TextView numberTextView,
            distanceTextView;
    private GoogleMap mapa;
    private ArrayList<com.google.android.gms.maps.model.Marker> markersList = new ArrayList<com.google.android.gms.maps.model.Marker>();
    private static final LatLng LOWER_MANHATTAN = new LatLng(40.722543,
            -73.998585);
    private static final LatLng BROOKLYN_BRIDGE = new LatLng(40.7057, -73.9964);
    private static final LatLng WALL_STREET = new LatLng(40.7064, -74.0094);

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
            distanceTextView.setText(String.valueOf(calculateDistance(mMarkers)));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_chooser_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_next) {
            Marker.setList(mMarkers);
            Marker.writeMarkers();
            Intent intent = new Intent(this, MapsActivity1.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapa = map;
        map.setMyLocationEnabled(true);

        for (Marker m : mMarkers) {
            MarkerOptions marketOption = new MarkerOptions()
                    .position(new LatLng(m.getLatitude(), m.getLongitude()))
                    .snippet(m.getAddress()+"|"+m.getCategory())
                    .title(m.getName());
            com.google.android.gms.maps.model.Marker mapMarker = map.addMarker(marketOption);
            markersList.add(mapMarker);
        }

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mMarkers.get(0).getLatitude(), mMarkers.get(0).getLongitude()), 13));

        /*map.addMarker(new MarkerOptions()
                .position(LOWER_MANHATTAN)
                .title("manhatan"));

        map.addMarker(new MarkerOptions()
                .position(BROOKLYN_BRIDGE)
                .title("bridge"));

        map.addMarker(new MarkerOptions()
                .position(WALL_STREET)
                .title("wall_strett"));*/

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

        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                mapa.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(com.google.android.gms.maps.model.Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(com.google.android.gms.maps.model.Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.place_info_window, null);

                        String snippet = marker.getSnippet();
                        String address = snippet.substring(0, snippet.indexOf("|"));
                        String category = snippet.substring(snippet.indexOf("|") + 1, snippet.length());

                        final TextView titleTextView = (TextView) v.findViewById(R.id.place_title);
                        final TextView addressTextView = (TextView) v.findViewById(R.id.place_address);
                        final TextView categoryTextView = (TextView) v.findViewById(R.id.place_category);

                        titleTextView.setText(marker.getTitle());
                        addressTextView.setText(address);
                        categoryTextView.setText(category);

                        return v;
                    }

                });

                mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick(com.google.android.gms.maps.model.Marker marker) {
                        Intent intent = new Intent(NewMapChooser.this, PlaceActivity.class);
                        Marker mm = null;
                        Log.d("NewMapChooser", "Marker title: " + marker.getTitle());
                        for (Marker m : mMarkers) {
                            Log.d("NewMapChooser", "mMarkers:: " + m.getName());
                            if (m.getName().equalsIgnoreCase(marker.getTitle())) {
                                mm = m;
                                break;
                            }
                        }
                        intent.putExtra("marker", mm);
                        startActivity(intent);
                    }

                });
                return false;
            }
        });
    }

    private int calculateDistance(ArrayList<Marker> markers) {
        float distance = 0;
        int size = markers.size();

        for(int i = 0; i < size-1; i++){
            Marker previous = markers.get(i);
            Marker next = markers.get(i + 1);

            Location loc1 = new Location("");
            loc1.setLatitude(previous.getLatitude());
            loc1.setLongitude(previous.getLongitude());

            Location loc2 = new Location("");
            loc2.setLatitude(next.getLatitude());
            loc2.setLongitude(next.getLongitude());

            distance += loc1.distanceTo(loc2);
        }

        return Math.round(distance/1000);
    }

    private String getMapsApiDirectionsUrl(ArrayList<Marker> MarkersList) {
        ArrayList<Marker> myMarkersList = new ArrayList<Marker>(MarkersList);
        String url = null;
        Log.e("size", String.valueOf(myMarkersList .size()));
        if (myMarkersList .size() > 1) {
            // String origin = "origin="+  markersList.get(markersList.size() - 1).getLatitude() + "," + markersList.get(markersList.size() - 1).getLongitude();
            String origin = "origin=" + myMarkersList .get(0).getLatitude() + "," + myMarkersList .get(0).getLongitude();
            String destination = "destination=" + myMarkersList.get(myMarkersList .size() - 1).getLatitude() + "," + myMarkersList .get(myMarkersList.size() - 1).getLongitude();
            String sensor = "sensor=false";
            String output = "json";
            String params = origin + "&" + destination + "&" + sensor;
            // markersList.remove(0);
            myMarkersList .remove(0);
            myMarkersList .remove(myMarkersList.size() - 1);
            if (myMarkersList .size() > 0) {
                String waypoints = "waypoints=optimize:true";
                for (Marker m : myMarkersList) {
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
