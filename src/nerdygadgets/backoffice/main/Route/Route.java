package nerdygadgets.backoffice.main.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    HashMap<String, Coördinates> buffer;

    public Route() throws IOException {
        setLayout(new FlowLayout());
        setSize(300, 300);
        setVisible(true);
        plaatsArray = new ArrayList<>();
        latArray = new ArrayList<>();
        longArray = new ArrayList<>();

        JBgetAdressen = new JButton("Verkrijg adressen");
        JBgetAdressen.addActionListener(this);
        add(JBgetAdressen);

        JTadres = new JTextField(10);
        add(JTadres);

        JBadres = new JButton("Send adres");
        JBadres.addActionListener(this);
        add(JBadres);
        buffer = new HashMap<>();
        adressen = new HashMap<>();
        adressen.put("Zwolle", generate("Zwolle"));
        buffer.put("Zwolle", generate("Zwolle"));

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBadres) {
            Adres = JTadres.getText();
            if (Adres.length() > 2) {
                if (Adres.isEmpty() || checkSpecialCharacters(Adres)) {
                    JOptionPane.showMessageDialog(getParent(), "Het invoerveld is leeg of bevat niet toegestane characters!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JLadres = new JLabel("Is dit uw adres:  " + Adres + "?");
                JBgenerate = new JButton("Ja!");
                JBgenerate.addActionListener(this);
                add(JLadres);
                add(JBgenerate);
                remove(JBadres);
                JBnewAddress = new JButton("Nieuw Adres");
                JBnewAddress.addActionListener(this);
                add(JBnewAddress);
                revalidate();
                repaint();
            }
            if (Adres.length() <= 2) {
                JOptionPane.showMessageDialog(this, "Je moet meer dan twee characters invoeren!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == JBgenerate) {
            Coördinates coords;
            try {
                coords = generate(Adres);
                if (coords != null) {
                    adressen.put(Adres, coords);
                    buffer.put(Adres, coords);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == JBnewAddress) {
            resetGPSJpanel();
        }


        if (e.getSource() == JBgetAdressen) {
            removeAll();
            getAdressen();
            JButton goBack = new JButton("Terug");
            add(goBack);
            goBack.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    resetGPSJpanel();
                }
            });
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
                JOptionPane.showMessageDialog(this, "Levert geen resultaat op, probeer opnieuw!", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
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
        if(c1 == null|| c2 == null){
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

    public String getShortestNode(HashMap<String, Coördinates> hm, String start) {
        String startNode = start;
        String shortestNode = "Zwolle";
        Double shortestDistance = Double.MAX_VALUE;
        for (Map.Entry<String, Coördinates> all : hm.entrySet()) {
            if (!(all.getKey().equals(startNode))) {
                double dis = calculateDistance(hm.get(startNode), all.getValue());
                if (dis < shortestDistance) {
                    shortestDistance = dis;
                    shortestNode = all.getKey();
                }
            }
        }
        return shortestNode;
    }

    public String getPath(HashMap<String, Coördinates> hm) throws IOException {
        String path = "Zwolle";
        String previous = "Zwolle";
        String currentPath = "Zwolle";
        int size = hm.size() - 1;
        for (int i = 0; i < size; i++) {
            System.out.println(hm.get("Zwolle"));
            currentPath = getShortestNode(hm, previous);
            path += "->";
            path = path + currentPath;
            hm.remove(previous);
            previous = currentPath;
        }
        System.out.println(path);
        return path;
    }

    public void getAdressen() {
        try {
            if (adressen.isEmpty()) {
                JLshowAdres = new JLabel("Er zijn geen gegevens!");
                add(JLshowAdres);
                revalidate();
                repaint();
                return;
            } else {
                String route = getPath(adressen);
                JLabel JLroute = new JLabel("De kortste route is " + route);
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Plaats");
                model.addColumn("Longtitude");
                model.addColumn("Latitude");
                model.addRow(new Object[]{"Plaats", "Lontitude", "Latitude"});
                JTable jtable = new JTable(model);

                for (Map.Entry<String, Coördinates> all : buffer.entrySet()) {
                    model.addRow(new Object[]{all.getKey(), all.getValue().getLongtitude(), all.getValue().getLatitude()});
                    adressen.put(all.getKey(), all.getValue());
                }
                add(jtable);
                add(JLroute);
                revalidate();
                repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
