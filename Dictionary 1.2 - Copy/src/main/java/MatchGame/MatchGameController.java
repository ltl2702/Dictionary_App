package MatchGame;

import Connect.ConnectDB;
import Controller.Home;
import Controller.Welcome;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.net.URL;
import java.util.*;

public class MatchGameController implements Initializable {

    @FXML
    private JFXButton card1;

    @FXML
    private JFXButton card10;

    @FXML
    private JFXButton card11;

    @FXML
    private JFXButton card12;

    @FXML
    private JFXButton card2;

    @FXML
    private JFXButton card3;

    @FXML
    private JFXButton card4;

    @FXML
    private JFXButton card5;

    @FXML
    private JFXButton card6;

    @FXML
    private JFXButton card7;

    @FXML
    private JFXButton card8;

    @FXML
    private JFXButton card9;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label timeLabel;

    private List<String> list = new ArrayList<>();

    private boolean isButtonPressed = false;
    private JFXButton firstPressedButton;

    private int score = 0;
    private int currentscore = 0;
    private Timeline countdown;
    private int timeRemaining = 180;
    private AnchorPane mainpane;

    private void loadDataFromDatabase() {
            try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
                //Verifies.
                String verify = "SELECT word, description FROM av ORDER BY RANDOM() LIMIT 6";
                Statement statement = connectDatabase.createStatement();
                ResultSet query = statement.executeQuery(verify);

                //randomPairs = new ArrayList<>();
                while (query.next()) {
                    String word = query.getString("word");
                    String description = query.getString("description");
                    //randomPairs.add(new RandomPair(word, description));
                    list.add(word);
                    list.add(description);
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            } finally {
                ConnectDB.closeConnection();
            }
        }

    private void updateButtonText() {
        Collections.shuffle(list);
        card1.setText(list.get(1));
        card2.setText(list.get(2));
        card3.setText(list.get(3));
        card4.setText(list.get(4));
        card5.setText(list.get(5));
        card6.setText(list.get(6));
        card7.setText(list.get(7));
        card8.setText(list.get(8));
        card9.setText(list.get(9));
        card10.setText(list.get(10));
        card11.setText(list.get(11));
        card12.setText(list.get(0));
    }

    private String getWord(String description) {
        String word = "";
        try (Connection connection = new ConnectDB().connect("dict_hh")) {
            String query = "SELECT COUNT(*) AS counter FROM av WHERE word = '" + description + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int count = resultSet.getInt("counter");
                if (count == 1) {
                    word = description;
                }
                else {
                    String select = "SELECT word FROM av WHERE description = '" + description + "'";
                    ResultSet qquery = statement.executeQuery(select);
                    if (qquery.next()) {
                        //int imageNumber = query.getInt("image");
                        word = qquery.getString("word");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection();
        }
        return word;
    }

    private void handleButtonClick(JFXButton button) {
        if (!isButtonPressed) {
            // Nếu chưa có nút nào được nhấn trước đó
            firstPressedButton = button;
            isButtonPressed = true;
        } else {
            // Đã có một nút được nhấn trước đó
            if (button != firstPressedButton) {
                // Kiểm tra xem 2 nút có tạo thành cặp hay không
                if (isPair(firstPressedButton, button)) {
                    // Nếu là cặp, triệt tiêu chúng
                    firstPressedButton.setDisable(true);
                    button.setDisable(true);
                    firstPressedButton.setOpacity(0);
                    button.setOpacity(0);
                    score++;
                    if (score < 5) {
                        currentscore = score * 15;
                        scoreLabel.setText(String.valueOf(currentscore));
                    } else if (score == 5) {
                        currentscore = 80;
                        scoreLabel.setText(String.valueOf(currentscore));
                    } else if (score == 6) {
                        currentscore = 100;
                        scoreLabel.setText(String.valueOf(currentscore));
                        close();
                    }
                } else {
                    // Nếu không phải là cặp, đặt lại trạng thái nhấn của cả hai nút
                    firstPressedButton.setDisable(false);
                    button.setDisable(false);
                    firstPressedButton.setOpacity(1);
                    button.setOpacity(1);
                }
            }
            // Reset trạng thái
            isButtonPressed = false;
            firstPressedButton = null;
        }
    }

    private boolean isPair(JFXButton button1, JFXButton button2) {
        // Kiểm tra xem 2 nút có tạo thành cặp hay không
        String description1 = button1.getText();
        String description2 = button2.getText();
        String word1 = getWord(description1);
        String word2 = getWord(description2);
        //return description1.equals(word2) && description2.equals(word1);
        return word1.equals(word2);
    }

    private void start() {
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeRemaining--;
            timeLabel.setText(String.valueOf(formatTime(timeRemaining)));
            if (timeRemaining <= 0) {
                close();
            }
        }));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }

    private void close() {
        System.out.println("Điểm của bạn là: " + currentscore);
        System.out.println("Thời gian còn lại: " + formatTime(timeRemaining));
        // Dừng đếm ngược
        countdown.stop();
        Stage window = new Stage();
        FXMLLoader fxmlLoader1 = new FXMLLoader(Score.class.getResource("/data/fxml/ScoreMatchGame.fxml"));
        try {
            Parent root = fxmlLoader1.load();
            Score scoreController = fxmlLoader1.getController();
            scoreController.setScore(currentscore);
            scoreController.setTime(180 - timeRemaining);
            scoreController.display();
            Scene scene = new Scene(root, 500, 350);

            //Closes the window.
            window.setOnCloseRequest(e -> {
                e.consume();
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
            });

            window.setTitle("Hello!");
            window.setScene(scene);
            window.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataFromDatabase();
        updateButtonText();
        scoreLabel.setText(String.valueOf(0));
        timeLabel.setText(String.valueOf(formatTime(180)));
        start();

        card1.setOnAction(event -> handleButtonClick(card1));
        card2.setOnAction(event -> handleButtonClick(card2));
        card3.setOnAction(event -> handleButtonClick(card3));
        card4.setOnAction(event -> handleButtonClick(card4));
        card5.setOnAction(event -> handleButtonClick(card5));
        card6.setOnAction(event -> handleButtonClick(card6));
        card7.setOnAction(event -> handleButtonClick(card7));
        card8.setOnAction(event -> handleButtonClick(card8));
        card9.setOnAction(event -> handleButtonClick(card9));
        card10.setOnAction(event -> handleButtonClick(card10));
        card11.setOnAction(event -> handleButtonClick(card11));
        card12.setOnAction(event -> handleButtonClick(card12));
    }

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }
}
