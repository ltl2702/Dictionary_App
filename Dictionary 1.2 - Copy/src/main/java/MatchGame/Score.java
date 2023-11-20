package MatchGame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Score {

    @FXML
    private Label judgeLabel;

    @FXML
    private ImageView memeImage;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label timeLabel;

    private int score;

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

    public void display() {
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
    }
}
