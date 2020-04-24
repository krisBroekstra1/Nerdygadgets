package nerdygadgets.backoffice.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Frame extends JFrame{
    JButton JBopenGPSPanel;
    private Gpscoördinate Gpscoördinate;

    private JButton JBgetAdressen;
    private JButton _btnRetour;

    public Frame(int width, int height) {
        setSize(width, height);

        setTitle("NerdyGadgets backoffice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);

    }


}
