package nerdygadgets.backoffice.main;

import nerdygadgets.backoffice.main.JDBC.Driver;
import nerdygadgets.backoffice.main.data.LoginData;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args){
        LoginData d = LoginData.getInstance();
        if(d.isloggedin){
            Frame f = new Frame(1000, 1000);
            f.add(new ControllerJPanel());
            f.revalidate();
            f.repaint();
        } else {
            Frame f2 = new Frame(200, 200);
            f2.add(new LoginContent(f2));
        }

    }
}
