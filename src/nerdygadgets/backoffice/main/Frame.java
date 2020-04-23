package nerdygadgets.backoffice.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    JButton JBopenGPSPanel;
    private Gpscoördinate Gpscoördinate;

    private JButton JBgetAdressen;

    public Frame(int width, int height) {
        setSize(width, height);

        setTitle("NerdyGadgets backoffice");
        setLayout(new FlowLayout());
        setTitle("NerdyGadgets Backoffice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        JBopenGPSPanel = new JButton("Open gps panel");
        this.add(new LoginContent(this));
        JBopenGPSPanel.addActionListener(this);
        add(JBopenGPSPanel);
        JBgetAdressen = new JButton("Verkrijg adressen");
        JBgetAdressen.addActionListener(this);
        add(JBgetAdressen);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBopenGPSPanel) {
            Gpscoördinate = new Gpscoördinate();
            add(Gpscoördinate);
            Gpscoördinate.revalidate();
            Gpscoördinate.repaint();
            revalidate();
            repaint();
        }
        if (e.getSource() == JBgetAdressen) {
            getAdressen();
        }
    }

    public void getAdressen() {
        try{
            for (int i = 0; i < Gpscoördinate.getPlaatsArray().size(); i++) {
                add(new JLabel(Gpscoördinate.getPlaatsArray().get(i)));
                add(new JLabel(""+Gpscoördinate.getLongArray().get(i)));
                add(new JLabel(""+Gpscoördinate.getLatArray().get(i)));
                System.out.println(Gpscoördinate.getPlaatsArray().get(i));
                System.out.println(Gpscoördinate.getLongArray().get(i));
                System.out.println(Gpscoördinate.getLatArray().get(i));
                revalidate();
                repaint();
            }
        } catch (Exception e){
            System.out.println("godver");
        }
    }
}
