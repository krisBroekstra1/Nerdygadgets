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
    private Frame frame;

    public LoginContent(Frame f){
        this.frame = f;
        setLayout(new FlowLayout());
        name = new JLabel("Username");
        //name.setBounds(650,0,80,65);
        username = new JTextField();
        //username.setBounds(730,20,165,25);
        username.setPreferredSize(new Dimension(120, 20));
        add(name);
        add(username);

        passwordL = new JLabel("Password");
        //passwordL.setBounds(650,60,80,25);
        password = new JPasswordField();
        //password.setBounds(730,60,165,25);
        password.setPreferredSize(new Dimension(120, 20));
        add(passwordL);
        add(password);

        login = new JButton("LOGIN");
        //login.setBounds(650,100,80,25);
        add(login);
        login.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == login) {
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
                        }
                    }
                }catch (Exception ex){
                    
            }
        }
    }
}
