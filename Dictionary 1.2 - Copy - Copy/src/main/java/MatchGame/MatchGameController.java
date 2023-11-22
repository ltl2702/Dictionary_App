package MatchGame;

import Connect.ConnectDB;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private boolean isButtonClickable = true;
    private JFXButton firstPressedButton;

    private int score = 0;
    private int currentscore = 0;
    private Timeline countdown;
    private int timeRemaining = 180;
    private AnchorPane mainpane;

    private void loadDataFromDatabase() {
            try (Connection connectDatabase = new ConnectDB().connect("test3")) {
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
        card1.setText(list.get(0));
        card2.setText(list.get(1));
        card3.setText(list.get(2));
        card4.setText(list.get(3));
        card5.setText(list.get(4));
        card6.setText(list.get(5));
        card7.setText(list.get(6));
        card8.setText(list.get(7));
        card9.setText(list.get(8));
        card10.setText(list.get(9));
        card11.setText(list.get(10));
        card12.setText(list.get(11));
    }

    private ArrayList<String> getDescription(String text) {
        List<String> descriptions = null;
        text = text.replaceAll("\'", "''");
        descriptions = new ArrayList<>();
        try (Connection connection = new ConnectDB().connect("test3")) {
            String query = "SELECT COUNT(*) AS counter FROM av WHERE description = '" + text + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int count = resultSet.getInt("counter");
                if (count >= 1) {
                    descriptions.add(text);
                }
                else {
                    String select = "SELECT description FROM av WHERE word = '" + text + "'";
                    ResultSet qquery = statement.executeQuery(select);
                    while (qquery.next()) {
                        descriptions.add(qquery.getString("description"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection();
        }
        return (ArrayList<String>) descriptions;
    }

/*
    private ArrayList<String> getDescription(String text) {
        ArrayList<String> descriptions = new ArrayList<>();
        try (Connection connection = new ConnectDB().connect("test3")) {
            String query = "SELECT COUNT(*) AS counter FROM av WHERE description = ?";
            try (PreparedStatement countStatement = connection.prepareStatement(query)) {
                countStatement.setString(1, text);
                ResultSet resultSet = countStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt("counter");
                    if (count >= 1) {
                        descriptions.add(text);
                    } else {
                        String select = "SELECT description FROM av WHERE word = ?";
                        try (PreparedStatement selectStatement = connection.prepareStatement(select)) {
                            selectStatement.setString(1, text);
                            ResultSet qquery = selectStatement.executeQuery();
                            while (qquery.next()) {
                                descriptions.add(qquery.getString("description"));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection();
        }
        return descriptions;
    }*/

    private void handleButtonClick(JFXButton button) {
        if (isButtonClickable) {
            if (!isButtonPressed) {
                // Nếu chưa có nút nào được nhấn trước đó
                firstPressedButton = button;
                isButtonPressed = true;
                button.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-radius: 10; -fx-border-radius: 10;");
            } else {
                // Đã có một nút được nhấn trước đó
                if (button != firstPressedButton) {
                    isButtonClickable = false;
                    // Kiểm tra xem 2 nút có tạo thành cặp hay không
                    if (isPair(firstPressedButton, button)) {
                        System.out.println("trueeeeee");
                        // Nếu là cặp, triệt tiêu chúng
                        button.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-radius: 10; -fx-border-radius: 10;");
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
                        System.out.println("false");
                        System.out.println(getDescription(firstPressedButton.getText()));
                        System.out.println(getDescription(button.getText()));
                        button.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-radius: 10; -fx-border-radius: 10;");
                        firstPressedButton.setDisable(false);
                        button.setDisable(false);
                        firstPressedButton.setOpacity(1);
                        button.setOpacity(1);

                        animation(firstPressedButton);
                        animation(button);

                        // Đặt lại màu border về màu đen
                        firstPressedButton.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-radius: 10; -fx-border-radius: 10;");
                        button.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-radius: 10; -fx-border-radius: 10;");
                    }
                    //Reset trạng thái
                    isButtonPressed = false;
                    firstPressedButton = null;
                    isButtonClickable = true;

                } else {
                    isButtonPressed = false;
                    firstPressedButton = null;
                    isButtonClickable = true;
                    button.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-radius: 10; -fx-border-radius: 10;");
                }
            }
        }
    }

    private boolean isPair(JFXButton button1, JFXButton button2) {
        // Kiểm tra xem 2 nút có tạo thành cặp hay không
        //word word, word des, des word, des des
        String word1 = button1.getText();
        String word2 = button2.getText();
        ArrayList<String> description1 = getDescription(word1);
        ArrayList<String> description2 = getDescription(word2);
        if (!description1.isEmpty() && !description2.isEmpty()) {
            for (String string : description1) {
                for (String s : description2) {
                    if (string.equalsIgnoreCase(s)) {
                        System.out.println(description1);
                        System.out.println(description2);
                        return true;
                    }
                }
            }
        }
        return false;
    }

/*
    private boolean isPair(JFXButton button1, JFXButton button2) {
        // Get descriptions for the two buttons
        ArrayList<String> description1 = getDescription(button1.getText());
        ArrayList<String> description2 = getDescription(button2.getText());

        // Check for null or empty descriptions
        if (description1 == null || description2 == null || description1.isEmpty() || description2.isEmpty()) {
            return false;
        }

        // Convert descriptions to sets for efficient comparison
        Set<String> descriptionSet1 = new HashSet<>(description1);
        Set<String> descriptionSet2 = new HashSet<>(description2);

        // Check if there is any common description
        for (String desc1 : descriptionSet1) {
            for (String desc2 : descriptionSet2) {
                if (desc1.equalsIgnoreCase(desc2)) {
                    // Found a common description, it's a pair
                    System.out.println(desc1);
                    System.out.println(desc2);
                    return true;
                }
            }
        }

        // No common description found, not a pair
        return false;
    }
    */

    private void animation(JFXButton button) {
        //Vị trí ban đầu
        double x = button.getTranslateX();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), new KeyValue(button.translateXProperty(), x - 5)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(button.translateXProperty(), x + 10)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(button.translateXProperty(), x - 10)),
                new KeyFrame(Duration.seconds(0.4), new KeyValue(button.translateXProperty(), x + 10)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(button.translateXProperty(), x - 5))
        );

        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Set lại vị trí ban đầu khi hiệu ứng kết thúc
                button.setTranslateX(x);
            }
        });

        timeline.play();

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

            window.setTitle("Score!");
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
