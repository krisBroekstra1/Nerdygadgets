package nerdygadgets.backoffice.main;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gpscoördinate extends JPanel implements ActionListener {
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


    private JButton JBgetAdressen;

    public Gpscoördinate() {
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


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBadres) {
            Adres = JTadres.getText();
            if (Adres.isEmpty() || checkSpecialCharacters(Adres)) {
                JOptionPane.showMessageDialog(getParent(), "Het invoerveld is leeg of bevat niet toegestane characters!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            System.out.println(Adres);
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
        if (e.getSource() == JBgenerate) {
            try {
                generate(Adres);
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
            goBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetGPSJpanel();
                }
            });
            add(goBack);
        }
    }


    public void generate(String adres) throws IOException {
        String apiKey = "96e20cadb6a7778960dd6d2e55d01610";
        String query = adres;
        String surl = "http://api.positionstack.com/v1/forward?access_key=" + apiKey + "&query=" + query;
        URL url = new URL(surl);
        InputStream ip = url.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(ip));
            //convert String into coords
            String coords = rd.readLine().substring(11, 60);
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
            plaatsArray.add(Adres);
            latArray.add(lat);
            longArray.add(lon);
            revalidate();
            repaint();
            resetGPSJpanel();
            JOptionPane.showMessageDialog(this, Adres + " is toegevoegd!", "Succes!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getAdressen() {
        try {
            for (int i = 0; i < plaatsArray.size(); i++) {
                JLshowAdres = new JLabel("Adres: " + plaatsArray.get(i) + " lonitude: " + longArray.get(i) + " latitude: " + latArray.get(i));
                if (plaatsArray.size() == 0) {
                    JLshowAdres.setText("Er zijn geen gegevens!");
                }
                add(JLshowAdres);
                revalidate();
                repaint();
            }
        } catch (Exception e) {
            System.out.println("godver");
        }
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
