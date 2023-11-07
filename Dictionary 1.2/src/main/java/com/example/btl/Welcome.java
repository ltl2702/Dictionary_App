package com.example.btl;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Welcome implements Initializable {

    @FXML
    private Label invalidLabel;

    @FXML
    private Label signupLabel;

    @FXML
    private Button loginbutton;

    @FXML
    private Button signupbutton;

    @FXML
    private PasswordField passwordfill;

    @FXML
    private Label passwordlabel;

    @FXML
    private Label userlabel;

    @FXML
    private TextField usernamefill;

    @FXML
    private ImageView welcomeimageView;

    @FXML
    public void loginButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!usernamefill.getText().isBlank() && !passwordfill.getText().isBlank()) {
            try (Connection connectDatabase = new UserInfo().connect()) {
                //Verifies login.
                String verify = "SELECT username, password" +
                        " FROM account WHERE username = '" + usernamefill.getText() +
                        "' AND password = '" + passwordfill.getText() + "'";

                try {
                    Statement statement = connectDatabase.createStatement();
                    ResultSet query = statement.executeQuery(verify);

                    if (!query.next())
                        invalidLabel.setText("Invalid Login. Please try again.");
                    else
                        invalidLabel.setText("Congratulations!!!");
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getCause();
                } finally {
                    try {
                        UserInfo.closeConnection();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ex.getCause();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
        } else {
            invalidLabel.setText("Please enter username and password.");
        }
    }

    @FXML
    private AnchorPane mainpane;

    @FXML
    public void signupButtonOnAction(ActionEvent event) {
        signupbutton.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Signup.class.getResource("signup.fxml"));
                AnchorPane signuppane = fxmlLoader.load();
                mainpane.getChildren().setAll(signuppane);
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
        });
    }

/*
    public void validLogin() throws SQLException, ClassNotFoundException {

        UserInfo connection = new UserInfo();
        Connection connectDatabase = connection.getConnection();

        //Verifies login.
        String verify = "SELECT count(1) FROM account WHERE username = '" + usernamefill.getText() + "' AND password = '" + passwordfill.getText() +  "'";
        try {
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            while (query.next()) {
                if (query.getInt(1) == 1) {
                    invalidLabel.setText("Congratulations!!!");
                }
                else {
                    invalidLabel.setText("Invalid Login. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        UserInfo connection = new UserInfo();
        Connection connectDatabase = connection.connect();
        //Verifies login.
        String verify = "SELECT username, password" +
                " FROM account WHERE username = '" + usernamefill.getText() +
                "' AND password = '" + passwordfill.getText() + "'";

        try {
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (!query.next())
                invalidLabel.setText("Invalid Login. Please try again.");
            else
                invalidLabel.setText("Congratulations!!!");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            try {
                connectDatabase.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
        }
    }
*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File welcomeFile = new File("src/main/resources/com/example/btl/image/background.png");
        Image welcomeImage = new Image(welcomeFile.toURI().toString());
        welcomeimageView.setImage(welcomeImage);
    }
}
