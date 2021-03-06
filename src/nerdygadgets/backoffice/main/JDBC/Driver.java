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
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
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
    public static void UpdateCustomer(String id, String cust, String city, String adres, String post, String email, String tel){
        try {
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

            System.out.println("Hoeveelheid rows veranderd: "+rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void UpdateVoorraad(String id, String itemname, String Quantity){ //Moet nog iets gebeuren als 1 niet werkt
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

            System.out.println("Hoeveelheid rows veranderd in tabel 1: "+rowsAffected1);
            System.out.println("Hoeveelheid rows veranderd in tabel 2: "+rowsAffected2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}