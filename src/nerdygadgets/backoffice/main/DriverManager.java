package nerdygadgets.backoffice.main;

public class DriverManager {
    public DriverManager() throws ClassNotFoundException {
        Class.forName("com.mysql.jbdc.Driver");
    }

    public static Connection getConnection(String url, String username, String password) {
        return null;
    }
}
