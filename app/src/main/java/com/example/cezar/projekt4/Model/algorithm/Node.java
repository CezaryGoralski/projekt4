package com.example.cezar.projekt4.Model.algorithm;

import java.util.ArrayList;

/**
 * Created by Cezar on 2015-12-12.
 */
public class Node {

    private String label;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private double cost = 0;
    private Boolean visited = false;
    private int group;



    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Node(String label, int group){
        this.label = label;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Node [label=" + label + ", cost=" + cost
                + "]";
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }



}

