package Controller;


import Connect.ConnectDB;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;


public class Welcome implements Initializable {

    private Stage stage;

    @FXML
    private Label invalidLabel;

    @FXML
    private PasswordField passwordfill;

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

    public String getUsernamefill() {
        return usernamefill.getText();
    }

    public void setUsernamefill(TextField usernamefill) {
        this.usernamefill = usernamefill;
    }

    public boolean check = false;

    public boolean isCheck() {
        return check;
    }

    @FXML
    public void loginButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!usernamefill.getText().isBlank() && !passwordfill.getText().isBlank()) {
            try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
                //Verifies login.
                String verify = "SELECT username, password FROM account WHERE username = '" + usernamefill.getText() +
                        "' AND password = '" + passwordfill.getText() + "'";

                try {
                    Statement statement = connectDatabase.createStatement();
                    ResultSet query = statement.executeQuery(verify);

                    if (!query.next())
                        invalidLabel.setText("Invalid Login. Please try again.");
                    else {
                        invalidLabel.setText("Congratulations!!!");
                        check = true;
                        //setUsernamefill(usernamefill);
                        System.out.println(getUsernamefill());
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/data/fxml/wtf.fxml"));

                            Parent root = fxmlLoader.load();
                            Home homeController = fxmlLoader.getController();
                            homeController.setStage(stage);
                            homeController.setUsername(usernamefill);
                            homeController.setCheckLogin(check);

                            Scene scene = new Scene(root);
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
                        ConnectDB.closeConnection();
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Signup.class.getResource("/data/fxml/signup.fxml"));

            //AnchorPane signuppane = fxmlLoader.load();
            //mainpane.getChildren().setAll(signuppane);
            Parent root = fxmlLoader.load();
            Signup signupController = fxmlLoader.getController();
            signupController.setStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    private void loading(Scene scene) {
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            loading.setOpacity(1);
            loading.setProgress(loading.getProgress() + 0.25);
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

    public void initializeStage(Stage window) {
        this.stage = window;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
