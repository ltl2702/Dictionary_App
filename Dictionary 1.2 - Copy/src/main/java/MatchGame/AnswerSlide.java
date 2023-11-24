package MatchGame;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class AnswerSlide {

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton prevButton;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton skipButton;

   AnchorPane[] wordpane = new AnchorPane[6];

    private Stage window;
    private AnchorPane mainpane;
    private TranslateTransition transition;
    private int currentIndex = 0;

    private WordAnswer[] wordAnswers; // Mảng chứa các WordAnswer
    private ArrayList<String> WordAns;

    @FXML
    void initialize() {
        mainpane.getChildren().addAll(wordpane);
        System.out.println("AnswerSlide: " + WordAns);

        // Khởi tạo mảng WordAnswer và thêm chúng vào wordpane
        wordAnswers = new WordAnswer[6]; // Thay đổi số lượng theo số lượng pane thực tế của bạn

        for (int i = 0; i < wordAnswers.length; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/fxml/WordAnswer.fxml"));
            try {
                AnchorPane pane = loader.load();
                wordAnswers[i] = loader.getController();
                wordAnswers[i].setWordLabel(WordAns.get(i));
                wordAnswers[i].getWordpane();
                System.out.println(WordAns.get(i));

                // Hiển thị pane đầu tiên, ẩn các pane khác
                wordAnswers[i].setVisible(i == 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void nextButtonOnAction(ActionEvent event) {
        int nextIndex = (currentIndex + 1) % wordAnswers.length;
        playTransition(nextIndex);
    }

    @FXML
    void prevButtonOnAction(ActionEvent event) {
        int prevIndex = (currentIndex - 1 + wordAnswers.length) % wordAnswers.length;
        playTransition(prevIndex);
    }

    private void playTransition(int targetIndex) {
        // Dừng hiệu ứng hiện tại (nếu có)
        if (transition != null) {
            transition.stop();
        }

        // Tạo hiệu ứng chuyển slide
        transition = new TranslateTransition(Duration.seconds(1), wordpane[i]);
        transition.setFromX(-currentIndex * mainpane.getWidth());
        transition.setToX(-targetIndex * mainpane.getWidth());

        // Cài đặt sự kiện khi hoàn thành hiệu ứng
        transition.setOnFinished(event -> {
            currentIndex = targetIndex;
            updateButtonVisibility();
        });

        // Chơi hiệu ứng
        transition.play();
    }

    private void updateButtonVisibility() {
        // Hiển thị/ẩn nút "Prev" và "Next" tùy thuộc vào vị trí hiện tại
        prevButton.setDisable(currentIndex == 0);
        nextButton.setDisable(currentIndex == wordAnswers.length - 1);

        // Hiển thị/ẩn các WordAnswer tùy thuộc vào vị trí hiện tại
        for (int i = 0; i < wordAnswers.length; i++) {
            wordAnswers[i].setVisible(i == currentIndex);
        }
    }

    @FXML
    void skipButtonOnAction(ActionEvent event) {
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

    @FXML
    void searchButtonOnAction(ActionEvent event) {

    }

    public void setStage(Stage window) {
        this.window = window;
    }

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }

    public void setWordAns(ArrayList<String> wordAns) {
        this.WordAns = wordAns;
    }
}
