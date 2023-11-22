package Dictionary;

import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DictionaryManagement {
    private static final String TABLE_NAME = "av";
    public static ObservableList<Word> dbSearchWord(String keyWord, String datatable) {
        ObservableList<Word> list = FXCollections.observableArrayList();
        keyWord = keyWord.trim().replaceAll("\\s+", " ");
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE word LIKE ?" ;
        try (Connection c = ConnectDB.connect("dict_hh");
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, keyWord + "%");
            System.out.println("Executing SQL query: " + sql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String wordValue = rs.getString("word");
                    String htmlValue = rs.getString("html");

                    if (wordValue != null && htmlValue != null) {
                        Word word = new Word(wordValue, htmlValue);
                        list.add(word);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
