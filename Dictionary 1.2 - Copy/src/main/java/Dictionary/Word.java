package Dictionary;

import Connect.Alerter;
import Connect.ConnectDB;
import Connect.HTML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Word {
    private String wordTarget;
    private String html;

    public static final Word NOT_FOUND = new Word("Not found", "");

    public Word() {
    }

    public Word(String wordTarget, String html) {
        this.wordTarget = wordTarget;
        this.html = html;
    }
    public String getWordTarget(){return wordTarget;}

    public String getHtml() {
        return html;
    }

    @Override
    public String toString() {
        return wordTarget;
    }

    public int getId() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String verify = "SELECT ID FROM av1 WHERE word = '" + wordTarget + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
              int ID = query.getInt("ID");
              return ID;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
        return 0;
    }
}
