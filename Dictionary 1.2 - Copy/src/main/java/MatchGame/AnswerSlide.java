package MatchGame;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    private AnchorPane[] wordpanes = new AnchorPane[6];

    private Stage window;
    private AnchorPane mainpane;
    private int currentIndex = 0;
    private ArrayList<String> WordAns;

    @FXML
    void initialize() {
        System.out.println("AnswerSlide: " + WordAns);

        // Khởi tạo mảng WordAnswer và thêm chúng vào wordpane
        WordAnswer[] wordAnswers;
        wordAnswers = new WordAnswer[6];

        for (int i = 0; i < 6; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/fxml/WordAnswer.fxml"));
            try {
                Parent root = loader.load();
                wordAnswers[i] = loader.getController();
                wordAnswers[i].setWordLabel(WordAns.get(i));
                wordpanes[i] = wordAnswers[i].getWordpane();
                System.out.println(WordAns.get(i));

                // Hiển thị pane đầu tiên, ẩn các pane khác
                wordAnswers[i].setVisible(i == 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mainpane.getChildren().add(wordpanes[0]);
    }

    @FXML
    void nextButtonOnAction(ActionEvent event) {
        if (currentIndex < 5)
            transitionToPane(currentIndex + 1);
    }

    @FXML
    void prevButtonOnAction(ActionEvent event) {
        if (currentIndex > 0)
            transitionToPane(currentIndex - 1);
    }

    private void transitionToPane(int i) {
        //Tạo transition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), wordpanes[i]);
        transition.setToX(-wordpanes[i].getPrefWidth() * (i - currentIndex));
        transition.setOnFinished(event -> {
            //Xóa pane hiện tại và thêm pane mới
            mainpane.getChildren().remove(wordpanes[currentIndex]);
            mainpane.getChildren().add(wordpanes[i]);

            mainpane.setTranslateX(0);

            currentIndex = i;
        });
        transition.play();
    }

    /*
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
*/


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
