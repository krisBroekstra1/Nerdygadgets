package nerdygadgets.backoffice.main;

import nerdygadgets.backoffice.main.Route.Route;

import javax.swing.*;
import java.io.IOException;

public class ControllerJPanel extends JPanel {
    public ControllerJPanel() throws IOException {
        JTabbedPane pane = new JTabbedPane();

        pane.addTab("Voorraad", new StockPanel());
        pane.addTab("Klanten", new CustomerPanel());
        pane.addTab("Orders", new orderPanel());
        pane.addTab("Gps", new Gpscoördinate());
        add(pane);
    }
}
