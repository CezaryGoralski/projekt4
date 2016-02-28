package com.example.cezar.projekt4.RecyclerView;

/**
 * Created by Marcin on 27.02.2016.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cezar.projekt4.Activites.PlaceActivity;
import com.example.cezar.projekt4.Markers.Marker;
import com.example.cezar.projekt4.R;

import java.util.List;
import java.util.Map;

public class SelectablePlaceModelHolder extends RecyclerView.ViewHolder {

    public TextView nameTextView;
    public TextView addressTextView;

    public ImageView iconView;

    public CheckBox checkBox;

    private List<String> chosenMarkers;
    private Marker marker;

    public SelectablePlaceModelHolder(View itemView) {
        super(itemView);

        nameTextView = (TextView) itemView.findViewById(R.id.place_title);
        addressTextView = (TextView) itemView.findViewById(R.id.place_address);

        iconView = (ImageView) itemView.findViewById(R.id.place_image);

        checkBox = (CheckBox) itemView.findViewById(R.id.selected_checkbox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean is = false;
                String newHash = null;
                checkBox.setChecked(isChecked);

                for(String hash : chosenMarkers){
                    String tempHash = marker.getName()+marker.getAddress();
                    if(hash.equalsIgnoreCase(tempHash)){
                        is = true;
                        newHash = tempHash;
                        break;
                    }
                }

                if(isChecked){
                    if(!is){
                        chosenMarkers.add(newHash);
                    }
                }else{
                    if(is){
                        chosenMarkers.remove(newHash);
                    }
                }

            }
        });

    }

    public void setMarker(Marker marker){
        this.marker = marker;
    }

    public void setChosenMarkers(List<String> markers){
        this.chosenMarkers = markers;
    }

}