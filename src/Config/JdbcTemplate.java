package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTemplate {

    private final String url = "jdbc:postgresql://localhost:5432/geor";
    private final String user = "postgres";
    private final String password = "1"; //password 입력

    public Connection getConnection() throws SQLException {
        Connection connect = DriverManager.getConnection(url, user, password);
        connect.setAutoCommit(false);
        return connect;
    }
}
