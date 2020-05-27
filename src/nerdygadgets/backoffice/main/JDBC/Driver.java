package nerdygadgets.backoffice.main.JDBC;

import com.mysql.cj.protocol.Resultset;
import nerdygadgets.backoffice.main.data.Shaa256;

import javax.swing.*;
import java.sql.*;

public class Driver {
    public static void main(String[] args) {
    }


    //Functies
    //Functie adressen
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

    //Functie medewerkers
    public static ResultSet medewerkers() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT FullName, EmailAddress, PhoneNumber FROM people");
            return myRs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet login(String username, String password) {

        try {
            password = Shaa256.toHexString(Shaa256.getSHA(password));
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
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


    //Functie Orders
    public static ResultSet orders() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT o.OrderID, c.CustomerName, ci.Cityname FROM orders o LEFT JOIN customers c ON o.CustomerID = c.CustomerID LEFT JOIN cities ci ON c.DeliveryCityID = ci.CityID ORDER BY OrderID");
            return myRs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getStock() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT o.StockItemID, o.StockItemName AS `Productnaam`, QuantityOnHand AS `Aantal`FROM wideworldimporters.stockitems o LEFT JOIN stockitemholdings sh ON o.StockItemID = sh.StockItemID ORDER BY o.StockItemID");
            return myRs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getCustomers() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM customer_ned");
            return myRs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void UpdateCustomer(String id, String cust, String city, String adres, String post, String email, String tel) {
        try {
            String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
            if(post.contains(" ")){
                post = post.replace(" ", "");
            }
            if(!tel.matches(pattern)){
                JOptionPane.showMessageDialog(null, "Telefoon nummer is niet correct", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            // create the java mysql update preparedstatement
            String query = "UPDATE customer_ned SET CustomerName = ?, City = ?, Adres = ?, Postalcode = ?, EmailAddress = ?, TelephoneNumber = ? WHERE CustomerID = ?";
            PreparedStatement preparedStmt = myConn.prepareStatement(query);
            preparedStmt.setString(1, cust);
            preparedStmt.setString(2, city);
            preparedStmt.setString(3, adres);
            preparedStmt.setString(4, post);
            preparedStmt.setString(5, email);
            preparedStmt.setString(6, tel);
            preparedStmt.setString(7, id);

            // execute the java preparedstatement
            int rowsAffected = preparedStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, id + " is succesvol bewerkt", "bewerken voltooid", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Hoeveelheid rows veranderd: " + rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UpdateVoorraad(String id, String itemname, String Quantity) { //Moet nog iets gebeuren als 1 niet werkt
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            // create the java mysql update preparedstatement
            String query1 = "UPDATE stockitems SET StockItemName = ? WHERE StockItemId = ?";
            PreparedStatement preparedStmt1 = myConn.prepareStatement(query1);
            preparedStmt1.setString(1, itemname);
            preparedStmt1.setString(2, id);
            int rowsAffected1 = preparedStmt1.executeUpdate();
            String query2 = "UPDATE stockitemholdings SET QuantityOnHand = ? WHERE StockItemId = ?";
            PreparedStatement preparedStmt2 = myConn.prepareStatement(query2);
            preparedStmt2.setString(1, Quantity);
            preparedStmt2.setString(2, id);
            int rowsAffected2 = preparedStmt2.executeUpdate();

            System.out.println("Hoeveelheid rows veranderd in tabel 1: " + rowsAffected1);
            System.out.println("Hoeveelheid rows veranderd in tabel 2: " + rowsAffected2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getOrderCities() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            String query = "SELECT DISTINCT OrderID, CustomerName, City, Adres, Postalcode, province FROM orders JOIN customer_ned as cn ON orders.CustomerID = cn.CustomerID WHERE Delivered = false AND Country = 'NED' GROUP BY province";
            PreparedStatement preparedstmt = myConn.prepareStatement(query);
            ResultSet result = preparedstmt.executeQuery();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static ResultSet getOrders(String stad, String adress) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            String query = "SELECT OrderID, CustomerName, OrderDate FROM orders JOIN customer_ned as cn ON orders.CustomerID = cn.CustomerID WHERE Delivered = false AND Country = 'NED' AND city = ? AND adres = ?";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, stad);
            preparedStatement.setString(2, adress);

            ResultSet result = preparedStatement.executeQuery();
            return result;
        } catch (Exception exe) {
            exe.printStackTrace();
        }
        return null;
    }

    public static ResultSet getProduct(String orderID) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            String query = "SELECT StockItemName FROM stockitems AS s JOIN orderlines AS ol ON s.StockItemID = ol.StockItemID WHERE ol.OrderID = ?";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, orderID);
            ResultSet result = preparedStatement.executeQuery();
            return result;
        } catch (Exception exep) {
            exep.printStackTrace();
        }
        return null;
    }

    public static ResultSet getOrderCities(String province) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            String query = "SELECT OrderID, CustomerName, City, Adres, Postalcode, province, lat, `long` FROM orders JOIN customer_ned as cn ON orders.CustomerID = cn.CustomerID WHERE Delivered = false AND Country = 'NED' AND province = ?";
            PreparedStatement preparedstmt = myConn.prepareStatement(query);
            preparedstmt.setString(1, province);
            ResultSet result = preparedstmt.executeQuery();
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void setCoördinates(double lat, double lon, String postalcode){
        try{
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            // create the java mysql update preparedstatement
            String query1 = "UPDATE customer_ned SET lat = ?, `long` = ? WHERE Postalcode = ?";
            PreparedStatement preparedStmt1 = myConn.prepareStatement(query1);
            preparedStmt1.setDouble(1, lat);
            preparedStmt1.setDouble(2, lon);
            preparedStmt1.setString(3, postalcode);
            int rowsAffected1 = preparedStmt1.executeUpdate();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

}