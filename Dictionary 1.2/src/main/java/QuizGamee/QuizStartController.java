package QuizGamee;

import Controller.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuizStartController {
    @FXML
    private Button playQuizButton;
    private AnchorPane mainpane;

    /*
    @FXML
    private void initializeQuiz () {
        playQuizButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage thisstage = (Stage)((Button) event.getSource()).getScene().getWindow();
                    thisstage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/data/fxml/QuestionScene.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    //QuizStartController.randomQuestion();
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

     */


    @FXML
    private Button exitQuizButton;
    private int userID;

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
    void playQuizButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/data/fxml/QuestionScene.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            QuestionController quizController = fxmlLoader.getController();
            quizController.setmainpane(mainpane);
            quizController.setUserID(userID);
            quizController.initialize(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setmainpane(AnchorPane gamepane) {
        this.mainpane = gamepane;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}