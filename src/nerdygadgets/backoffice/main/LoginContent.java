package nerdygadgets.backoffice.main;

import com.mysql.cj.log.Log;
import nerdygadgets.backoffice.main.JDBC.Driver;
import nerdygadgets.backoffice.main.data.LoginData;

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
    private String testname = "KRIS";
    private String testpassword = "KRIS";
    private Frame frame;

    public LoginContent(Frame f){
        this.frame = f;
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
            try{
                ResultSet myrs = Driver.login(username.getText(), password.getText());
                if(myrs.next()) {
                    if (myrs.getRow() == 1) {
                        LoginData session = LoginData.getInstance();
                        session.setEmail("HENK@gmail.com");
                        session.setId("1");
                        session.setName("KRIS");
                        session.login();
                        frame.remove(this);
                        frame.add(new JLabel("HUTS, Je bent ingelogd"));
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        System.out.println("De gebruikersnaam of het wachtwoord is onjuist");
                    }
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
