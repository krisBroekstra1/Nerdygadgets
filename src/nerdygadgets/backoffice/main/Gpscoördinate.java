package nerdygadgets.backoffice.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gpscoördinate extends JDialog implements ActionListener {
    double lon;
    double lat;
    String Adres;

    JTextField JTadres;
    JButton JBadres;

    JButton JBgenerate;

    public Gpscoördinate() {
        setLayout(new FlowLayout());
        setTitle("Adres naar GPS coördinaten");
        setSize(300, 300);
        setVisible(true);
        JTadres = new JTextField(10);
        add(JTadres);
        JBadres = new JButton("Send adres");
        add(JBadres);
        JBadres.addActionListener(this);
        JBgenerate = new JButton("Genereer long en lat");
        add(JBgenerate);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBadres) {
            Adres = JTadres.getText();
        }
    }

    public void generateLongLat(){

    }
}
