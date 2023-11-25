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
    private String word;
    private String html;

    public static final Word NOT_FOUND = new Word("Not found", "");

    private int sequentialId;
    private int id;
    private String pronounce;
    private String description;
    public Word() {
    }

    public Word(int id, String word, String pronounce, String description, int sequentialId) {
        this.id = id;
        this.word = word;
        this.pronounce = pronounce;
        this.description = description;
        this.sequentialId = sequentialId;
    }

    public Word(String word, String html) {
        this.word = word;
        this.html = html;
    }

    public String getWord(){return word;}

    public int getSequentialId() {
        return sequentialId;
    }

    public String getHtml() {
        return html;
    }

    @Override
    public String toString() {
        return word;
    }

    public int getId() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String verify = "SELECT ID FROM av1 WHERE word = '" + word + "'";
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

    public String getDescription() {
        return description;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }
}
