package com.example.btl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserInfo {
    /*
    public Connection dbLink;

    public Connection getConnection() {
        String dbName = "usersinfo";
        String dbUser = "abcd";
        String dbPass = "abcdef";
        String Url = "jdbc:mysql://localhost:3306/" + dbName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbLink = DriverManager.getConnection(Url, dbUser, dbPass);

        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return dbLink;
    }
     */
    public static Connection dbLink;

    private static String DB_URL = null;

    public static Connection connect() throws ClassNotFoundException, SQLException {
        String databasename = "userinfo";
        DB_URL = "jdbc:sqlite:src/main/java/com/example/btl/" + databasename + ".db";
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
