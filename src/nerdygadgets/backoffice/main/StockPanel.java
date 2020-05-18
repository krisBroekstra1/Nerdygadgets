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

public class StockPanel extends JPanel implements ActionListener {

    JTable _table;
    JButton _btnBewerken;
    JLabel label;


    public StockPanel(){
        ResultSet rs = Driver.getStock();
        setLayout(new MigLayout("wrap", "[center, grow]"));
        label = new JLabel("Voorraad");
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


        _table.setBounds(30, 40, 200, 200);
        _table.getTableHeader().setBackground(new Color(217, 43, 133));
        _table.setEnabled(false);
        _table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = _table.getSelectedRow();
                String id = _table.getModel().getValueAt(row, 0).toString(); //StockItemId
                String itemname = _table.getModel().getValueAt(row, 1).toString(); //StockItemName
                String Quantity = _table.getModel().getValueAt(row, 2).toString(); //QuantityOnHand
                Driver.UpdateVoorraad(id, itemname, Quantity);
            }
        });
//        table.setBackground(new Color(255,255,255));

        sp.setPreferredSize(new Dimension(900, 825));
        label.setFont(new Font("Serif", Font.PLAIN, 24));

        _btnBewerken.addActionListener(this);

        add(label);
        add(sp);
        add(_btnBewerken);
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();//Alle methodes van Vector zijn synchronized en van ArrayList niet.
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
