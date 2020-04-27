package nerdygadgets.backoffice.main;

import nerdygadgets.backoffice.main.Route.Route;

import javax.swing.*;
import java.io.IOException;

public class ControllerJPanel extends JPanel {
    public ControllerJPanel() throws IOException {
        JTabbedPane pane = new JTabbedPane();

        pane.addTab("voorraad", new JLabel("voorraad"));
        pane.addTab("klanten", new JLabel("klanten"));
        pane.addTab("retours", new orderPanel());
        pane.addTab("Gps", new Gpscoördinate());
        pane.addTab("Route", new Route());
        add(pane);
    }
}
