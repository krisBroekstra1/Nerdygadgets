package nerdygadgets.backoffice.main.Route;

import com.sun.source.tree.WhileLoopTree;
import nerdygadgets.backoffice.main.JDBC.Driver;
import nerdygadgets.backoffice.main.data.CustomerAddress;
import nerdygadgets.backoffice.main.data.GenerateRouteCities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.*;

public class NeigerstNeighbour extends JPanel implements ActionListener {
    private GenerateRouteCities rc;
    private static double distance = 0;
    private JComboBox<String> citiess;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private DefaultTableModel model3;
    private String stringVoorProvincie;
    private JTable jtable;
    private JLabel adressenVoor;
    private JLabel aantalKilometers;
    private ArrayList<CustomerAddress> newroute = new ArrayList<>();
    private ArrayList<CustomerAddress> route;
    private ArrayList<CustomerAddress> routeBuffer;
    private String geselecteerdeRowStad;
    private String geselecteerdeRowAdres;
    private String geselecteerdeRowCustomer;
    private String province;

    public NeigerstNeighbour() throws SQLException {
        setLayout(new GridBagLayout());
        route = new ArrayList<>();
        routeBuffer = new ArrayList<>();
        JButton test = new JButton("Genereer route!");
        adressenVoor = new JLabel("Kies provincie");
        aantalKilometers = new JLabel("Totaal aantal Kilometers: " + 0);
        stringVoorProvincie = "Drenthe";
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Hier wordt de start van routecities (met provincie)
                CustomerAddress c1 = new CustomerAddress(stringVoorProvincie, null, stringVoorProvincie);
                rc = new GenerateRouteCities(c1);
                rc.getOrderCities();
                //de array van alle plaatsen die binnen de straal vallen
                route = rc.getSelectedCities();
                //Print alle waarden die voor het algoritme aan de arraylist zijn toegevoegd
                System.out.println("Before algorithm:");
                //resset van model dat in jpanel staat
                model.setRowCount(0);
                for (CustomerAddress c : route
                ) {
                    System.out.println(c.getCity() + " - " + c.getAddress());
                }
                //twoOpt uitvoeren op route geeft als object een arraylist terug.
                //aantal meegeven -> hoe hoger hoe vaker er wordt geprobeerd om een betere route te vinden
                CustomerAddress ca = new CustomerAddress("", "", "");
                ca.setPostalcode("");
                for (CustomerAddress c : route) {
                    if (!(ca.getPostalcode().equals(c.getPostalcode()))) {
                        ca = c;
                        routeBuffer.add(c);
                    }
                }
                ca.setPostalcode("");
                route = twoOpt(routeBuffer, 10000, true);
                routeBuffer.clear();
                //aanpassen jlabel voor jpanel
                aantalKilometers.setText("Totaal aantal Kilometers: " + getAfstand(route) + "km");
                //output in cosole voor bugfixing
                System.out.println("After algorithm:");
                System.out.println("Afstand" + getAfstand(route));
                System.out.println("Zwolle - Windesheim");
                //Toevoegen plaatsen aan model
                String name = "";
                System.out.println(route);
                for (CustomerAddress c : route
                ) {
                    if (!(name.equals(c.getName()))) {
                        name = c.getName();
                        model.addRow(new Object[]{name, c.getCity(), c.getAddress()});
                        System.out.println(c.getCity() + " - " + c.getAddress());
                    }
                }
                revalidate();
                repaint();
            }
        });

        //Opmaak van de Jpanel en toevoegen elementen
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        add(new JLabel("Kies provincie"), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(new JSeparator(), c);
        c.gridy = 2;
        maakJcombobox(c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(new JPanel(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(new JSeparator(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = GridBagConstraints.WEST;
        add(new JLabel("Route"), c);
        c.gridy = 5;
        add(test, c);
        model = new DefaultTableModel();
        model.addColumn("Customer");
        model.addColumn("Stad");
        model.addColumn("Adres");

        //model.addRow(new Object[]{"Customer", "Stad", "Adres"});
        jtable = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jtable.getTableHeader().setBackground(new Color(217, 43, 133));
        JTable jTable2 = new JTable(maakOrderTable(geselecteerdeRowStad, geselecteerdeRowAdres)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable2.getTableHeader().setBackground(new Color(217, 43, 133));
        JLabel ordertekst = new JLabel("De orders voor gekozen persoon:");
        jtable.getColumnModel().getColumn(2).setPreferredWidth(200);
        jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jtable.getSelectedRow() > -1) {
                    geselecteerdeRowCustomer = (jtable.getValueAt(jtable.getSelectedRow(), 0)).toString();
                    geselecteerdeRowStad = (jtable.getValueAt(jtable.getSelectedRow(), 1)).toString();
                    geselecteerdeRowAdres = (jtable.getValueAt(jtable.getSelectedRow(), 2)).toString();
                    System.out.println(geselecteerdeRowStad);
                    System.out.println(geselecteerdeRowAdres);
                    try {
                        jTable2.setModel(maakOrderTable(geselecteerdeRowStad, geselecteerdeRowAdres));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    ordertekst.setText("De orders voor: " + geselecteerdeRowCustomer);
                }
            }
        });

        c.gridy = 6;
        add(adressenVoor, c);
        c.gridy = 7;
        c.gridy = 8;
        add(aantalKilometers, c);
        c.gridy = 9;
        JScrollPane pane1 = new JScrollPane(jtable);
        pane1.setPreferredSize(new Dimension(250, 250));
        add(pane1, c);
        c.gridy = 10;
        add(ordertekst, c);
        c.gridy = 11;

        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jTable2.getSelectedRow() > -1) {
                    String orderId = jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString();
                    JTable jTable3 = null;
                    try {
                        jTable3 = new JTable(maakproductTable(orderId)) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };
                        jTable3.getTableHeader().setBackground(new Color(217, 43, 133));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    JScrollPane pane3 = new JScrollPane(jTable3);
                    pane3.setPreferredSize(new Dimension(300, 200));
                    JOptionPane.showMessageDialog(null, pane3, "OrderID: " + orderId, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        JScrollPane pane2 = new JScrollPane(jTable2);
        pane2.setPreferredSize(new Dimension(250, 250));
        add(pane2, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 12;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(new JSeparator(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 13;
        c.weighty = 1;
        add(new JPanel(), c);

        setSize(300, 500);
        setVisible(true);


    }

    public DefaultTableModel maakproductTable(String orderId) throws SQLException {
        model3 = new DefaultTableModel();
        model3.addColumn("Product");
        //model3.addRow(new Object[]{"Product"});

        ResultSet result = Driver.getProduct(orderId);
        while (result.next()) {
            model3.addRow(new Object[]{result.getString("StockItemName")});
        }
        return model3;
    }

    public DefaultTableModel maakOrderTable(String stad, String adres) throws SQLException {
        model2 = new DefaultTableModel();
        model2.addColumn("OrderID");
        model2.addColumn("Klant");
        model2.addColumn("Besteldatum");
        //model2.addRow(new Object[]{"OrderID", "Klant", "Besteldatum"});
        ResultSet result = Driver.getOrders(stad, adres);
        while (result.next()) {
            String klant = result.getString("CustomerName");
            String Orderid = result.getString("OrderID");
            String bestelDatum = result.getString("OrderDate");
            model2.addRow(new Object[]{Orderid, klant, bestelDatum});
        }
        return model2;
    }

    //Provincie toevoegen
    public void maakJcombobox(GridBagConstraints c) throws SQLException {
        ArrayList<String> cities = new ArrayList<>();
        ResultSet result = Driver.getOrderCities();
        while (result.next()) {

            String city = result.getString("City");
            String adres = result.getString("Adres");

            province = result.getString("province");
            cities.add(province);
        }
        citiess = new JComboBox(cities.toArray());
        citiess.addActionListener(this);
        add(citiess, c);
    }

    //de afstand bepalen tussen twee cooordinaten
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

    //Uit de arraylist de dichtsbijzijnste locatie kiezen
    public CustomerAddress getShortestNode2(ArrayList<CustomerAddress> ar, CustomerAddress start) {
        CustomerAddress shortestNode = new CustomerAddress(null, null, null);
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

    //De totale afstand van alle punten in een arraylist bepalen (met als beginpunt zwolle)
    public double getAfstand(ArrayList<CustomerAddress> ar) {
        int size = ar.size();
        double dis;
        double totaldis = 0;
        totaldis += calculateDistance(ar.get(0).getCoördinaten(), new Coördinates(6.0830219, 52.5167747));
        for (int i = 0; i < size - 1; i++) {
            dis = calculateDistance(ar.get(i).getCoördinaten(), ar.get(i + 1).getCoördinaten());
            totaldis += dis;
        }
        return totaldis;
    }

    // Nearest neighbour met als start de locatie zwolle
    public ArrayList<CustomerAddress> findPath2(ArrayList<CustomerAddress> arl) {
        distance = 0;
        ArrayList<CustomerAddress> returnvalue = new ArrayList<>();
        CustomerAddress start = new CustomerAddress("Zwolle", "Windesheim", "Overijssel");
        start.setCoördinaten(new Coördinates(6.0830219, 52.5167747));
        //returnvalue.add(start);

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

    //een Two opt optimalisatie van nearest neighbour
    public ArrayList<CustomerAddress> twoOpt(ArrayList<CustomerAddress> arl, int aantal, boolean twoOpt) {
        ArrayList<CustomerAddress> returnvalue = new ArrayList<>();
        //newroute leeghalen voor hergebruik van TwoOpt
        newroute.clear();
        if (twoOpt) {
            //eerst een arraylist van nearestNeighbour
            route = findPath2(arl);
            int size = route.size();
            //alle waarden van route toevoegen aan newroute
            for (int l = 0; l < size; l++) {
                newroute.add(l, route.get(l));
            }
            //als iteraties boven meegegeven aantal komt stopt de loop
            int improve = 0;
            int iteraties = 0;
            while ((iteraties < aantal) && improve < 300) {
                double distance0 = distance;
                for (int i = 1; i < size - 1; i++) {
                    for (int k = i + 1; k < size; k++) {
                        //het verplaatsen van waardes in de newroute arraylist
                        TwoOptSwap(i, k);
                        iteraties++;
                        distance0 = getAfstand(newroute);
                        //de afstand met nearesNeighbour zonder twoOpt
                        System.out.println(distance);
                        //De afstand met nearestNeighbour met twoOpt
                        System.out.println(distance0);
                        System.out.println("Verbeter poging:" + iteraties);
                        //Als er een betere afstand is gevonden wordt deze gebruikt.
                        if (distance0 < distance) {
                            System.out.println("Beter pad gevonden!");
                            improve = 0;
                            for (int j = 0; j < size; j++) {
                                route.set(j, newroute.get(j));
                            }
                            distance = distance0;
                        }
                    }
                }
                improve++;
            }
        }
        returnvalue = route;
        return returnvalue;
    }

    public void TwoOptSwap(int i, int k) {
        //Inteligent veranderen van volgorden in de arraylist om een te kijken voor een kortere route.
        int size = route.size();
        //take route[0] to route[i-1] and add them in order to new_route
        for (int c = 0; c <= i - 1; c++) {
            newroute.set(c, route.get(c));
        }
        //take route[i] to route[k] and add them in reverse order to new_route
        int dec = 0;
        for (int c = 0; c <= k; c++) {
            newroute.set(c, route.get(k - dec));
            dec++;
        }
        //take route[k+1] to end and add them in order to new_route
        for (int c = k + 1; c < size; c++) {
            newroute.set(c, route.get(c));
        }
    }


    //Het veranderen van input voor provincie
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == citiess) {

            JComboBox comboBox = (JComboBox) e.getSource();

            // Print the selected items and the action command.
            Object selected = comboBox.getSelectedItem();
            System.out.println("Selected Item  = " + selected);
            String command = e.getActionCommand();
            System.out.println("Action Commanddd = " + command);

            // Detect whether the action command is "comboBoxEdited"
            // or "comboBoxChanged"
            if ("comboBoxChanged".equals(command)) {
                System.out.println("User has typed a string in " +
                        "the combo box.");
                adressenVoor.setText(selected + ": ");
                stringVoorProvincie = selected.toString();
                revalidate();
                repaint();
            }
        }
    }

}
