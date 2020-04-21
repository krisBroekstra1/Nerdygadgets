package nerdygadgets.backoffice.main;

import java.sql.Connection;
import java.sql.SQLException;

public class Statement {
    private Connection connection;
    Statement s = (Statement) connection.createStatement();

    public Statement() throws SQLException {
    }
}
