package nerdygadgets.backoffice.main.Route;

import nerdygadgets.backoffice.main.data.CustomerAddress;
import nerdygadgets.backoffice.main.data.GenerateRouteCities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NeigerstNeighbour extends JPanel implements ActionListener {
    private GenerateRouteCities rc = new GenerateRouteCities(new CustomerAddress("Heeten", "Stokvisweg 14"));
    private ArrayList<CustomerAddress> AlgoArray;

    public NeigerstNeighbour() {
        setLayout(new FlowLayout());
        setSize(300, 300);
        setVisible(true);
        AlgoArray = new ArrayList<>();
        JButton test = new JButton("test path");
        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rc.getOrderCities();
                AlgoArray = rc.getSelectedCities();
                System.out.println("Before algorithm:");
                for (CustomerAddress c:AlgoArray
                     ) {
                    System.out.println(c.getCity()+ " - "+  c.getAddress());
                }
                ArrayList<CustomerAddress> result = findPath2(AlgoArray);
                System.out.println("After algorithm:");
                for (CustomerAddress c:result
                     ) {
                    System.out.println(c.getCity()+ " - "+  c.getAddress());
                }
            }
        });
        add(test);
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
        CustomerAddress shortestNode = new CustomerAddress(null,null);
        Double shortestDistance = Double.MAX_VALUE;
        for (CustomerAddress c : ar) {
            if (!(c.getCoördinaten().equals(start.getCoördinaten()))) {
                double dis = calculateDistance(c.getCoördinaten(),start.getCoördinaten());
                if(dis < shortestDistance){
                    shortestDistance = dis;
                    shortestNode = c;
                }
            }
        }
        return shortestNode;
    }
    public ArrayList<CustomerAddress> findPath2(ArrayList<CustomerAddress> arl){
        ArrayList<CustomerAddress> returnvalue = new ArrayList<>();
        CustomerAddress start = new CustomerAddress("Zwolle", "Windesheim");
        start.setCoördinaten(new Coördinates(52.5167747,6.0830219));
        returnvalue.add(start);

        ArrayList<CustomerAddress> buffer = arl;
        CustomerAddress previous = start;

        CustomerAddress currentPath;
        int size = buffer.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            currentPath = getShortestNode2(buffer,previous);
            returnvalue.add(currentPath);
            buffer.remove(previous);
            previous = currentPath;
        }
        return returnvalue;
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
