package nerdygadgets.backoffice.main;

public class DriverManager {
    public DriverManager() throws ClassNotFoundException {
        Class.forName("com.mysql.jbdc.Driver");
    }
}
