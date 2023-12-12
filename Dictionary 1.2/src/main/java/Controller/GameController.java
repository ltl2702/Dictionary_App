package Controller;

import MatchGame.MenuMatchGame;
import QuizGamee.QuizStartController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class GameController {

    @FXML
    private JFXButton flippinggameButton;

    @FXML
    private JFXButton quizgameButton;

    @FXML
    private AnchorPane gamepane;
    private int userID;

    @FXML
    void flippinggameButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuMatchGame.class.getResource("/data/fxml/MenuMatchGame.fxml"));
            AnchorPane Matchpane = fxmlLoader.load();
            gamepane.getChildren().setAll(Matchpane);

            MenuMatchGame MenuController = fxmlLoader.getController();
            //userController.userLogin();
            MenuController.setmainpane(gamepane);
            MenuController.setUserID(userID);
            //userController.setStage(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    void quizgameButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(QuizStartController.class.getResource("/data/fxml/QuizStartScene.fxml"));
            AnchorPane Matchpane = fxmlLoader.load();
            gamepane.getChildren().setAll(Matchpane);

            QuizStartController QuizController = fxmlLoader.getController();
            //userController.userLogin();
            QuizController.setmainpane(gamepane);
            QuizController.setUserID(userID);
            //userController.setStage(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
