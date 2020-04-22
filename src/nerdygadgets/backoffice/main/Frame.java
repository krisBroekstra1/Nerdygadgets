package nerdygadgets.backoffice.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    private JLabel _label;
    private JButton _btnRetour;

    public Frame (int width, int height){
        JButton btnRetour = new JButton("Retours");
        String[] columns = new String[] {"Name", "Age", "Nickname", "Employee"};

        //testdata
        Object[][] data = new Object[][] {
                {"Ingvar", 18, "baas", false },
                {"Kris", 18, "milf", false },
                {"Justin", 18, "Anniek", true },
        };

        JTable tabel = new JTable(data, columns);
        this.add(new JScrollPane(tabel));

        setLayout(new BorderLayout());
        setSize(width, height);
        setTitle("NerdyGadgets backoffice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        _btnRetour = new JButton("orders");
        _btnRetour.addActionListener(this);

        add(_btnRetour);

        setVisible(true);
        repaint();

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == _btnRetour) {
            retourPanel j = new retourPanel();
        }
    }
}
