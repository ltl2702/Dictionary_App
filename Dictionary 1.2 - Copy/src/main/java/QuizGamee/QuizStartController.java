package QuizGamee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class QuizStartController {
    @FXML
    private Button playQuizButton;
    private AnchorPane mainpaine;

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

    public void setmainpane(AnchorPane gamepane) {
        this.mainpaine = gamepane;
    }
}