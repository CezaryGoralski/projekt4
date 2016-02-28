package com.example.cezar.projekt4.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.Model.NetworkModels.PlacesListModel;
import com.example.cezar.projekt4.Network.ListOfPlacesSpiceRequest;
import com.example.cezar.projekt4.R;
import com.example.cezar.projekt4.RecyclerView.DividerItemDecoration;
import com.example.cezar.projekt4.RecyclerView.PlaceModelViewAdapter;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;


public class PlacesActivity extends AppCompatActivity {
    private double latitude;
    private double lognitude;
    private RecyclerView mylist;
    private SpiceManager spiceManager = new SpiceManager(
            UncachedSpiceService.class);
    private PlaceModelViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_places);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        latitude = getIntent().getDoubleExtra("Latitude", 0);
        lognitude = getIntent().getDoubleExtra("Lognitude", 0);

        Marker.readMarkers();
        adapter = new PlaceModelViewAdapter(Marker.getList());

        mylist = (RecyclerView) findViewById(R.id.lists);
        mylist.setLayoutManager(new LinearLayoutManager(this));
        mylist.addItemDecoration(new DividerItemDecoration(this));

        mylist.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.places_menu, menu);
        return true;
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


    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    private void performRequest() {


        PlacesActivity.this.setProgressBarIndeterminateVisibility(true);

        ListOfPlacesSpiceRequest request = new ListOfPlacesSpiceRequest();
        spiceManager.execute(request, new QueueRequestListener());

    }

    private final class QueueRequestListener implements
            RequestListener<PlacesListModel> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(PlacesActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(PlacesListModel kolejkiDto) {
            PlacesActivity.this.setProgressBarIndeterminateVisibility(false);
            ArrayList<Marker> kolejkaDto;
            if (kolejkiDto != null) {
                kolejkaDto = new ArrayList<Marker>(kolejkiDto.getPlaces());
            } else {
                kolejkaDto = new ArrayList<Marker>();
                Toast.makeText(PlacesActivity.this,
                        "Error: " + "brak danych", Toast.LENGTH_SHORT)
                        .show();
            }
        /*    recList = (RecyclerView) findViewById(R.id.recycledList2);
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(QueueToOfficeListActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);*/

            adapter = new PlaceModelViewAdapter(kolejkaDto);
            //  officeDataAdapter.setClickListener(this);
            mylist.setAdapter(adapter);
        }


    }


}
