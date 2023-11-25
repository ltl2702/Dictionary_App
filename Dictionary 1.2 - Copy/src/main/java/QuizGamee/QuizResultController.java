package QuizGamee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class QuizResultController {

    Stage window = new Stage();
    @FXML
    Label yourScore, correct, wrong;
    @FXML
    Button exit, replay;
    private AnchorPane mainpane;

    public void display(Scene scene) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);

        yourScore.setText("Your score: " + QuestionController.correct + "/10");
        correct.setText("Correct answers: " + String.valueOf(QuestionController.correct));
        wrong.setText("Incorrect answers: " + String.valueOf(QuestionController.wrong));

        window.setScene(scene);
        window.show();
    }

    public void exitSetOnAction() {

    }

    public void replay() {
    }

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }
}
