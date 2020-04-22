package nerdygadgets.backoffice.main.JDBC;

import java.sql.*;

public class Driver {
    public static void main(String[] args) {
        medewerkers();
    }



//Functies
    //Test functie
    public static void functie() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("Vul hier jouw SQL query in!");
            while (myRs.next()){
                System.out.println(myRs.getString("Vul hier je columnLabel in!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Functie adressen
    public static void adressen() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT DeliveryInstructions FROM invoices");
            while (myRs.next()){
                System.out.println(myRs.getString("DeliveryInstructions"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Functie medewerkers
    public static void medewerkers() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT FullName FROM people");
            while (myRs.next()){
                System.out.println(myRs.getString("FullName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}