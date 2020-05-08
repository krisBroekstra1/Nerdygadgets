package nerdygadgets.backoffice.main.Route;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GPSCoördinaten {
    private double lon,lat;

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
}
