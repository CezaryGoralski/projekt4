package com.example.cezar.projekt4.Model.NetworkModels;

/**
 * Created by Kuba on 13.12.2015.
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Path {


    private long id;
    private String name;
    private double[] coordinates;
    private String adress;
    private String info;
    private String cathegory;
    private String url;
    private String img_url;
}
