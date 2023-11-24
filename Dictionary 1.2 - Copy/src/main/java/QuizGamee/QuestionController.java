package QuizGamee;

import Connect.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class QuestionController {
    @FXML
    Label question;
    @FXML
    Button option1;
    @FXML
    Button option2;
    @FXML
    Button option3;
    @FXML
    Button option4;
    @FXML
    Button next;
    Scene scene;
    private AnchorPane mainpane;

    @FXML
    void randomQuestion() {
        try (Connection connectDatabase = new ConnectDB().connect("tu")) {
            String query = "SELECT word FROM questions ORDER BY RANDOM() LIMIT 1";
            Statement statement = connectDatabase.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String questionText = resultSet.getString("meaning");
                question.setText(questionText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }
}
