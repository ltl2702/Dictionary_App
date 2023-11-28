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
    private int userID;
    @FXML
    private AnchorPane menumatchgamePane;

    @FXML
    void exitButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("/data/fxml/gameController.fxml"));
            AnchorPane gamepane = fxmlLoader.load();
            mainpane.getChildren().setAll(gamepane);

            GameController gameController = fxmlLoader.getController();
            gameController.setUserID(userID);
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
            menumatchgamePane.getChildren().setAll(gamepane);

            MatchGameController gameController = fxmlLoader.getController();
            //userController.userLogin();
            gameController.setmainpane(mainpane);
            //gameController.setUsername(username);
            gameController.setUserID(userID);
            gameController.initialize();
            //userController.setStage(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    public void setmainpane(AnchorPane gamepane) {
        this.mainpane = gamepane;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
