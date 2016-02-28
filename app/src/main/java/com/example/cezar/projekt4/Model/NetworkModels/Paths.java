package com.example.cezar.projekt4.Model.NetworkModels;

import com.example.cezar.projekt4.Markers.Marker;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kuba on 13.12.2015.
 */

@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Paths implements Serializable {
    private Long id;
    private List<Marker> places;
    private String name;

}
