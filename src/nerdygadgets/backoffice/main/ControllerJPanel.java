package nerdygadgets.backoffice.main;

import javax.swing.*;

public class ControllerJPanel extends JPanel {
    public ControllerJPanel(){
        JTabbedPane pane = new JTabbedPane();
        pane.addTab("Voorraad", new JLabel("voorraad"));
        pane.addTab("Klanten", new JLabel("klanten"));
        pane.addTab("Orders", new orderPanel());
        pane.addTab("Test", new JLabel("test"));

        add(pane);
    }
}
