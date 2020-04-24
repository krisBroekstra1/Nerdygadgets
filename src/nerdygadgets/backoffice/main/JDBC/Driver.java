package nerdygadgets.backoffice.main.JDBC;

import nerdygadgets.backoffice.main.data.Shaa256;

import java.sql.*;

public class Driver {
    public static void main(String[] args) {
    }


    //Functies
    //Functie adressen
    public static ResultSet adressen() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "elke", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT DeliveryInstructions FROM invoices");
            return myRs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //Zo lees je de gegevens uit
    /*ResultSet myRs = Driver.adressen();
        try {
        while (myRs.next()) {
            System.out.println(myRs.getString("DeliveryInstructions"));
            System.out.println();
        }
    }
        catch (Exception e) {
        e.printStackTrace();
    }*/

    //Functie medewerkers
    public static ResultSet medewerkers() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "elke", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT FullName, EmailAddress, PhoneNumber FROM people");
            return myRs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet login(String username, String password){

        try {
            password = Shaa256.toHexString(Shaa256.getSHA(password));
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "elke", "");
            String sql = "SELECT COUNT(*) FROM people WHERE LogonName = ? AND fixedpassword = ?";
            PreparedStatement ps = myConn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet myRs = ps.executeQuery();
            return myRs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Zo lees je de gegevens uit
    /*ResultSet myRs = Driver.medewerkers();
        try {
        while (myRs.next()) {
            System.out.println(myRs.getString("FullName"));
            System.out.println(myRs.getString("EmailAddress"));
            System.out.println(myRs.getString("PhoneNumber"));
            System.out.println();
        }
    }
        catch (Exception e) {
        e.printStackTrace();
    }*/

    //Functie Orders
    public static ResultSet orders() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "elke", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT o.OrderID, c.CustomerName , ci.Cityname AS `Leveradres` FROM orders o LEFT JOIN customers c ON o.CustomerID = c.CustomerID LEFT JOIN cities ci ON c.DeliveryCityID = ci.CityID ORDER BY OrderID");
            return myRs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}