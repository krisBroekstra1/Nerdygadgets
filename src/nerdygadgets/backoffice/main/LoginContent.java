package nerdygadgets.backoffice.main;

import javax.swing.*;
import java.awt.*;

public class LoginContent extends JPanel {
    private JTextField username, password;
    private JButton login;
    private JLabel name, passwordL;

    public LoginContent(){
        name = new JLabel("username");
        username = new JTextField();
        username.setPreferredSize(new Dimension(100, 20));
        add(name);
        add(username);

        passwordL = new JLabel("password");
        password = new JTextField();
        password.setPreferredSize(new Dimension(100, 20));
        add(passwordL);
        add(password);

        add(login);
    }

}
