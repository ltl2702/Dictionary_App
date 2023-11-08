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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Signup implements Initializable {

    @FXML
    private TextField firstnamefill;

    @FXML
    private Label firstnamelabel;

    @FXML
    private Label invalidLabel;

    @FXML
    private TextField lastnamefill;

    @FXML
    private Label lastnamelabel;

    @FXML
    private PasswordField passwordfill;

    @FXML
    private Label passwordlabel;

    @FXML
    private Button signupbutton;

    @FXML
    private Label userlabel;

    @FXML
    private TextField usernamefill;

    @FXML
    private ImageView welcomeimageView;

    @FXML
    private ProgressBar loading;

    private Timeline timeline;

    /*
    @FXML
    private AnchorPane mainpane;
     */

    private Stage stage;

    @FXML
    void signupButtonOnAction(ActionEvent event) {
        /*
            try (Connection connectDatabase = new UserInfo().connect()) {
                //Verifies login.
                String verify = "SELECT COUNT(*) AS counter" +
                        " FROM account WHERE username = '" + usernamefill.getText() + "'";
                Statement statement = connectDatabase.createStatement();
                ResultSet query = statement.executeQuery(verify);

                if (query.next()) {
                    int count = query.getInt("counter");
                    if (count == 1)
                        invalidLabel.setText("Account already exists. Please try again.");
                    else {
                        signup();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            } finally {
                UserInfo.closeConnection();
            }
         */
        if (!usernamefill.getText().isBlank() && !passwordfill.getText().isBlank() && !firstnamefill.getText().isBlank() && !lastnamefill.getText().isBlank())
            signup();
        else
            invalidLabel.setText("Please enter your information.");
    }
        /*
        finally {
                try {
                    connectDatabase.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ex.getCause();
                }
            }
         */

    public void signup() {
        try (Connection connectDatabase = new UserInfo().connect()) {
            String first = firstnamefill.getText();
            String last = lastnamefill.getText();
            String user = usernamefill.getText();
            String pass = passwordfill.getText();

            //Verifies login.
            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM account WHERE username = '" + usernamefill.getText() + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                int count = query.getInt("counter");
                if (count == 1)
                    invalidLabel.setText("Account already exists. Please try again.");
                else {
                    String add = "INSERT INTO account(firstname, lastname, username, password) VALUES ('"
                            + first + "','" + last + "','" + user + "','" + pass + "')";
                    statement.executeUpdate(add);
                    invalidLabel.setText("User has been registered successfully!");
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("home2.fxml"));
                        //AnchorPane loadingpane = fxmlLoader.load();
                        Parent root = fxmlLoader.load();
                        Home homeController = fxmlLoader.getController();
                        homeController.setStage(stage);

                        Scene scene = new Scene(root, 893, 600);
                        loading(scene);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ex.getCause();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            UserInfo.closeConnection();
        }
    }

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

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
