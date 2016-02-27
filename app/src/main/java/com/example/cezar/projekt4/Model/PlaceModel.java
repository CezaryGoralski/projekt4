package com.example.cezar.projekt4.Model;

/**
 * Created by Marcin on 27.02.2016.
 */
public class PlaceModel {
    private String name;
    private String address;
    private String image;
    private String tag;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public String getTag(){
        return tag;
    }
}
