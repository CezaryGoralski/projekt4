package com.example.cezar.projekt4.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cezar.projekt4.HttpConnection;
import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.Model.NetworkModels.DataFromNetwork;
import com.example.cezar.projekt4.PathJSONParser;
import com.example.cezar.projekt4.R;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location. To
 * track changes to the users location on the map, we request updates from the
 * {@link com.google.android.gms.location.FusedLocationProviderApi}.
 */
public class MapsActivity1 extends AppCompatActivity
        implements
        ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener,
        OnMyLocationButtonClickListener,
        OnMapReadyCallback {

    private GoogleApiClient mGoogleApiClient;
    private TextView mMessageView;
    private ArrayList<com.google.android.gms.maps.model.Marker> markersList = new ArrayList<com.google.android.gms.maps.model.Marker>();
    private GoogleMap mapa;
    private boolean refresh = true;
    private double latitude;
    private double lognitude;
    private static final LatLng LOWER_MANHATTAN = new LatLng(40.722543,
            -73.998585);
    private static final LatLng BROOKLYN_BRIDGE = new LatLng(40.7057, -73.9964);
    private static final LatLng WALL_STREET = new LatLng(40.7064, -74.0094);

    private ArrayList<Marker> mMarkers;

    PolylineOptions polyLineOptionsb = null;
    private ArrayList<LatLng> mypoints = new ArrayList<LatLng>();

    // These settings are the same as the settings for the map. They will in fact give you updates
    // at the maximal rates currently possible.
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private PolylineOptions polylineOptionsb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlaces();
            }
        });

        FloatingActionButton listFab = (FloatingActionButton) findViewById(R.id.fabLists);
        listFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLists();
            }
        });

        FloatingActionButton refreshFab = (FloatingActionButton) findViewById(R.id.refreshFab);
        refreshFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshMarkers();
            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        refresh = getIntent().getBooleanExtra("refresh", false);
        latitude = getIntent().getDoubleExtra("Latitude", 0);
        lognitude = getIntent().getDoubleExtra("Lognitude", 0);
        /*
            String url = getMapsApiDirectionsUrl();
            ReadTask downloadTask = new ReadTask();
            downloadTask.execute(url);
         */


        //      readTaskk.execute(url);

    }

    private String getMapsApiDirectionsUrl() {

        String origin = "origin=" + LOWER_MANHATTAN.latitude + "," + LOWER_MANHATTAN.longitude;
        String destination = "destination=" + WALL_STREET.latitude + "," + WALL_STREET.longitude;
        String waypoints = "waypoints=optimize:true|" + BROOKLYN_BRIDGE.latitude + ","
                + BROOKLYN_BRIDGE.longitude;

        String sensor = "sensor=false";
        String params = origin + "&" + destination + "&" + waypoints + "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + params;
        System.out.println(url);
        return url;
    }

    private String getMapsApiDirectionsUrl(ArrayList<Marker> markersList) {

        String url = null;
        Log.e("size", String.valueOf(markersList.size()));
        if (markersList.size() > 0) {
            // String origin = "origin="+  markersList.get(markersList.size() - 1).getLatitude() + "," + markersList.get(markersList.size() - 1).getLongitude();
            String origin = "origin=" + latitude + "," + lognitude;
            String destination = "destination=" + markersList.get(markersList.size() - 1).getLatitude() + "," + markersList.get(markersList.size() - 1).getLongitude();
            String sensor = "sensor=false";
            String output = "json";
            String params = origin + "&" + destination + "&" + sensor;
            // markersList.remove(0);
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


    private class DownloadList extends AsyncTask<Void, Void, List<Marker>> {

        @Override
        protected List<Marker> doInBackground(Void... params) {
            System.out.println("pobieranie danych");
            URL url = null;
            try {
                url = new URL("http://52.34.41.87/trips");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();

            DataFromNetwork ordersDto = null;
            ;
            try {
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");
                connect.setRequestProperty("Content-Type", "application/json");
                connect.setRequestProperty("Accept", "application/json");
                InputStream ios = connect.getInputStream();

                String body = CharStreams.toString(new InputStreamReader(ios, Charset.defaultCharset()));
                ordersDto = gson.fromJson(body, DataFromNetwork.class);
                ios.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (ordersDto != null)
                return ordersDto.getTrips().get(0).getPlaces();
            else
                return null;
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            startActivity(new Intent(this, NewListChooser.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);

        Geocoder g = new Geocoder(this, Locale.getDefault());
  /*      List<Address> a = new ArrayList<Address>();
        try {
         a = g.getFromLocationName("Warszawa",1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LatLng tmp = null;
        LatLng tmp2 = null;
        for(Address m: a){
            tmp = new LatLng(m.getLatitude(), m.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(m.getLatitude(), m.getLongitude()))
                    .title("warszawa"));
        }
        try {
            a = g.getFromLocationName("Poznan",1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Address m: a){
            tmp2 = new LatLng(m.getLatitude(), m.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(m.getLatitude(), m.getLongitude()))
                    .title("poznan"));
        }*/

        Marker.readMarkers();
        //rysowanie
        mapa = map;
        if (refresh) {
            refresh = false;


            List<Marker> paths = null;


            try {
                paths = new DownloadList().execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (paths != null)
                Marker.convertToMarkers(paths);
            System.out.println("after convert");
/*
            for (Marker m : Marker.getList()) {
                System.out.println (m.getName());
                List<Address> adresses = new ArrayList<Address>();
               try {
                    adresses = g.getFromLocationName(m.getAdress(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                   {
                        m.setLatitude(adresses.get(0).getLatitude());
                        m.setLongitude(adresses.get(0).getLongitude());                }
                        m.setVisited(false);
                    }
            }*/
            System.out.println("end after convert");
            Marker.writeMarkers();

        }


        for (Marker m : Marker.getToDoList()) {
            MarkerOptions marketOption = new MarkerOptions()
                    .position(new LatLng(m.getLatitude(), m.getLongitude())).snippet(m.getAddress()+"|"+m.getCategory())
                    .title(m.getName());
            com.google.android.gms.maps.model.Marker mapMarker = map.addMarker(marketOption);
            markersList.add(mapMarker);
        }

        if (Marker.getToDoList().size() > 1) {
            String url = getMapsApiDirectionsUrl(Marker.executeKruskal());
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

        map.addMarker(new MarkerOptions()
                .position(LOWER_MANHATTAN)
                .title("manhatan"));

        map.addMarker(new MarkerOptions()
                .position(BROOKLYN_BRIDGE)
                .title("bridge"));

        map.addMarker(new MarkerOptions()
                .position(WALL_STREET)
                .title("wall_strett"));

/*
        Log.e("data",String.valueOf(mypoints.size()));
        map.addPolyline(new PolylineOptions().addAll(mypoints).width(5).color(Color.RED));*/
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            //dupa
            @Override
            public void onMapLongClick(LatLng latLng) {
                for (com.google.android.gms.maps.model.Marker marker : markersList) {
                    if (Math.abs(marker.getPosition().latitude - latLng.latitude) < 0.05 && Math.abs(marker.getPosition().longitude - latLng.longitude) < 0.05) {
                        // marker.remove();
                        // markersList.remove(marker);
                        Intent openSecondActivity = new Intent(MapsActivity1.this, MarkAsVisitedMarker.class);
                        openSecondActivity.putExtra("Name", marker.getTitle());
                        openSecondActivity.putExtra("Latitude", latitude);
                        openSecondActivity.putExtra("Lognitude", lognitude);
                        Toast.makeText(MapsActivity1.this, "got clicked", Toast.LENGTH_SHORT).show();
                        startActivity(openSecondActivity);
                        break;
                    }
                }

            }
        });

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
                        String category = snippet.substring(snippet.indexOf("|")+1, snippet.length());

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
                        Intent intent = new Intent(MapsActivity1.this, PlaceActivity.class);
                        Marker mm = null;
                        Log.d("NewMapChooser", "Marker title: " + marker.getTitle());
                        for (Marker m : Marker.getToDoList()) {
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
        //mMessageView.setText("Location = " + location);
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


    public void refreshMarkers() {
        Intent openSecondActivity = new Intent(this, MapsActivity1.class);
        openSecondActivity.putExtra("refresh", true);
        openSecondActivity.putExtra("Latitude", LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
        openSecondActivity.putExtra("Lognitude", LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());
        startActivity(openSecondActivity);
    }

    public void showPlaces() {
        Intent openSecondActivity = new Intent(this, PlacesActivity.class);
        if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
            openSecondActivity.putExtra("Latitude", LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
            openSecondActivity.putExtra("Lognitude", LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());
        }
        startActivity(openSecondActivity);

    }

    public void showLists() {
        Intent openSecondActivity = new Intent(this, ListOfPathsActivity.class);
        startActivity(openSecondActivity);
    }
}