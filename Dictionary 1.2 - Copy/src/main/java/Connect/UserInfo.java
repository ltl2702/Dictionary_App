package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserInfo {
    public static Connection dbLink;

    private static String DB_URL = null;

    public static Connection connect() throws ClassNotFoundException, SQLException {
        String databasename = "userinfo";
        DB_URL = "jdbc:sqlite:src/main/resources/data/database/" + databasename + ".db";
        Class.forName("org.sqlite.JDBC");
        dbLink = DriverManager.getConnection(DB_URL);
        return dbLink;
    }

    public static void closeConnection() {
        try {
            if (dbLink != null && !dbLink.isClosed()) {
                dbLink.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

}
