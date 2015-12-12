package com.example.cezar.projekt4;


public class Edge {
    private Node beginNode;
    private Node endNode;
    private double cost;


    public Edge(Node beginNode, Node endNode,  double cost){
        this.endNode = endNode;
        this.beginNode = beginNode;
        this.cost = cost;
    }




    public Node getBeginNode() {
        return beginNode;
    }




    public void setBeginNode(Node beginNode) {
        this.beginNode = beginNode;
    }




    @Override
    public String toString() {
        return "Edge [endNode=" + endNode + ", cost=" + cost + "]";
    }



    public Node getEndNode() {
        return endNode;
    }



    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }



    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }




}
