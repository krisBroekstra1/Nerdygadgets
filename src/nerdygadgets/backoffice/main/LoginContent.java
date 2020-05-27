package nerdygadgets.backoffice.main;

import com.mysql.cj.log.Log;
import nerdygadgets.backoffice.main.JDBC.Driver;
import nerdygadgets.backoffice.main.data.LoginData;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class LoginContent extends JPanel implements ActionListener {
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private JLabel name, passwordL;
    private Frame frame;

    public LoginContent(Frame f) {
        this.frame = f;
        setLayout(new MigLayout("wrap 1", "[center][right][left][c]", "[top][center][b]"));
        add(Box.createHorizontalGlue());
        name = new JLabel("Email:");
        username = new JTextField();
        username.setPreferredSize(new Dimension(100, 20));
        add(name, "center");
        add(username, "center");

        passwordL = new JLabel("Wachtwoord:");
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(100, 20));
        add(passwordL, "center");
        add(password, "center");

        login = new JButton("LOGIN");
        add(login, "center");
        login.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            try {
                ResultSet myrs = Driver.login(username.getText(), String.valueOf(password.getPassword()));
                if (myrs.next()) {
                    int rows = Integer.parseInt(myrs.getString("COUNT(*)"));
                    if (rows == 1) {
                        LoginData logindata = LoginData.getInstance();
                        logindata.setEmail("HENK@gmail.com");
                        logindata.setId("1");
                        logindata.setName("KRIS");
                        logindata.login();
                        frame.setVisible(false);
                        frame.dispose();
                        Main.main(new String[0]);
                    } else {
                        System.out.println("De gebruikersnaam of het wachtwoord is onjuist");
                        this.add(new JLabel("Gebruikersnaam of wachtwoord is onjuist"));
                        frame.revalidate();
                        frame.repaint();
                    }
                }
            } catch (Exception ex) {

            }
        }
    }
}
