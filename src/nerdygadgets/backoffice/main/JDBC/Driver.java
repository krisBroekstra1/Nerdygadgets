package nerdygadgets.backoffice.main.JDBC;

import java.sql.*;

public class Driver {
    public static void main(String[] args) {
        adressen();
    }


    public static ResultSet adressen() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT DeliveryInstructions FROM invoices");
            return myRs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}