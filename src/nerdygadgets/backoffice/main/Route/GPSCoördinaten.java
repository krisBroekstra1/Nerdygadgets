package nerdygadgets.backoffice.main.Route;

import com.mysql.cj.xdevapi.JsonParser;
import com.mysql.cj.xdevapi.JsonString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


public class GPSCoördinaten {
    private double lon, lat;
    private JSONParser parser = new JSONParser();

    public Coördinates generate(String adres) throws IOException {
        String apiKey = "96e20cadb6a7778960dd6d2e55d01610";
        String query = adres;
        query = query.replaceAll(" ", "%20");
        String surl = "http://api.positionstack.com/v1/forward?access_key=" + apiKey + "&query=" + query;
        URL url = new URL(surl);
        InputStream ip = url.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(ip));
            //convert String into coords
            String coordsStringone = rd.readLine();
            if (coordsStringone.length() > 11) {
                System.out.println(coordsStringone);

                String coords = coordsStringone.substring(11, 60);
                String[] coordsSplit = coords.split(",");
                String[] latSplit = coordsSplit[0].split(":");
                String[] longSplit = coordsSplit[1].split(":");
                lon = Double.parseDouble(longSplit[1]);
                lat = Double.parseDouble(latSplit[1]);
                return new Coördinates(lon, lat);
            }
            if (coordsStringone.length() == 11) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public double calculateDistance(Coördinates c1, Coördinates c2) {
        if (c1 == null || c2 == null) {
            System.out.println("Coordinaten zijn null, alles kapot!");
            return -1;
        }
        //Berekent de afstand tussen twee punten in kilometer;
        if (c1.equals(c2)) {
            return 0;
        }
        double variable1 = c1.getLongtitude() - c2.getLongtitude();
        double afstand = Math.sin(Math.toRadians(c1.getLatitude())) * Math.sin(Math.toRadians(c2.getLatitude())) + Math.cos(Math.toRadians(c1.getLatitude())) * Math.cos(Math.toRadians(c2.getLatitude())) * Math.cos(Math.toRadians(variable1));
        afstand = Math.acos(afstand);
        afstand = Math.toDegrees(afstand);
        afstand = afstand * 60 * 1.1515 * 1.609344;
        return afstand;
    }
}
