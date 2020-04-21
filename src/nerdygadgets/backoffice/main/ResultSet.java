package nerdygadgets.backoffice.main;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultSet {
    private PreparedStatement s;
    ResultSet rs =
            (ResultSet) s.executeQuery(
                    "SELECT AUTHORNAME FROM AUTHORS");

    public ResultSet() throws SQLException {
    }
}
