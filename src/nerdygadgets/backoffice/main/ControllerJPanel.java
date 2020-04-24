package nerdygadgets.backoffice.main;

import javax.swing.*;

public class ControllerJPanel extends JPanel {
    public ControllerJPanel() {
        JTabbedPane pane = new JTabbedPane();
        pane.addTab("voorraad", new JLabel("voorraad"));
        pane.addTab("klanten", new JLabel("klanten"));
        pane.addTab("retours", new retourPanel());
        pane.addTab("test", new JLabel("test"));
        pane.addTab("Gps Coordinate", new Gpscoördinate());
        add(pane);
    }
}
