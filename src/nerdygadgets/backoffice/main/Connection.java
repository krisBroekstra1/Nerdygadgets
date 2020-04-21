package nerdygadgets.backoffice.main;

public class Connection {
    public Connection() {
        String url = "jdbc:mysql://localhost/NerdyGadgets";
        String username = "ICTM2G2", password = "WeetIkVeel";

        Connection connection =
                DriverManager.getConnection(url, username, password);
    }
}
