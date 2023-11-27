package MatchGame;

import Connect.ConnectDB;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AnswerSlide {

    @FXML
    private Label wordLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton prevButton;

    @FXML
    private JFXButton skipButton;

    private Stage window;

    private AnchorPane answerpane;
    private ArrayList<String> WordAns;

    private int currentindex = 0;
    private int userID;

    @FXML
    void skipButtonOnAction(ActionEvent event) {
        event.consume();
        try {
            window.close();
            FXMLLoader fxmlLoader2 = new FXMLLoader(MenuMatchGame.class.getResource("/data/fxml/MenuMatchGame.fxml"));
            AnchorPane Matchpane = fxmlLoader2.load();
            answerpane.getChildren().setAll(Matchpane);

            MenuMatchGame MenuController = fxmlLoader2.getController();
            MenuController.setmainpane(answerpane);
            MenuController.setUserID(userID);

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    void nextButtonOnAction(ActionEvent event) {
        if (currentindex < WordAns.size() - 1) {
            transition(currentindex + 1);
            currentindex = currentindex + 1;
        }
    }

    @FXML
    void prevButtonOnAction(ActionEvent event) {
        if (currentindex > 0) {
            transition(currentindex - 1);
            currentindex = currentindex - 1;
        }
    }

    private void transition(int index) {
        wordLabel.setText(WordAns.get(index));
        String des = getDescription(wordLabel.getText());
        descriptionLabel.setText(des);
    }

    private String getDescription(String text) {
        String description = "";
        text = text.replaceAll("\'", "''");
        try (Connection connection = new ConnectDB().connect("dict_hh")) {
            String select = "SELECT description FROM av1 WHERE word = '" + text + "'";
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

    public void initialize() {
        System.out.println("AnswerSlide: " + this.WordAns);
        if (WordAns != null && !WordAns.isEmpty() && currentindex == 0) {
            wordLabel.setText(WordAns.get(0));
            String des = getDescription(wordLabel.getText());
            descriptionLabel.setText(des);
        }
    }

    public void setWordAns(ArrayList<String> wordAns) {
        this.WordAns = new ArrayList<>(wordAns);
    }

    public void setStage(Stage window) {
        this.window = window;
    }

    public void setmainpane(AnchorPane mainpane) {
        this.answerpane = mainpane;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
