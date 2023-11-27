package QuizGamee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class QuizResultController {

    Stage window = new Stage();
    @FXML
    Label yourScore, correct, wrong;
    @FXML
    Button exit, replay;
    private AnchorPane mainpane;
    private MediaPlayer mediaPlayer;

    public void display(Scene scene) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        if (QuestionController.correct >= 7) {
            Media sound = new Media(getClass().getResource("/data/audio/cheer.mp3").toExternalForm());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } else {
            Media sound = new Media(getClass().getResource("/data/audio/disappointed.mp3").toExternalForm());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }

        yourScore.setText("Your score: " + QuestionController.correct + "/10");
        correct.setText("Correct answers: " + String.valueOf(QuestionController.correct));
        wrong.setText("Incorrect answers: " + String.valueOf(QuestionController.wrong));

        window.setScene(scene);
        window.show();
    }

    @FXML
    void exitOnAction(ActionEvent event) {
        try {
            window.close();
            FXMLLoader fxmlLoader2 = new FXMLLoader(QuizResultController.class.getResource("/data/fxml/QuizStartScene.fxml"));
            AnchorPane Matchpane = fxmlLoader2.load();
            mainpane.getChildren().setAll(Matchpane);

            QuizStartController MenuController = fxmlLoader2.getController();
            MenuController.setmainpane(mainpane);

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    void replayOnAction(ActionEvent event) {
        try {
            window.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/data/fxml/QuestionScene.fxml"));
            AnchorPane gamepane = fxmlLoader.load();
            mainpane.getChildren().setAll(gamepane);

            QuestionController quizController = fxmlLoader.getController();
            QuestionController.setCorrect(0);
            QuestionController.setWrong(0);
            QuestionController.setCounter(1);

            quizController.setmainpane(mainpane);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }
}