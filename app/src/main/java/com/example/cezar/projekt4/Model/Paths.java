package com.example.cezar.projekt4.Model;

import com.example.cezar.projekt4.Model.Path;

import java.util.List;

/**
 * Created by Kuba on 13.12.2015.
 */

@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Paths {
    private Long id;
    private List<Path> places;
    private String name;

}
