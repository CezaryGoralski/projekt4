package com.example.cezar.projekt4.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cezar.projekt4.R;

import org.w3c.dom.Text;

/**
 * Created by Marcin on 27.02.2016.
 */
public class PlaceActivity extends AppCompatActivity {

    private TextView titleTextView,
            descriptionTextView,
            categoryTextView,
            addressTextView,
            timeOneTextView,
            timeTwoTextView;

    private ImageView headerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_place);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        titleTextView = (TextView) findViewById(R.id.place_title);
        descriptionTextView = (TextView) findViewById(R.id.description_text);
        categoryTextView = (TextView) findViewById(R.id.category_test);
        addressTextView = (TextView) findViewById(R.id.address_text);
        timeOneTextView = (TextView) findViewById(R.id.week_hours);
        timeTwoTextView = (TextView) findViewById(R.id.weekend_hours);

        headerImage = (ImageView) findViewById(R.id.circular_header);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.place_menu, menu);
        return true;
    }
}
