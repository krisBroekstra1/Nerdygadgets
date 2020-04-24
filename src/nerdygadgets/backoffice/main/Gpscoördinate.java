package nerdygadgets.backoffice.main;

import com.mysql.cj.xdevapi.Table;

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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
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

        JBadres = new JButton("Verstuur adres");
        JBadres.addActionListener(this);
        add(JBadres);


    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBadres) {
            Adres = JTadres.getText();
            if (Adres.length() > 2) {
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
            if (Adres.length() <= 2) {
                JOptionPane.showMessageDialog(this, "Je moet meer dan twee characters invoeren!", "Error", JOptionPane.ERROR_MESSAGE);
            }
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
            System.out.println("remove all werkt!");
            getAdressen();
            System.out.println("getAdressen werkt!");
            JButton goBack = new JButton("Terug");
            add(goBack);
            goBack.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    resetGPSJpanel();
                }
            });
        }
    }


    public void generate(String adres) throws IOException {
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
                plaatsArray.add(Adres);
                latArray.add(lat);
                longArray.add(lon);
                JOptionPane.showMessageDialog(this, Adres + " is toegevoegd!", "Succes!", JOptionPane.INFORMATION_MESSAGE);
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

    }

    public void getAdressen() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Plaats");
            model.addColumn("Longtitude");
            model.addColumn("Latitude");
            model.addRow(new Object[]{"Plaats","Lontitude","Latitude"});
            JTable jtable = new JTable(model);
            if (plaatsArray.isEmpty()) {
                JLshowAdres = new JLabel("Er zijn geen gegevens!");
                add(JLshowAdres);
                revalidate();
                repaint();
                return;
            } else {
                for (int i = 0; i < plaatsArray.size(); i++) {
                    model.addRow(new Object[]{plaatsArray.get(i), String.valueOf(longArray.get(i)), String.valueOf(latArray.get(i))});
                    //JLshowAdres = new JLabel("Index: " + i + " Adres: " + plaatsArray.get(i) + " longitude: " + longArray.get(i) + " latitude: " + latArray.get(i));
                    //add(JLshowAdres);
                }
                add(jtable);
                revalidate();
                repaint();
            }
        } catch (Exception e) {
            System.out.println("godver");
        }
        try {
            double afstand = calculateDistance(latArray.get(0), longArray.get(0), latArray.get(1), longArray.get(1));
            add(new JLabel("De afstand in km tussen plaats: "+ plaatsArray.get(0)+" en "+plaatsArray.get(1)+ " is: " + afstand + " km"));
            System.out.println("De afstand in km tussen plaats index 0 en index 1 is: " + afstand + " km");
            revalidate();
            repaint();
        } catch (Exception e) {
            System.out.println("rip");
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

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        //Berekent de afstand tussen twee punten in kilometer;
        double variable1 = lon1 - lon2;
        double afstand = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(variable1));
        afstand = Math.acos(afstand);
        afstand = Math.toDegrees(afstand);
        afstand = afstand * 60 * 1.1515 * 1.609344;
        return afstand;
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

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
