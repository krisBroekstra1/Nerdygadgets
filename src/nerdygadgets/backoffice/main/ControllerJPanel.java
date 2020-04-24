package nerdygadgets.backoffice.main;

import javax.swing.*;

public class ControllerJPanel extends JPanel {
    public ControllerJPanel() {
        JTabbedPane pane = new JTabbedPane();

        pane.addTab("Voorraad", new StockPanel());
        pane.addTab("Klanten", new JLabel("Klanten"));
        pane.addTab("Orders", new orderPanel());
        pane.addTab("Gps", new Gpscoördinate());
        add(pane);
    }
}
