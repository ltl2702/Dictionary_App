package QuizGamee;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    @FXML
    void randomQuestion() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:tu.db");
        String query = "SELECT word FROM questions ORDER BY RANDOM() LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            String questionText = resultSet.getString("meaning");
            question.setText(questionText);
        }
    }

}
