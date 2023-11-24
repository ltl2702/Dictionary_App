package MatchGame;

import Controller.Welcome;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Score {

    Stage window = new Stage();

    @FXML
    private Label judgeLabel;

    @FXML
    private ImageView memeImage;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private JFXButton answerButton, okButton;
    
    private int score;
    private AnchorPane mainpane;

    public void setScore(int currentscore) {
        this.score = currentscore;
        this.scoreLabel.setText(String.valueOf(currentscore));
    }

    public void setTime(int i) {
        this.timeLabel.setText(String.valueOf(formatTime(i)));
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    @FXML
    void answerButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AnswerSlide.class.getResource("/data/fxml/answerSlide.fxml"));
            Parent root = fxmlLoader.load();
            AnswerSlide answerSlideController = fxmlLoader.getController();
            answerSlideController.setStage(window);
            answerSlideController.setmainpane(mainpane);

            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    void okButtonOnAction(ActionEvent event) {
        event.consume();
        try {
            window.close();
            FXMLLoader fxmlLoader2 = new FXMLLoader(MenuMatchGame.class.getResource("/data/fxml/MenuMatchGame.fxml"));
            AnchorPane Matchpane = fxmlLoader2.load();
            mainpane.getChildren().setAll(Matchpane);

            MenuMatchGame MenuController = fxmlLoader2.getController();
            MenuController.setmainpane(mainpane);

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }
    
    public void display(Scene scene) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);

        System.out.println(score);
        if (score == 100) {
            judgeLabel.setText("Sir! Please lead us.");
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/data/gameMatching/100.jpg")));
            memeImage.setImage(image);
        } else if (score >= 60 && score < 100) {
            judgeLabel.setText("Very good. Continue to develop.");
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/data/gameMatching/60-80.jpg")));
            memeImage.setImage(image);
        } else if (score == 45) {
            judgeLabel.setText("Good. Continue to develop.");
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/data/gameMatching/45.jpg")));
            memeImage.setImage(image);
        } else {
            judgeLabel.setText("Not good. Try harder.");
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/data/gameMatching/0-30.jpg")));
            memeImage.setImage(image);
        }
        window.setScene(scene);
        window.show();
    }

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }
}
