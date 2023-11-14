package Controller;

import Connect.ConnectDB;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Avatar {

    @FXML
    private JFXButton user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12, user13, user14, user15, user16;

    @FXML
    private Label avtLabel;

    private TextField username;
    @FXML
    private ImageView userimage;
    private Stage window;

    void display() {
        System.out.println(username.getText());
        user1.setOnAction(e -> {
            action(1);
            window.close();
        });
        user2.setOnAction(e -> {
            action(2);
            window.close();
        });
        user3.setOnAction(e -> {
            action(3);
            window.close();
        });
        user4.setOnAction(e -> {
            action(4);
            window.close();
        });
        user5.setOnAction(e -> {
            action(5);
            window.close();
        });
        user6.setOnAction(e -> {
            action(6);
            window.close();
        });
        user7.setOnAction(e -> {
            action(7);
            window.close();
        });
        user8.setOnAction(e -> {
            action(8);
            window.close();
        });
        user9.setOnAction(e -> {
            action(9);
            window.close();
        });
        user10.setOnAction(e -> {
            action(10);
            window.close();
        });
        user11.setOnAction(e -> {
            action(11);
            window.close();
        });
        user12.setOnAction(e -> {
            action(12);
            window.close();
        });
        user13.setOnAction(e -> {
            action(13);
            window.close();
        });
        user14.setOnAction(e -> {
            action(14);
            window.close();
        });
        user15.setOnAction(e -> {
            action(15);
            window.close();
        });
        user16.setOnAction(e -> {
            action(16);
            window.close();
        });
    }

    public void action(int imageNum) {
        try (Connection connectDatabase = new ConnectDB().connect("userinfo")) {
            String userName = username.getText();
            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM account WHERE username = '" + userName + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);
            if (query.next()) {
                String getID = "SELECT ID FROM account WHERE username = '" + userName + "'";
                ResultSet IDquery = statement.executeQuery(getID);
                if (IDquery.next()) {
                    int id = IDquery.getInt("id");
                    String update = "UPDATE account SET image = '" + imageNum + "' WHERE id = '" + id + "'";
                    statement.executeUpdate(update);
                    avtLabel.setText("You have successfully changed your profile picture.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
    }

    public void setusername(TextField username) {
        this.username = username;
    }

    public void setStage(Stage window) {
        this.window = window;
    }
}
