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
import com.example.cezar.projekt4.Model.DataFromNetwork;
import com.example.cezar.projekt4.Model.Path;
import com.example.cezar.projekt4.Model.Paths;
import com.example.cezar.projekt4.Network.ListOfListSpiceRequest;
import com.example.cezar.projekt4.R;
import com.example.cezar.projekt4.RecyclerView.DividerItemDecoration;
import com.example.cezar.projekt4.RecyclerView.ListModelViewAdapter;
import com.example.cezar.projekt4.RecyclerView.PlaceModelViewAdapter;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;


public class ListOfPathsActivity extends AppCompatActivity {
    private double latitude;
    private double lognitude;
    private RecyclerView mylist;
    private SpiceManager spiceManager = new SpiceManager(
            UncachedSpiceService.class);
    private ListModelViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        performRequest();
        adapter = new ListModelViewAdapter(new ArrayList<Paths>());

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


       ListOfPathsActivity.this.setProgressBarIndeterminateVisibility(true);

        ListOfListSpiceRequest request = new ListOfListSpiceRequest();
        spiceManager.execute(request, new QueueRequestListener());

    }

    private final class QueueRequestListener implements
            RequestListener<DataFromNetwork> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(ListOfPathsActivity.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(DataFromNetwork kolejkiDto) {
            ListOfPathsActivity.this.setProgressBarIndeterminateVisibility(false);
            ArrayList<Paths> kolejkaDto = new ArrayList<Paths>();
            if(kolejkiDto != null) {
                kolejkaDto = new ArrayList<Paths>(kolejkiDto.getTrips());
            }
            else {
                kolejkaDto = new ArrayList<Paths>();
                Toast.makeText(ListOfPathsActivity.this,
                        "Error: " + "brak danych", Toast.LENGTH_SHORT)
                        .show();
            }
        /*    recList = (RecyclerView) findViewById(R.id.recycledList2);
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(QueueToOfficeListActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);*/

            ListModelViewAdapter adapter= new ListModelViewAdapter(kolejkaDto);
            //  officeDataAdapter.setClickListener(this);
            mylist.setAdapter(adapter);
        }


    }



}
