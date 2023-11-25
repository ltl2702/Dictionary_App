package Dictionary;

import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DictionaryManagement {
    private static final String TABLE_NAME = "av1";
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

    public static boolean isWordSaved(int wordId, String username) {
        try (Connection connectDatabase = new ConnectDB().connect("test3")) {
            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM SavedWord WHERE English_id = '" + wordId + "' AND User_id = '" + username + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                int count = query.getInt("counter");
                //if (count == 1) {
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
            ex.getCause();
        } finally{
            ConnectDB.closeConnection();
        }
        return false;
    }

    public static void removeSavedWord(int wordId, String username) {
        try (Connection connectDatabase = new ConnectDB().connect("test3")) {
            String verify = "DELETE FROM SavedWord WHERE English_id = '" + wordId + "' AND User_id = '" + username + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);
        } catch(Exception ex){
            ex.printStackTrace();
            ex.getCause();
        } finally{
            ConnectDB.closeConnection();
        }
    }

    public static void saveWordToSavedWord(int wordId, String username) {
        try (Connection connectDatabase = new ConnectDB().connect("test3")) {
            String add = "INSERT INTO SavedWord(English_id, User_id) VALUES ('"
                    + wordId + "','" + username + "')";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(add);
        } catch(Exception ex){
            ex.printStackTrace();
            ex.getCause();
        } finally{
            ConnectDB.closeConnection();
        }
    }
}
