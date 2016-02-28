package com.example.cezar.projekt4.Markers;

import android.os.Environment;
import android.util.Log;

import com.example.cezar.projekt4.Model.MarkerIsVisitedComparator;
import com.example.cezar.projekt4.Model.algorithm.Edge;
import com.example.cezar.projekt4.Model.algorithm.EdgeComparator;
import com.example.cezar.projekt4.Model.algorithm.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Cezar on 2015-12-07.
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Marker implements Serializable {
    private static ArrayList<Marker> list = new ArrayList<Marker>();
    private double longitude;
    private double latitude;
    private String description;
    private String name;
    private String address;
    private int numberOfPlaces;

    // private String info;
    private String category;
    private String url;
    private String img_url;

    private boolean selected;

    private boolean visited;

    public static ArrayList<Marker> getList() {
        Collections.sort(list, new MarkerIsVisitedComparator());
        return list;
    }

    public static ArrayList<Marker> getToDoList() {
        ArrayList<Marker> toDoList = new ArrayList<Marker>();

        for (Marker m : getList()) {
            if (!m.isVisited()) {
                toDoList.add(m);
            }

        }
        Log.e("todolist", String.valueOf(toDoList.size()));
        return toDoList;
    }

    public static void setList(ArrayList<Marker> list) {
        Marker.list = list;
    }

    public Marker(double longitude, double latitude, String description, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.name = name;
        list.add(this);
    }


    public Marker(String name) {
        this.name = name;
        list.add(this);
    }


    public static void writeMarkers() {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "markers.ser");
            //    if(!file.exists()) {
            file.createNewFile();
            Log.e("msg", "file created");
            //      }
            FileOutputStream fileOut =
                    new FileOutputStream(file, false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Marker m : list)
                out.writeObject(m);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in markers.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void readTestMarkers() {

        list.add(new Marker(52.01, 52.01, "1", "1"));
        list.add(new Marker(52.01, 53.01, "2", "2"));
        list.add(new Marker(51.01, 53.01, "3", "3"));
    }

    public static void readMarkers() {
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "markers.ser");
        if (!f.exists()) {
            try {

                FileInputStream fileIn = new FileInputStream(f);

                ObjectInputStream in = new ObjectInputStream(fileIn);
                Marker m = (Marker) in.readObject();
                while (m != null) {
                    list.add(m);
                    m = (Marker) in.readObject();
                }
                in.close();
                fileIn.close();
            } catch (IOException i) {
                i.printStackTrace();
                return;
            } catch (ClassNotFoundException c) {
                System.out.println("Markers class not found");
                c.printStackTrace();
                return;
            }
        }
    }

    public static void markAsVisited(String name) {
        for (Marker m : list) {
            if (m.getName().equals(name)) {
                m.setVisited(true);
                break;
            }
        }
    }

    public static void clearMarkers() {
        list = new ArrayList<Marker>();
    }


    public static ArrayList<Marker> executeKruskal() {
        //readMarkers();
        ArrayList<Marker> markers = Marker.getToDoList();
        System.out.println("size = " + markers.size());
        if (markers.size() > 1) {
            TreeMap<String, Marker> markersMap = new TreeMap<String, Marker>();

            for (Marker m : markers) {
                markersMap.put(m.getName(), m);
            }


            TreeMap<String, Node> mapa = new TreeMap<String, Node>();// mapa przechowujaca wszystkie wierzcholki

            ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
            int ile = 0;

            for (Marker m1 : markers) {
                for (Marker m2 : markers) {
                    double distance = Math.sqrt(((m1.getLatitude() - m2.getLatitude()) * (m1.getLatitude() - m2.getLatitude())) + ((m1.getLongitude() - m2.getLongitude()) * (m1.getLongitude() - m2.getLongitude())));
                    String label1 = m1.getName();
                    String label2 = m2.getName();
                    if (!mapa.containsKey(label1)) {
                        mapa.put(label1, new Node(label1, ile));
                        ile++;
                    }

                    if (!mapa.containsKey(label2)) {
                        mapa.put(label2, new Node(label2, ile));
                        ile++;
                    }

                    listOfEdges.add(new Edge(mapa.get(label1), mapa.get(label2), distance));
                }
            }


            Collections.sort(listOfEdges, new EdgeComparator());
            ArrayList<Node> listOfNodes = new ArrayList<Node>(mapa.values());


            for (Node node : listOfNodes) {
                System.out.println(node.getGroup());
            }


            ArrayList<Edge> kruskal = new ArrayList<Edge>();


            for (Edge edge : listOfEdges) {
                Node begin = edge.getBeginNode();
                Node end = edge.getEndNode();

                if (begin.getGroup() != end.getGroup()) {

                    for (Node node : listOfNodes) {
                        if (node.getGroup() == end.getGroup()) {
                            node.setGroup(begin.getGroup());
                        }
                    }
                    kruskal.add(edge);
                }
            }


            for (Node node : listOfNodes) {
                System.out.println(node.getGroup());
            }


            ArrayList<Marker> markesAfterKruskal = new ArrayList<Marker>();

            for (Edge edge : kruskal) {
                if (!markesAfterKruskal.contains(markersMap.get(edge.getBeginNode().getLabel())))
                    markesAfterKruskal.add(markersMap.get(edge.getBeginNode().getLabel()));
                if (!markesAfterKruskal.contains(markersMap.get(edge.getEndNode().getLabel())))
                    markesAfterKruskal.add(markersMap.get(edge.getEndNode().getLabel()));
            }


            return markesAfterKruskal;
        } else
            return markers;
    }


    public static void convertToMarkers(List<Marker> lista) {
        clearMarkers();
        ArrayList<Marker> newlist = new ArrayList<Marker>(lista);
        // setList(new ArrayList<Marker>(lista));
        System.out.println("ConvertToMarkers");/*
        for(Marker path : lista){

            newlist.add(path);
        }*/
        list = newlist;
        System.out.println("end");

    }
}


