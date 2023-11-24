package Controller;

import MatchGame.MenuMatchGame;
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

    @FXML
    void flippinggameButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuMatchGame.class.getResource("/data/fxml/MenuMatchGame.fxml"));
            AnchorPane Matchpane = fxmlLoader.load();
            gamepane.getChildren().setAll(Matchpane);

            MenuMatchGame MenuController = fxmlLoader.getController();
            //userController.userLogin();
            MenuController.setmainpane(gamepane);
            //userController.setStage(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    void quizgameButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuMatchGame.class.getResource("/data/fxml/MenuMatchGame.fxml"));
            AnchorPane Matchpane = fxmlLoader.load();
            gamepane.getChildren().setAll(Matchpane);

            MenuMatchGame MenuController = fxmlLoader.getController();
            //userController.userLogin();
            MenuController.setmainpane(gamepane);
            //userController.setStage(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

}
