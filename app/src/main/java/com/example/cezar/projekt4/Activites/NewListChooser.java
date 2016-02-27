package com.example.cezar.projekt4.Activites;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;

import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.R;
import com.example.cezar.projekt4.RecyclerView.DividerItemDecoration;
import com.example.cezar.projekt4.RecyclerView.PlaceModelViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 27.02.2016.
 */
public class NewListChooser extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView mylist;
    private ArrayList<Marker> mMarkers;
    private PlaceModelViewAdapter placeModelViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_list_chooser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Marker.readMarkers();

        mMarkers = Marker.getList();

        placeModelViewAdapter = new PlaceModelViewAdapter(mMarkers);

        mylist = (RecyclerView) findViewById(R.id.chooser);
        mylist.setLayoutManager(new LinearLayoutManager(this));
        mylist.addItemDecoration(new DividerItemDecoration(this));

        mylist.setAdapter(placeModelViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_chooser_menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Marker> filteredModelList = filter(mMarkers, query);
        placeModelViewAdapter.animateTo(filteredModelList);
        mylist.scrollToPosition(0);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Marker> filter(List<Marker> models, String query) {
        query = query.toLowerCase();

        final List<Marker> filteredModelList = new ArrayList<>();
        for (Marker model : models) {
            final String name = model.getName().toLowerCase();
            final String address = model.getAddress().toLowerCase();
            final String category = model.getCategory().toLowerCase();

            if (name.contains(query) || address.contains(query) || category.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
