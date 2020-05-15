package nerdygadgets.backoffice.main.Route;

import nerdygadgets.backoffice.main.JDBC.Driver;
import nerdygadgets.backoffice.main.data.CustomerAddress;
import nerdygadgets.backoffice.main.data.GenerateRouteCities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NeigerstNeighbour extends JPanel implements ActionListener {
    private GenerateRouteCities rc;
    private ArrayList<CustomerAddress> AlgoArray;
    private static double distance = 0;
    private JComboBox<String> citiess;
    private DefaultTableModel model;
    private JComboBox straal;
    private JLabel straalLabel;
    private String stringVoorGenerateRouteCities;
    private double straalVoorGenerateRouteCities;
    private HashMap<String, String> hm = new HashMap<>();
    JTable jtable;
    JLabel adressenVoor;

    public NeigerstNeighbour() throws SQLException {
        setLayout(new FlowLayout());
        setSize(300, 300);
        setVisible(true);
        AlgoArray = new ArrayList<>();
        JButton test = new JButton("Genereer route!");
        makeJcombobox();
        adressenVoor = new JLabel("Kies voorkeurslocatie");
        straalLabel = new JLabel("kies gewenste straal");
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerAddress c1 = new CustomerAddress(stringVoorGenerateRouteCities,hm.get(stringVoorGenerateRouteCities));
                rc = new GenerateRouteCities(c1);
                rc.setStraal(straalVoorGenerateRouteCities);
                rc.getOrderCities();
                AlgoArray = rc.getSelectedCities();
                System.out.println("Before algorithm:");
                model.setRowCount(1);
                for (CustomerAddress c : AlgoArray
                ) {
                    System.out.println(c.getCity() + " - " + c.getAddress());
                }
                ArrayList<CustomerAddress> result = findPath2(AlgoArray);
                System.out.println("After algorithm:");
                System.out.println("Straal: " + straalVoorGenerateRouteCities);
                System.out.println("Distance: " + distance + " km");
                for (CustomerAddress c : result
                ) {
                    System.out.println(c.getCity() + " - " + c.getAddress());
                    model.addRow(new Object[] {"OrderID", c.getCity(),c.getAddress()});
                }
                distance = 0;
            }
        });
        add(test);
        model = new DefaultTableModel();
        model.addColumn("OrderID");
        model.addColumn("Stad");
        model.addColumn("Adres");
        model.addRow(new Object[]{"OrderID","Stad", "Adres"});
        jtable = new JTable(model);
        add(adressenVoor);
        add(straalLabel);
        add(jtable);
    }

    public void makeJcombobox() throws SQLException {
        ArrayList<String> cities = new ArrayList<>();
        ResultSet result = Driver.getOrderCities();
        while (result.next()) {
            String city = result.getString("City");
            String adres = result.getString("Adres");
            hm.put(city,adres);
            cities.add(city);
        }
        citiess = new JComboBox(cities.toArray());
        add(new JLabel("Kies voorkeurslocatie"));
        citiess.addActionListener(this);
        add(citiess);
        add(new JLabel("Kies gebied (km)"));
        straal = new JComboBox<>(new String[]{"25", "50", "100"});
        straal.addActionListener(this);
        add(straal);
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

    public CustomerAddress getShortestNode2(ArrayList<CustomerAddress> ar, CustomerAddress start) {
        CustomerAddress shortestNode = new CustomerAddress(null, null);
        Double shortestDistance = Double.MAX_VALUE;
        for (CustomerAddress c : ar) {
            if (!(c.getCoördinaten().equals(start.getCoördinaten()))) {
                double dis = calculateDistance(c.getCoördinaten(), start.getCoördinaten());
                if (dis < shortestDistance) {
                    shortestDistance = dis;
                    shortestNode = c;
                }
            }
        }

        distance += shortestDistance;
        return shortestNode;
    }

    public ArrayList<CustomerAddress> findPath2(ArrayList<CustomerAddress> arl) {
        ArrayList<CustomerAddress> returnvalue = new ArrayList<>();
        CustomerAddress start = new CustomerAddress("Zwolle", "Windesheim");
        start.setCoördinaten(new Coördinates(6.0830219, 52.5167747));
        returnvalue.add(start);

        ArrayList<CustomerAddress> buffer = arl;
        CustomerAddress previous = start;

        CustomerAddress currentPath;
        int size = buffer.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            currentPath = getShortestNode2(buffer, previous);
            returnvalue.add(currentPath);
            buffer.remove(previous);
            previous = currentPath;
        }
        return returnvalue;
    }
    public void updateModel(){
        if((!(straalLabel.getText().equals("kies gewenste straal")))&&!(adressenVoor.getText().equals("Kies voorkeurslocatie"))){

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == citiess) {

            JComboBox comboBox = (JComboBox) e.getSource();

            // Print the selected items and the action command.
            Object selected = comboBox.getSelectedItem();
            System.out.println("Selected Item  = " + selected);
            String command = e.getActionCommand();
            System.out.println("Action Command = " + command);

            // Detect whether the action command is "comboBoxEdited"
            // or "comboBoxChanged"
            if ("comboBoxChanged".equals(command)) {
                System.out.println("User has typed a string in " +
                        "the combo box.");
                adressenVoor.setText(selected + ": ");
                stringVoorGenerateRouteCities = selected.toString();
                revalidate();
                repaint();
            }
        }
        if (e.getSource() == straal) {
            JComboBox comboBox1 = (JComboBox) e.getSource();

            // Print the selected items and the action command.
            Object selected = comboBox1.getSelectedItem();
            System.out.println("Selected Item  = " + selected);
            String command = e.getActionCommand();
            System.out.println("Action Command = " + command);

            // Detect whether the action command is "comboBoxEdited"
            // or "comboBoxChanged"
            if ("comboBoxChanged".equals(command)) {
                System.out.println("User has typed a string in " +
                        "the combo box.");
                straalVoorGenerateRouteCities = Double.parseDouble(selected.toString());
                straalLabel.setText("(" + selected + ") km");
                revalidate();
                repaint();
            }
        }
    }

}
