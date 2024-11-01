package QuizGamee;

import Connect.ConnectDB;
import MatchGame.MenuMatchGame;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class QuestionController {

    Stage window = new Stage();
    @FXML
    Label question, answerLabel;

    @FXML
    Button option1;

    @FXML
    Button option2;

    @FXML
    Button option3;

    @FXML
    Button option4;

    @FXML
    private Label QuestionNum;

    @FXML
    private Label ScoreLabel;

    AnchorPane mainpane;

    public static int counter = 1;
    public static int wrong = 0;
    public static int correct = 0;
    public static Connection connection;
    public static String answer;
    private int userID;

    public QuestionController() {
        try {
            //String Url = "jdbc:sqlite:data/database/que.db";
            connection = new ConnectDB().connect("que");
            System.out.println("Kết nối thành công");
        } catch (Exception e) {
            e.printStackTrace();
            // In ra logs hoặc console để xem thông tin chi tiết về lỗi
            System.err.println("Lỗi khi thiết lập kết nối: " + e.getMessage());
        }
    }

    public String setQuesNum(int counter) {
        return "Câu " + counter + "/10";
    }

    public String setScoreNum(int correct) {
        return "Score: " + correct + "/10";
    }

    public void initialize(Scene scene) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);

        if (QuestionNum != null) {
            QuestionNum.setText(setQuesNum(1));
        } else {
            System.out.println("Don't load QuesNum.");
        }
        loadQuestions();
        /*
        option1.setOnMouseEntered(mouseEvent -> option1.setOpacity(0.70));
        option1.setOnMouseExited(mouseEvent -> option1.setOpacity(1));
        option2.setOnMouseEntered(mouseEvent -> option2.setOpacity(0.70));
        option2.setOnMouseExited(mouseEvent -> option2.setOpacity(1));
        option3.setOnMouseEntered(mouseEvent -> option3.setOpacity(0.70));
        option3.setOnMouseExited(mouseEvent -> option3.setOpacity(1));
        option4.setOnMouseEntered(mouseEvent -> option4.setOpacity(0.70));
        option4.setOnMouseExited(mouseEvent -> option4.setOpacity(1));
        */
        window.setScene(scene);
        window.showAndWait();
    }


    public void loadQuestions() {
        option1.setDisable(false);
        option2.setDisable(false);
        option3.setDisable(false);
        option4.setDisable(false);
        option1.setStyle("-fx-background-color: #fffff ");
        option2.setStyle("-fx-background-color: #fffff ");
        option3.setStyle("-fx-background-color: #fffff ");
        option4.setStyle("-fx-background-color: #fffff ");
        HashSet optSet = new HashSet();
        try {
            Random random = new Random();

            // Sinh số ngẫu nhiên từ 1 đến 1000
            int randomNumber = random.nextInt(1000) + 1;

            String str = getQuesContent(randomNumber);
            question.setText(str);
            answer = getAns(randomNumber);
            optSet.add(answer);
            do {
                int ran = random.nextInt(1000) + 1;
                optSet.add(getAns(ran));
            }
            while(optSet.size() < 4);
            optSet = shuffleSet(optSet);
            System.out.println(optSet);
            List<String> optList = new ArrayList<>(optSet);
            System.out.println(optList);
            option1.setText(optList.get(0));
            option2.setText(optList.get(1));
            option3.setText(optList.get(2));
            option4.setText(optList.get(3));


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getQuesContent(int quesID) {
        String statement = "SELECT word FROM questions WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setInt(1, quesID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("word");
                } else {
                    // Handle the case where no rows are returned for the given quesID
                    return null; // Or throw an exception or return a default value
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String getAns(int quesID) {
        String statement = "select meaning from questions where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.setInt(1, quesID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("meaning");
                } else {
                    // Handle the case where no rows are returned for the given quesID
                    return null; // Or throw an exception or return a default value
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private HashSet shuffleSet(HashSet inputSet) {
        // Chuyển Set thành List
        List list = new ArrayList<>(inputSet);

        // Xáo trộn List
        Collections.shuffle(list);

        // Chuyển List đã xáo trộn thành HashSet
        return new HashSet<>(list);
    }

    public boolean check(int counter, int x) {
        if(x == 1 && option1.getText().equals(answer)) {
            System.out.println("A la dap an dung");
            return true;
        }
        if(x == 2 && option2.getText().equals(answer)) {
            System.out.println("B la dap an dung");
            return true;
        }
        if(x == 3 && option3.getText().equals(answer)) {
            System.out.println("C la dap an dung");
            return true;
        }
        if(x == 4 && option4.getText().equals(answer)) {
            System.out.println("D la dap an dung");
            return true;
        }
        return false;
    };
    public void opt1Clicked (ActionEvent event) throws SQLException {
        option2.setDisable(true);
        option3.setDisable(true);
        option4.setDisable(true);
        if(check(counter, 1)) {
            correct++;
            TrueSound();
            ScoreLabel.setText(setScoreNum(correct));
            option1.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        } else {
            wrong++;
            NopeSound();
            option1.setStyle("-fx-background-color:#ff6666; x-fx-text-fill: white; ");
            getCorrectButton(option4);
            getCorrectButton(option2);
            getCorrectButton(option3);
        }
        if (counter == 10) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(QuizResultController.class.getResource("/data/fxml/QuizResultScene.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);

                QuizResultController quizController = fxmlLoader.getController();
                quizController.setmainpane(mainpane);
                quizController.display(scene);
                quizController.setUserID(userID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            System.out.println("Câu " + counter);
            QuestionNum.setText(setQuesNum(counter));
            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
            pause.setOnFinished(e -> {
                loadQuestions();
            });
            pause.play();

            ///loadQuestions();
        }
        System.out.println(correct);
        System.out.println(wrong);
    }
    public void opt2Clicked (ActionEvent event) throws SQLException {
        option1.setDisable(true);
        option3.setDisable(true);
        option4.setDisable(true);
        if(check(counter, 2)) {
            correct++;
            TrueSound();
            ScoreLabel.setText(setScoreNum(correct));
            option2.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        } else {
            wrong++;
            NopeSound();
            option2.setStyle("-fx-background-color:#ff6666; x-fx-text-fill: white; ");
            getCorrectButton(option1);
            getCorrectButton(option4);
            getCorrectButton(option3);
        }
        if (counter == 10) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(QuizResultController.class.getResource("/data/fxml/QuizResultScene.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);

                QuizResultController quizController = fxmlLoader.getController();
                quizController.setmainpane(mainpane);
                quizController.display(scene);
                quizController.setUserID(userID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            System.out.println("Câu " + counter);
            QuestionNum.setText(setQuesNum(counter));
            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
            pause.setOnFinished(e -> {
                loadQuestions();
            });
            pause.play();
        }
        System.out.println(correct);
        System.out.println(wrong);
    }
    public void opt3Clicked (ActionEvent event) throws SQLException {
        option2.setDisable(true);
        option1.setDisable(true);
        option4.setDisable(true);
        if(check(counter, 3)) {
            correct++;
            TrueSound();
            ScoreLabel.setText(setScoreNum(correct));
            option3.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        } else {
            wrong++;
            NopeSound();
            option3.setStyle("-fx-background-color:#ff6666; x-fx-text-fill: white; ");
            getCorrectButton(option1);
            getCorrectButton(option2);
            getCorrectButton(option4);
        }
        if (counter == 10) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(QuizResultController.class.getResource("/data/fxml/QuizResultScene.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);

                QuizResultController quizController = fxmlLoader.getController();
                quizController.setmainpane(mainpane);
                quizController.display(scene);
                quizController.setUserID(userID);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            counter++;
            System.out.println("Câu " + counter);
            QuestionNum.setText(setQuesNum(counter));
            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
            pause.setOnFinished(e -> {
                loadQuestions();
            });
            pause.play();

        }
        System.out.println(correct);
        System.out.println(wrong);
    }
    public void opt4Clicked (ActionEvent event) throws SQLException {
        option2.setDisable(true);
        option3.setDisable(true);
        option1.setDisable(true);
        if (check(counter, 4)) {
            correct++;
            TrueSound();
            ScoreLabel.setText(setScoreNum(correct));
            option4.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        } else {
            wrong++;
            NopeSound();
            option4.setStyle("-fx-background-color:#ff6666; x-fx-text-fill: white; ");
            getCorrectButton(option1);
            getCorrectButton(option2);
            getCorrectButton(option3);
        }
        if (counter == 10) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(QuizResultController.class.getResource("/data/fxml/QuizResultScene.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);

                QuizResultController quizController = fxmlLoader.getController();
                quizController.setmainpane(mainpane);
                quizController.display(scene);
                quizController.setUserID(userID);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            counter++;
            System.out.println("Câu " + counter);
            QuestionNum.setText(setQuesNum(counter));
            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
            pause.setOnFinished(e -> {
                loadQuestions();
            });
            pause.play();
        }
        System.out.println(correct);
        System.out.println(wrong);
    }

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }

    public static void setCorrect(int correct) {
        QuestionController.correct = correct;
    }

    public static void setWrong(int wrong) {
        QuestionController.wrong = wrong;
    }

    public static void setCounter(int counter) {
        QuestionController.counter = counter;
    }

    public void getCorrectButton(Button option) {
        if(option.getText().equals(answer)) {
            option.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        }
        else {
            return;
        }
    }

    private MediaPlayer mediaPlayer;

    private void TrueSound() {
        Media sound = new Media(getClass().getResource("/data/audio/yes.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    private void NopeSound() {
        Media sound = new Media(getClass().getResource("/data/audio/Nope.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @FXML
    void returnButtonOnAction(ActionEvent event) {
        window.close();
        correct = 0;
        wrong = 0;
        counter = 1;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(QuizStartController.class.getResource("/data/fxml/QuizstartScene.fxml"));
            AnchorPane Matchpane = fxmlLoader.load();
            mainpane.getChildren().setAll(Matchpane);

            QuizStartController MenuController = fxmlLoader.getController();
            //userController.userLogin();
            MenuController.setmainpane(mainpane);
            MenuController.setUserID(userID);
            //userController.setStage(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }
}