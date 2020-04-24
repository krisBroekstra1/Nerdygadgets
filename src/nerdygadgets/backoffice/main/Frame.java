package nerdygadgets.backoffice.main;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame(int width, int height) {
        setSize(width, height);
        setLayout(new GridLayout());
        setTitle("NerdyGadgets Backoffice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
