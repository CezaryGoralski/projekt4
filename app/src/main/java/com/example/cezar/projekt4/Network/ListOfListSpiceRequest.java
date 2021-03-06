package com.example.cezar.projekt4.Network;

import com.example.cezar.projekt4.Model.NetworkModels.DataFromNetwork;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Cezar on 2016-02-27.
 */
public class ListOfListSpiceRequest extends SpiceRequest<DataFromNetwork> {
    private String param;

    public ListOfListSpiceRequest() {
        super(DataFromNetwork.class);
    }

    @Override
    public DataFromNetwork loadDataFromNetwork() throws Exception {
        URL url = null;
        try {
            url = new URL("http://52.34.41.87/trips");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        DataFromNetwork ordersDto = null;

        try {
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("Accept", "application/json");
            InputStream ios = connect.getInputStream();

            String body = CharStreams.toString(new InputStreamReader(ios, Charset.defaultCharset()));
            ordersDto = gson.fromJson(body, DataFromNetwork.class);
            ios.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //   System.out.println(ordersDto[0]);
        // return ordersDto[0].getRequestOrder();
        return ordersDto;
    }
}
