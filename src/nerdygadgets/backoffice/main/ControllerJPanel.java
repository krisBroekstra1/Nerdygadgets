package nerdygadgets.backoffice.main;

import javax.swing.*;

public class ControllerJPanel extends JPanel {
    public ControllerJPanel() {
        JTabbedPane pane = new JTabbedPane();

        pane.addTab("voorraad", new JLabel("voorraad"));
        pane.addTab("klanten", new JLabel("klanten"));
        pane.addTab("retours", new orderPanel());
        pane.addTab("test", new JLabel("test"));
        pane.addTab("Gps", new Gpscoördinate());
        pane.addTab("Route", new Route());
        add(pane);
    }
}
