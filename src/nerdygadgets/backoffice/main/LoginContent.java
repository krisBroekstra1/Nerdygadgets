package nerdygadgets.backoffice.main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginContent extends JPanel implements ActionListener {
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private JLabel name, passwordL;
    private String testname = "KRIS";
    private String testpassword = "KRIS";

    public LoginContent(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createHorizontalGlue());
        name = new JLabel("username");
        username = new JTextField();
        username.setPreferredSize(new Dimension(100, 20));
        add(name);
        add(username);

        passwordL = new JLabel("password");
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(100, 20));
        add(passwordL);
        add(password);

        login = new JButton("LOGIN");
        add(login);
        login.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == login) {
            if(username.getText().equals("KRIS") && password.getText().equals("KRIS")) {
                System.out.println("Je bent ingelogd");
            }
            else {
                System.out.println("De gebruikersnaam of het wachtwoord is onjuist");
            }
        }
    }
}
