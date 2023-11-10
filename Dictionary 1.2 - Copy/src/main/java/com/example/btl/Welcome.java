package com.example.btl;


import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Welcome implements Initializable {

    private Stage stage;

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
    private ProgressBar loading;

    private Timeline timeline;

    @FXML
    private ImageView welcomeimageView;

    @FXML
    private AnchorPane mainpane;

    public AnchorPane getMainpane() {
        return mainpane;
    }

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
                    else {
                        invalidLabel.setText("Congratulations!!!");
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("home2.fxml"));
                            //Cách 1:
                            //AnchorPane loadingpane = fxmlLoader.load();
                            //loading(mainpane, loadingpane);
                            //Cách 2:
                            Parent root = fxmlLoader.load();
                            Home homeController = fxmlLoader.getController();
                            homeController.setStage(stage);

                            Scene scene = new Scene(root, 893, 600);
                            //stage.setScene(scene);
                            loading(scene);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            ex.getCause();
                        }
                    }
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
    public void signupButtonOnAction(ActionEvent event) {
        signupbutton.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Signup.class.getResource("signup.fxml"));

                //AnchorPane signuppane = fxmlLoader.load();
                //mainpane.getChildren().setAll(signuppane);
                Parent root = fxmlLoader.load();
                Signup signupController = fxmlLoader.getController();
                signupController.setStage(stage);

                Scene scene = new Scene(root, 893, 540);
                stage.setScene(scene);
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
    private void loading(Scene scene) {
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            loading.setOpacity(1);
            loading.setProgress(loading.getProgress() + 0.15);
            if (loading.getProgress() >= 1.0) {
                timeline.stop();
                //Pauses 1 second.
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                //pause.setOnFinished(event -> mainpane.getChildren().setAll(loadingpane));
                pause.setOnFinished(event -> stage.setScene(scene));
                pause.play();
            }
        }));
        //Set cycle of timeline
        timeline.setCycleCount(-1);
        timeline.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File welcomeFile = new File("src/main/resources/com/example/btl/image/background.png");
        Image welcomeImage = new Image(welcomeFile.toURI().toString());
        welcomeimageView.setImage(welcomeImage);
    }

    public void initializeStage(Stage window) {
        this.stage = window;
    }
}