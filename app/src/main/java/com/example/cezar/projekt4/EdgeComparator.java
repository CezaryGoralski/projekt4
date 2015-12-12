package com.example.cezar.projekt4;

import java.util.Comparator;

/**
 * Created by Cezar on 2015-12-12.
 */
public class EdgeComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge arg0, Edge arg1) {

        if(arg0.getCost() == arg1.getCost()){
            return 0;
        }else{
            if(arg0.getCost() > arg1.getCost())
                return 1;
            else
                return -1;
        }

    }

}