package MatchGame;

import Connect.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class WordAnswer {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label wordLabel;

    @FXML
    private AnchorPane wordpane;

    public void setWordLabel(String wordLabel) {
        this.wordLabel.setText(wordLabel);
        this.descriptionLabel.setText(getDescription(wordLabel));
    }

    public void setVisible(boolean visible) {
        wordpane.setVisible(visible);
    }

    private String getDescription(String text) {
        String description = "";
        text = text.replaceAll("\'", "''");
        try (Connection connection = new ConnectDB().connect("test3")) {
            String select = "SELECT description FROM av WHERE word = '" + text + "'";
            Statement statement = connection.createStatement();
            ResultSet qquery = statement.executeQuery(select);
            if (qquery.next()) {
                description = qquery.getString("description");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection();
        }
        return description;
    }

    public AnchorPane getWordpane() {
        return wordpane;
    }
}
