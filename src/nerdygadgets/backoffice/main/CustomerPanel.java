package nerdygadgets.backoffice.main;

import nerdygadgets.backoffice.main.JDBC.Driver;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class CustomerPanel extends JPanel implements ActionListener {

    JTable _table;
    JButton _btnBewerken;
    JLabel label;


    public CustomerPanel(){
        ResultSet rs = Driver.getCustomers();
        setLayout(new MigLayout("wrap", "[center, grow]"));
        label = new JLabel("Klanten");
        try {
            _table = new JTable(buildTableModel(rs)){
                @Override
                public boolean isCellEditable(int row, int column) {
                    if (column == 0) {
                        return false;
                    }  else {
                        return true;
                    }
                }
            };
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        _btnBewerken = new JButton("Bewerken");
        JScrollPane sp = new JScrollPane(_table);


        _table.setBounds(30, 40, 400, 200);
        _table.getTableHeader().setBackground(new Color(217, 43, 133));
        _table.setEnabled(false);

//        table.setBackground(new Color(255,255,255));
        _table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = _table.getSelectedRow();
                String id = _table.getModel().getValueAt(row, 0).toString(); //id
                String cust = _table.getModel().getValueAt(row, 1).toString(); //Customername
                String city = _table.getModel().getValueAt(row, 2).toString(); //City
                String adres = _table.getModel().getValueAt(row, 3).toString(); //adres
                String post = _table.getModel().getValueAt(row, 4).toString(); //postalcode
                String email = _table.getModel().getValueAt(row, 5).toString(); //email
                String tel = _table.getModel().getValueAt(row, 6).toString(); //telphonenumber
                Driver.UpdateCustomer(id, cust, city, adres, post, email, tel);

            }
        });

        sp.setPreferredSize(new Dimension(900, 825));

        label.setFont(new Font("Serif", Font.PLAIN, 24));

        _btnBewerken.addActionListener(this);

        add(label);
        add(sp, "align center");
        add(_btnBewerken);
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>(); //Alle methodes van Vector zijn synchronized en van ArrayList niet.
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _btnBewerken) {
            _table.setEnabled(true);
        }
    }
}