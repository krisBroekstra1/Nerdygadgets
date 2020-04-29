package nerdygadgets.backoffice.main;

import nerdygadgets.backoffice.main.data.LoginData;

public class Main {
    public static void main(String[] args) {
        LoginData d = LoginData.getInstance();
        if (d.isloggedin) {
            Frame f = new Frame(1000, 1000);
            f.add(new ControllerJPanel());
            f.revalidate();
            f.repaint();
        } else {
            Frame f2 = new Frame(300, 200);
            f2.setTitle("Backoffice - Login");
            f2.add(new LoginContent(f2));
            f2.setResizable(false);
            f2.revalidate();
            f2.repaint();
        }
    }
}
