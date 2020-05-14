package nerdygadgets.backoffice.main;

import nerdygadgets.backoffice.main.Route.NeigerstNeighbour;
import nerdygadgets.backoffice.main.Route.Route;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerJPanel extends JPanel {
    public ControllerJPanel() throws IOException, SQLException {
        JTabbedPane pane = new JTabbedPane();

        pane.addTab("Voorraad", new StockPanel());
        pane.addTab("Klanten", new CustomerPanel());
        pane.addTab("Orders", new orderPanel());
        pane.addTab("Gps", new Gpscoördinate());
        pane.addTab("Algoritme_V1", new Route());
        pane.addTab("NeigerstNeighbour", new NeigerstNeighbour());
        add(pane);
    }
}
