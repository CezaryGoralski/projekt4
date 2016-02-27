package com.example.cezar.projekt4.Network;

import android.util.Base64;

import com.example.cezar.projekt4.Model.Paths;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Cezar on 2016-02-27.
 */
public class ListOfListSpiceRequest extends SpiceRequest<Paths> {
    private String param;
    public ListOfListSpiceRequest() {
        super(Paths.class);
    }

    @Override
    public Paths loadDataFromNetwork() throws Exception {
        URL url = null;
        try {
            url = new URL("http://52.34.41.87/trips");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        Paths kolejkiDto = null;
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            }, null);
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            HttpsURLConnection connect =  (HttpsURLConnection) url.openConnection();
            connect.setSSLSocketFactory(ctx.getSocketFactory());
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Authorization", "Basic " + Base64.encodeToString("goralce:89hzVcmK".getBytes(), Base64.NO_WRAP));
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("Accept", "application/json");

            int statusCode = connect.getResponseCode();
            System.out.println(statusCode);
            InputStream is = null;

            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                is = connect.getInputStream();

            }
            else {
                is = connect.getErrorStream();
            }
            InputStream ios = connect.getInputStream();

            String body = CharStreams.toString(new InputStreamReader(ios));



            //  System.out.println(body);
            kolejkiDto = gson.fromJson(body, Paths.class);
            System.out.println(kolejkiDto);
            ios.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (KeyManagementException e) {
            e.printStackTrace();
        }

        //   System.out.println(ordersDto[0]);
        // return ordersDto[0].getRequestOrder();
        return kolejkiDto;
    }
}
