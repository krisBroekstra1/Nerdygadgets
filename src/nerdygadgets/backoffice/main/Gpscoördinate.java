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

    JButton JBaddToArraylist;

    public Gpscoördinate() {
        setLayout(new FlowLayout());
        setSize(300, 300);
        setVisible(true);
        plaatsArray = new ArrayList<>();
        latArray = new ArrayList<>();
        longArray = new ArrayList<>();
        JTadres = new JTextField(10);
        add(JTadres);
        JBadres = new JButton("Send adres");
        JBadres.addActionListener(this);
        add(JBadres);
        JBgenerate = new JButton("Genereer long en lat");
        JBgenerate.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBadres) {
            Adres = JTadres.getText();
            JLadres = new JLabel("Is dit uw adres:  " + Adres + "?");
            add(JLadres);
            add(JBgenerate);
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
            remove(JLadres);
            remove(JLcoords);
            remove(JBgenerate);
            remove(JBnewAddress);
            remove(JBaddToArraylist);
            JTadres.setText("");
            revalidate();
            repaint();
        }
        if(e.getSource() == JBaddToArraylist) {
            plaatsArray.add(JTadres.getText());
            latArray.add(lat);
            longArray.add(lon);
            JBaddToArraylist.setText("Is al toegevoegd!");
            JOptionPane.showMessageDialog(this, "Toegevoegd!", "Succes!", JOptionPane.INFORMATION_MESSAGE);
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
            JBnewAddress = new JButton("Nieuw Adres");
            JBaddToArraylist = new JButton("Voeg toe");
            JBnewAddress.addActionListener(this);
            JBaddToArraylist.addActionListener(this);
            add(JBaddToArraylist);
            add(JBnewAddress);
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
