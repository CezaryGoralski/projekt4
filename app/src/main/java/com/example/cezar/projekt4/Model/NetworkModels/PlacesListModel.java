package com.example.cezar.projekt4.Model.NetworkModels;

import com.example.cezar.projekt4.Markers.Marker;

import java.util.ArrayList;

/**
 * Created by Cezar on 2016-02-28.
 */


@lombok.Getter
@lombok.Setter
@lombok.ToString
public class PlacesListModel {
    ArrayList<Marker> places = new ArrayList<Marker>();
}
