package nerdygadgets.backoffice.main;

public interface Connection {
    String url = "jdbc:mysql://localhost/NerdyGadgets";
    String username="henk", password="henkie";

    Connection c =
            DriverManager.getConnection( url,username,password );

}
