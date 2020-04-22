package nerdygadgets.backoffice.main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class retourPanel extends JPanel implements ActionListener {

    JFrame frame;
    JTable _table;
    JButton _btnBewerken;
    JLabel label;

    public retourPanel(){
        // Data to be displayed in the JTable
        String[][] data = {
                { "Kundan Kumar Jha", "4031", "CSE" },
                { "Anand Jha", "6014", "IT" }
        };

        String[] columnNames = { "Name", "Roll Number", "Boolean" };

        frame = new JFrame();
        label = new JLabel("Retours");
        _table = new JTable(data, columnNames);

        _btnBewerken = new JButton("Bewerken");
        JScrollPane sp = new JScrollPane(_table);

        frame.setTitle("JTable Example");
        frame.setMinimumSize(new Dimension(500, 600));
        frame.setLayout(new FlowLayout());


        _table.setBounds(30, 40, 200, 200);
        _table.getTableHeader().setBackground(new Color(217,43,133));
        _table.setEnabled(false);
//        table.setBackground(new Color(255,255,255));

        label.setFont(new Font("Serif", Font.PLAIN, 24));

        _btnBewerken.addActionListener(this);

        frame.add(label);
        frame.add(sp);
        frame.add(_btnBewerken);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _table.setEnabled(true);
    }
}
