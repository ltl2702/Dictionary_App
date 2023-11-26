package MatchGame;

import Connect.ConnectDB;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.*;

public class MatchGameController {

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

    @FXML
    private JFXButton PauseButton;

    @FXML
    private ImageView pauseImage;

    private List<String> list = new ArrayList<>();

    private boolean isButtonPressed = false;
    private boolean isButtonClickable = true;
    private JFXButton firstPressedButton;

    private int score = 0;
    private int currentscore = 0;
    private Timeline countdown;
    private int timeRemaining = 90;
    private AnchorPane mainpane;

    private ArrayList<String> wordAnswer = new ArrayList<>();
    private int userID;

    private void loadDataFromDatabase(String username) {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            //Verifies.
            String verify = "SELECT av1.word, av1.description, av1.id FROM av1 WHERE av1.description IS NOT NULL AND av1.description != '' AND av1.id IN (SELECT SavedWord.English_id FROM SavedWord WHERE SavedWord.English_id = av1.id AND SavedWord.User_id = '" + username + "') " +
                    "ORDER BY RANDOM() LIMIT 6";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            while (query.next()) {
                String word = query.getString("word");
                String description = query.getString("description");
                wordAnswer.add(query.getString("word").replaceAll("''", "\'"));
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

    /*
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

   /*
    private String getWord(String description) {
        String word = "";
        try (Connection connection = new ConnectDB().connect("dict_hh")) {
            String query = "SELECT COUNT(*) AS counter FROM av1 WHERE word = '" + description + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int count = resultSet.getInt("counter");
                if (count == 1) {
                    word = description;
                }
                else {
                    String select = "SELECT word FROM av1 WHERE description = '" + description + "'";
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
*/
   private ArrayList<String> getDescription(String text) {
       List<String> descriptions = null;
       text = text.replaceAll("\'", "''");
       descriptions = new ArrayList<>();
       try (Connection connection = new ConnectDB().connect("dict_hh")) {
           String query = "SELECT COUNT(*) AS counter FROM av1 WHERE description = '" + text + "'";
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(query);
           if (resultSet.next()) {
               int count = resultSet.getInt("counter");
               if (count >= 1) {
                   descriptions.add(text);
               }
               else {
                   String select = "SELECT description FROM av1 WHERE word = '" + text + "'";
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

    private ArrayList<String> getWord(String text) {
        List<String> word = null;
        text = text.replaceAll("\'", "''");
        word = new ArrayList<>();
        try (Connection connection = new ConnectDB().connect("dict_hh")) {
            String query = "SELECT COUNT(*) AS counter FROM av1 WHERE word = '" + text + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int count = resultSet.getInt("counter");
                if (count >= 1) {
                    word.add(text);
                }
                else {
                    String select = "SELECT word FROM av1 WHERE description = '" + text + "'";
                    ResultSet qquery = statement.executeQuery(select);
                    while (qquery.next()) {
                        word.add(qquery.getString("word"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectDB.closeConnection();
        }
        return (ArrayList<String>) word;
    }

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
                        System.out.println(getWord(firstPressedButton.getText()));
                        System.out.println(getWord(button.getText()));
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

    private ArrayList<String> getWordAnswer() {
        return wordAnswer;
    }

    private boolean isPair(JFXButton button1, JFXButton button2) {
        // Kiểm tra xem 2 nút có tạo thành cặp hay không
        //word word, word des, des word, des des
        String description1 = button1.getText();
        String description2 = button2.getText();
        ArrayList<String> word1 = getWord(description1);
        System.out.println(word1);
        ArrayList<String> word2 = getWord(description2);
        System.out.println(word2);
        if (!description1.isEmpty() && !description2.isEmpty()) {
            for (String string : word1) {
                for (String s : word2) {
                    string = string.replaceAll("''", "\'");
                    s = s.replaceAll("''", "\'");
                    if (string.equalsIgnoreCase(s)) {
                        //wordAnswer.add(string);
                        //System.out.println(description1);
                        //System.out.println(description2);
                        return true;
                    }
                }
            }
        }
        return false;
    }

/*
    private boolean isPair(JFXButton button1, JFXButton button2) {
        // Kiểm tra xem 2 nút có tạo thành cặp hay không
        String description1 = button1.getText();
        String description2 = button2.getText();
        String word1 = getWord(description1);
        String word2 = getWord(description2);
        //return description1.equals(word2) && description2.equals(word1);
        return word1.equals(word2);
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
        FXMLLoader fxmlLoader1 = new FXMLLoader(Score.class.getResource("/data/fxml/ScoreMatchGame.fxml"));
        try {
            Parent root = fxmlLoader1.load();
            Score scoreController = fxmlLoader1.getController();
            scoreController.setScore(currentscore);
            scoreController.setTime(90 - timeRemaining);
            Scene scene = new Scene(root);
            scoreController.display(scene);
            scoreController.setmainpane(mainpane);
            System.out.println(wordAnswer);
            scoreController.setWordAns(wordAnswer);
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

    public void initialize() {
        if (getUserName() == null) {
            System.out.println("Game: false");
        } else {
            System.out.println(getUserName());

            loadDataFromDatabase(getUserName());
            updateButtonText();
            scoreLabel.setText(String.valueOf(0));
            timeLabel.setText(String.valueOf(formatTime(90)));
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
    }

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String verify = "SELECT username FROM account WHERE ID = '" + userID + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);
            if (query.next()) {
                String USERNAME = query.getString("username");
                return USERNAME;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
        return null;
    }

    @FXML
    void PauseButtonOnAction(ActionEvent event) {
        if (countdown != null) {
            if (countdown.getStatus() == Timeline.Status.RUNNING) {
                countdown.pause();
                Image image = new Image(getClass().getResourceAsStream("/data/icon/pause.png"));
                pauseImage.setImage(image);
            } else {
                countdown.play();
                Image image = new Image(getClass().getResourceAsStream("/data/icon/play.png"));
                pauseImage.setImage(image);
            }
        }
    }
}
