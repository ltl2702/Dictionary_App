package QuizGamee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class QuizResultController implements Initializable {
    @FXML
    Label yourScore, correct, wrong;
    @FXML
    Button exit, replay;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        yourScore.setText("Your score: " + QuestionController.correct + "/10");
        correct.setText("Correct answers: " + String.valueOf(QuestionController.correct));
        wrong.setText("Incorrect answers: " + String.valueOf(QuestionController.wrong));
    }

    public void exitSetOnAction() {

    }

    public void replay() {
    }

}
