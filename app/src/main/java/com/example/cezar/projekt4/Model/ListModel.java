package com.example.cezar.projekt4.Model;

/**
 * Created by Marcin on 27.02.2016.
 */
public class ListModel {

    private long id;
    private String name;
    private String description;
    private int numberOfPlaces;
    private boolean visited;

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setNumberOfPlaces(int numberOfPlaces){
        this.numberOfPlaces = numberOfPlaces;
    }

    public int getNumberOfPlaces(){
        return numberOfPlaces;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public boolean isVisited(){
        return visited;
    }
}
