package nerdygadgets.backoffice.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    private JLabel _label;
    public Frame (int width, int height){
        setLayout(new BorderLayout());
        setSize(width, height);
        setTitle("NerdyGadgets backoffice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        _label = new JLabel("orders");
        add(_label);


        setVisible(true);
        repaint();

    }


    public void actionPerformed(ActionEvent e) {

    }
}
