package cmd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DictionaryManagement {
    private static final String url = "jdbc:sqlite:./src/main/resources/data/database/dictionary_database.db";

    public static ObservableList<Word> dbSearchWord(String keyWord, String tableName) {
        ObservableList<Word> list = FXCollections.observableArrayList();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "SELECT * FROM " + tableName + " WHERE word like " + keyWord + " ORDER BY word";
            System.out.println("Executing SQL query: " + sql);

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Word word = new Word(
                        rs.getString("word"),
                        rs.getString("html")
                );
                list.add(word);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.out.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (Exception e) {
                System.out.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return list;
    }
}
