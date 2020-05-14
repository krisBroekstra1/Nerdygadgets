package nerdygadgets.backoffice.main;

import nerdygadgets.backoffice.main.Route.Route;
import nerdygadgets.backoffice.main.data.CustomerAddress;
import nerdygadgets.backoffice.main.data.GenerateRouteCities;
import nerdygadgets.backoffice.main.data.LoginData;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        LoginData d = LoginData.getInstance();
        if (d.isloggedin) {
            Frame f = new Frame(1000, 1000);
            f.add(new ControllerJPanel());
            f.revalidate();
            f.repaint();
        } else {
            Frame f2 = new Frame(350, 350);
            f2.setTitle("Backoffice - Login");
            f2.add(new LoginContent(f2));
            f2.revalidate();
            f2.repaint();
        }
    }
}
