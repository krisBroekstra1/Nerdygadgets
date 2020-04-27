package nerdygadgets.backoffice.main.Route;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route extends JPanel implements ActionListener {
    double lon;
    double lat;
    String Adres;
    JTextField JTadres;
    JButton JBadres;
    private ArrayList<String> plaatsArray;
    private ArrayList<Double> latArray;
    private ArrayList<Double> longArray;
    private JButton JBgenerate;
    JButton JBnewAddress;
    JLabel JLcoords;
    JLabel JLadres;
    JLabel JLshowAdres;
    JComboBox<String> JCadressen;
    JButton JBaddAdressToList;
    private JButton JBgetAdressen;

    HashMap<String, Coördinates> adressen;
    JButton test;

    public Route() throws IOException {
        setLayout(new FlowLayout());
        setSize(300, 300);
        setVisible(true);
        JTadres = new JTextField(10);
        add(JTadres);
        JBgenerate = new JButton("add");
        JBgenerate.addActionListener(this);
        add(JBgenerate);
        adressen = new HashMap<>();
        adressen.put("Start", generate("Zwolle"));
        test = new JButton("test");
        test.addActionListener(this);
        add(test);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBgenerate) {
            Adres = JTadres.getText();
            if (!checkSpecialCharacters(Adres)) {
                try {
                    adressen.put(Adres, generate(Adres));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println(Adres);
            }
        }
        if (e.getSource() == test) {
            System.out.println(getPath(adressen));
            System.out.println(adressen);
        }
    }

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
            System.out.println(coordsStringone);
            if (coordsStringone.length() > 11) {
                String coords = coordsStringone.substring(11, 60);
                System.out.println(coords);
                String[] coordsSplit = coords.split(",");
                String[] latSplit = coordsSplit[0].split(":");
                String[] longSplit = coordsSplit[1].split(":");
                lon = Double.parseDouble(longSplit[1]);
                lat = Double.parseDouble(latSplit[1]);
                System.out.println(latSplit[1]);
                System.out.println(longSplit[1]);
                //print coords on dialog
                JLcoords = new JLabel("Latitude: " + lat + " Longitude: " + lon);
                add(JLcoords);
                //add reset button
                JOptionPane.showMessageDialog(this, Adres + " is toegevoegd!", "Succes!", JOptionPane.INFORMATION_MESSAGE);
                return new Coördinates(lon, lat);
            }
            if (coordsStringone.length() == 11) {
                JOptionPane.showMessageDialog(this, "Levert geen resultaat op, probeer opnieuw!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            revalidate();
            repaint();
            resetGPSJpanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean checkSpecialCharacters(String string) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    public void resetGPSJpanel() {
        removeAll();
        add(JBgetAdressen);
        add(JTadres);
        add(JBadres);
        JTadres.setText("");
        revalidate();
        repaint();
    }

    public double calculateDistance(Coördinates c1, Coördinates c2) {
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

    public String getShortestNode(HashMap<String, Coördinates> hm,String start) {
        String startNode = start;
        String shortestNode = "Start";
        Double shortestDistance = Double.MAX_VALUE;
        for (Map.Entry<String, Coördinates> all : hm.entrySet()) {
            System.out.println(all.getKey());
            if(!(all.getKey().equals(startNode))){
                double dis = calculateDistance(hm.get(startNode), all.getValue());
                if (dis < shortestDistance) {
                    shortestDistance = dis;
                    shortestNode = all.getKey();
                }
            }
        }
        System.out.println(shortestDistance);
        return shortestNode;
    }
    public String getPath(HashMap<String, Coördinates> hm){
        String path = "Start";
        String previous = "Start";
        String currentPath = "Start";
        int size = hm.size()-1;
        for (int i = 0; i < size; i++) {
            currentPath = getShortestNode(hm,previous);
            path = path+currentPath;
            hm.remove(previous);
            previous = currentPath;
        }
        return path;
    }

    public ArrayList<String> getPlaatsArray() {
        return plaatsArray;
    }

    public ArrayList<Double> getLatArray() {
        return latArray;
    }

    public ArrayList<Double> getLongArray() {
        return longArray;
    }
}
