package com.example.cezar.projekt4.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Cezar on 2016-02-27.
 */

@lombok.Getter
@lombok.Setter
@lombok.ToString
public class DataFromNetwork {
    ArrayList<Paths> trips  = new ArrayList<Paths>();
}
