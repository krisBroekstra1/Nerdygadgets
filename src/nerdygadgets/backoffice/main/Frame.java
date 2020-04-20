package nerdygadgets.backoffice.main;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame (int width, int height){
        setSize(width, height);
        setTitle("NerdyGadgets backoffice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        repaint();
        
    }

}
