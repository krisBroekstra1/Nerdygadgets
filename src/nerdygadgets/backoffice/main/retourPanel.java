package nerdygadgets.backoffice.main;

import nerdygadgets.backoffice.main.JDBC.Driver;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class retourPanel extends JPanel implements ActionListener {

    JFrame frame;
    JTable _table;
    JButton _btnBewerken;
    JLabel label;

    public retourPanel(){
        ResultSet rs = Driver.orders();

        frame = new JFrame();
        label = new JLabel("Retours");
        try {
            _table = new JTable(buildTableModel(rs));
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        _btnBewerken = new JButton("Bewerken");
        JScrollPane sp = new JScrollPane(_table);

        frame.setTitle("Retour");
        frame.setMinimumSize(new Dimension(500, 600));
        frame.setLayout(new FlowLayout());


        _table.setBounds(30, 40, 200, 200);
        _table.getTableHeader().setBackground(new Color(217, 43, 133));
        _table.setEnabled(false);
//        table.setBackground(new Color(255,255,255));

        label.setFont(new Font("Serif", Font.PLAIN, 24));

        _btnBewerken.addActionListener(this);

        frame.add(label);
        frame.add(sp);
        frame.add(_btnBewerken);

        frame.setVisible(true);
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _btnBewerken) {
            _table.setEnabled(true);
        }
    }
}
