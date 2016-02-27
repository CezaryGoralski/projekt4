package com.example.cezar.projekt4.Model;

import com.example.cezar.projekt4.Markers.Marker;

import java.util.Comparator;

/**
 * Created by Cezar on 2015-12-12.
 */
public class MarkerIsVisitedComparator implements Comparator<Marker> {
    @Override
    public int compare(Marker lhs, Marker rhs) {
        if (lhs.isVisited() == rhs.isVisited())
            return 0;
        if (lhs.isVisited() && !rhs.isVisited())
            return 1;
        else
            return -1;

    }
}
