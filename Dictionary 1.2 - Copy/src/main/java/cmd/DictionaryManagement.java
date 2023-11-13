package cmd;

import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DictionaryManagement {
    private static final String TABLE_NAME = "av";

    public static ObservableList<Word> dbSearchWord(String keyWord, String datatable) {
        ObservableList<Word> list = FXCollections.observableArrayList();
        Connection c = null;
        Statement stmt = null;

        try {
            // Establish the database connection using ConnectDB class
            c = ConnectDB.connect("dict_hh");

            stmt = c.createStatement();
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE word LIKE " + keyWord;
            System.out.println("Executing SQL query: " + sql);

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Word word = new Word(
                        rs.getString("word"),
                        rs.getString("html")
                );
                list.add(word);
            }
        } catch (Exception e) {
            System.out.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the database connection
            ConnectDB.closeConnection();
        }
        return list;
    }
}
