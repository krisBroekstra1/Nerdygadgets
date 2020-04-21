package nerdygadgets.backoffice.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;




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
        JBadres.addActionListener(this);
        add(JBadres);
        JBgenerate = new JButton("Genereer long en lat");
        JBgenerate.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == JBadres) {
            Adres = JTadres.getText();
            JLabel JLadres = new JLabel("Is dit uw adres:  " + Adres + "?");
            add(JLadres);
            add(JBgenerate);
            revalidate();
            repaint();
        }
        if(e.getSource() == JBgenerate){
            try {
                generate(Adres);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void generate(String adres) throws IOException {
        String apiKey = "96e20cadb6a7778960dd6d2e55d01610";
        String query = adres;
        String surl = "http://api.positionstack.com/v1/forward?access_key="+apiKey+"&query="+query;
        URL url = new URL(surl);
        InputStream ip = url.openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(ip));
            String coords = rd.readLine().substring(11,50);
            System.out.println(coords);
            JLabel JLcoords = new JLabel(coords);
            add(JLcoords);
            revalidate();
            repaint();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
