package nerdygadgets.backoffice.main.JDBC;
import java.sql.*;
public class Driver {
    public static void main(String[] args) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost/wideworldimporters", "elke", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT DeliveryInstructions FROM invoices");
            while (myRs.next()){
                System.out.println(myRs.getString("DeliveryInstructions"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}