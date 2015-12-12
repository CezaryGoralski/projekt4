package com.example.cezar.projekt4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;


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
    }

    public void openMap(View view) {
        Intent openSecondActivity = new Intent(this, MapsActivity1.class);
        startActivity(openSecondActivity);
    }

    public void openAddActivity(View view) {
        Intent openSecondActivity = new Intent(this, AddMarkerActivity.class);
        openSecondActivity.putExtra("Latitude", latitude);
        openSecondActivity.putExtra("Lognitude", lognitude);
        startActivity(openSecondActivity);
    }


}
