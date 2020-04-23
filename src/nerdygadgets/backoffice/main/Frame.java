package nerdygadgets.backoffice.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    JButton JBopenGPSPanel;
    private Gpscoördinate Gpscoördinate;


    public Frame(int width, int height) {
        setSize(width, height);
        setTitle("NerdyGadgets Backoffice");
        setLayout(new GridLayout());

        setTitle("NerdyGadgets Backoffice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JBopenGPSPanel = new JButton("Open gps panel");
        this.add(new LoginContent(this));
        JBopenGPSPanel.addActionListener(this);
        add(JBopenGPSPanel);
        revalidate();
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBopenGPSPanel) {
            Gpscoördinate = new Gpscoördinate();
            add(Gpscoördinate);
            Gpscoördinate.revalidate();
            Gpscoördinate.repaint();
            revalidate();
            repaint();
        }
    }
}
