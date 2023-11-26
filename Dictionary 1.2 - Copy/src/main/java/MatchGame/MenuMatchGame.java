package MatchGame;

import Controller.GameController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MenuMatchGame {

    @FXML
    private JFXButton exitButton;

    @FXML
    private JFXButton playButton;
    private AnchorPane mainpane;
    private TextField username;

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
        System.out.println("Play button: " + username.getText());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MatchGameController.class.getResource("/data/fxml/MatchGame.fxml"));
            AnchorPane gamepane = fxmlLoader.load();
            mainpane.getChildren().setAll(gamepane);

            MatchGameController gameController = fxmlLoader.getController();
            //userController.userLogin();
            gameController.setmainpane(mainpane);
            gameController.setUsername(username);
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

    public void setUsername(TextField username) {
        this.username = username;
    }
}
