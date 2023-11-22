package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

    private static Connection dbLink;

    private static String DB_URL = null;

    public static Connection connect(String databaseName) {
        try {
            DB_URL = "jdbc:sqlite:src/main/resources/data/database/" + databaseName + ".db";
            Class.forName("org.sqlite.JDBC");

            dbLink = DriverManager.getConnection(DB_URL);
            return dbLink;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection() {
        try {
            if (dbLink != null && !dbLink.isClosed()) {
                dbLink.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
