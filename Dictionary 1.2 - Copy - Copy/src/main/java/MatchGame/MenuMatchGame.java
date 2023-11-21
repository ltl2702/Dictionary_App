package MatchGame;

import Controller.GameController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class MenuMatchGame {

    @FXML
    private JFXButton exitButton;

    @FXML
    private JFXButton playButton;
    private AnchorPane mainpane;

    @FXML
    void exitButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("/data/fxml/gameController.fxml"));
            AnchorPane gamepane = fxmlLoader.load();
            mainpane.getChildren().setAll(gamepane);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    void playButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MatchGameController.class.getResource("/data/fxml/MatchGame.fxml"));
            AnchorPane gamepane = fxmlLoader.load();
            mainpane.getChildren().setAll(gamepane);

            MatchGameController gameController = fxmlLoader.getController();
            //userController.userLogin();
            gameController.setmainpane(mainpane);
            //userController.setStage(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    public void setmainpane(AnchorPane gamepane) {
        this.mainpane = gamepane;
    }
}
