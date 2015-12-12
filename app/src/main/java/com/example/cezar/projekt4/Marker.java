package com.example.cezar.projekt4;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Cezar on 2015-12-07.
 */
public class Marker implements Serializable{
    private static ArrayList<Marker> list = new ArrayList<Marker>();
    private double lognitude;
    private double lagnitude;
    private String description;
    private String name;

    public static ArrayList<Marker> getList() {
        return list;
    }

    public static void setList(ArrayList<Marker> list) {
        Marker.list = list;
    }

    public Marker(double lognitude, double lagnitude, String description, String name) {
        this.lognitude = lognitude;
        this.lagnitude = lagnitude;
        this.description = description;
        this.name = name;
        list.add(this);
    }


    public double getLognitude() {
        return lognitude;
    }

    public void setLognitude(double lognitude) {
        this.lognitude = lognitude;
    }

    public double getLagnitude() {
        return lagnitude;
    }

    public void setLagnitude(double lagnitude) {
        this.lagnitude = lagnitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void writeMarkers(){
        try
        {
            File file = new File(Environment.getExternalStorageDirectory()  + File.separator + "markers.ser");
            if(!file.exists()) {
                file.createNewFile();
                Log.e("msg","file created");
            }
            FileOutputStream fileOut =
                    new FileOutputStream(file, false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for(Marker m: list)
                out.writeObject(m);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in markers.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public static void readMarkers(){
        try
        {
            list = new ArrayList<Marker>();
            FileInputStream fileIn = new FileInputStream(Environment.getExternalStorageDirectory() + File.separator + "markers.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Marker m = (Marker)in.readObject();
            while(m != null) {
                list.add(m);
                m = (Marker) in.readObject();
            }
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Markers class not found");
            c.printStackTrace();
            return;
        }
    }

    public static void clearMarkers(){
        list = new ArrayList<Marker>();
    }
}
