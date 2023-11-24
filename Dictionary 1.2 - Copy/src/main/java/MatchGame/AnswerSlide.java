package MatchGame;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AnswerSlide {

    @FXML
    private JFXButton nextButton;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView memeImage;

    @FXML
    private JFXButton prevButton;

    @FXML
    private Label scoreLabel;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton skipButton;

    @FXML
    private Label wordLabel;
    private Stage window;
    private AnchorPane mainpane;


    @FXML
    void nextButtonOnAction(ActionEvent event) {

    }

    @FXML
    void prevButtonOnAction(ActionEvent event) {

    }

    @FXML
    void searchButtonOnAction(ActionEvent event) {

    }

    @FXML
    void skipButtonOnAction(ActionEvent event) {
        event.consume();
        try {
            window.close();
            FXMLLoader fxmlLoader2 = new FXMLLoader(MenuMatchGame.class.getResource("/data/fxml/MenuMatchGame.fxml"));
            AnchorPane Matchpane = fxmlLoader2.load();
            mainpane.getChildren().setAll(Matchpane);

            MenuMatchGame MenuController = fxmlLoader2.getController();
            MenuController.setmainpane(mainpane);

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    public void setStage(Stage window) {
        this.window = window;
    }

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }
}
