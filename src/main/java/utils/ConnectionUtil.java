package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String DRIVER = ConfigUtil.getProperty("db.driver");
    private static final String URL = ConfigUtil.getProperty("db.url");
    private static final String USER = ConfigUtil.getProperty("db.user");
    private static final String PASS = ConfigUtil.getProperty("db.pass");

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static Connection getConnectionAutoCommitFalse() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASS);
        connection.setAutoCommit(false);
        return connection;
    }
}
