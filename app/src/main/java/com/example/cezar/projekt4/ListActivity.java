package com.example.cezar.projekt4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ListActivity extends AppCompatActivity {
    private double latitude;
    private double lognitude;
    private ListView mylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        latitude = getIntent().getDoubleExtra("Latitude", 0);
        lognitude = getIntent().getDoubleExtra("Lognitude", 0);
        Marker.readMarkers();
        MarkerDataAdapter adapter = new MarkerDataAdapter(this, Marker.getList());

        mylist = (ListView) findViewById(R.id.listView);
        //  adapter = new ArrayAdapter<Product>(this,android.R.layout.simple_list_item_1,itemList);*/
        mylist.setAdapter(adapter);
    /*    try {
            new DownloadList().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }

    public void openMap(View view) {
        Intent openSecondActivity = new Intent(this, MapsActivity1.class);
        openSecondActivity.putExtra("Latitude", latitude);
        openSecondActivity.putExtra("Lognitude", lognitude);
        startActivity(openSecondActivity);
    }

    public void openAddActivity(View view) {
        Intent openSecondActivity = new Intent(this, AddMarkerActivity.class);
        openSecondActivity.putExtra("Latitude", latitude);
        openSecondActivity.putExtra("Lognitude", lognitude);
        startActivity(openSecondActivity);
    }

/*
    private class DownloadList extends AsyncTask<Void, Void, List<RequestOrderDto>> {

        @Override
        protected List<RequestOrderDto> doInBackground(Void... params) {

            URL url = null;
            try {
                url = new URL("http://b3982186.ngrok.io/orders");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();

            OrderDto[] ordersDto = new OrderDto[0];
;
            try {
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setRequestMethod("GET");
                connect.setRequestProperty("Content-Type", "application/json");
                connect.setRequestProperty("Accept", "application/json");
                InputStream ios = connect.getInputStream();

                String body = CharStreams.toString(new InputStreamReader(ios, Charset.defaultCharset()));
                ordersDto = gson.fromJson(body, OrderDto[].class);
                ios.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(ordersDto[0]);
            return ordersDto[0].getRequestOrder();
        }
    }*/

}
