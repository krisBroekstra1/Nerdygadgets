package nerdygadgets.backoffice.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    JButton JBopenGPSPanel;
    private JDialog Gpscoördinate;

    public Frame (int width, int height){
        setSize(width, height);

        setTitle("NerdyGadgets backoffice");
        setLayout(new FlowLayout());
        setTitle("NerdyGadgets Backoffice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JBopenGPSPanel = new JButton("Open gps panel");
        this.add(JBopenGPSPanel);
        JBopenGPSPanel.addActionListener(this);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Gpscoördinate = new Gpscoördinate();
        Gpscoördinate.revalidate();
        Gpscoördinate.repaint();
        revalidate();
        repaint();
    }
}
