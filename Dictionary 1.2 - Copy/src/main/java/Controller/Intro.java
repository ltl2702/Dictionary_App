package Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Intro {

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton prevButton;

    @FXML
    private ImageView image;

    private int currentIndex = 1;
    private int userID;
    private Stage stage;

    @FXML
    void nextButtonOnAction(ActionEvent event) {
        if (currentIndex < 2) {
            currentIndex++;
            updateImage();
        }
    }

    @FXML
    void prevButtonOnAction(ActionEvent event) {
        if (currentIndex > 1) {
            currentIndex--;
            updateImage();
        }
    }

    private void updateImage() {
        String imagePath = "/data/image/intro" + currentIndex + ".png";
        Image newImage = new Image(getClass().getResourceAsStream(imagePath));
        image.setImage(newImage);
    }

    @FXML
    void skipButtonOnAction(ActionEvent event) {
        event.consume();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/data/fxml/home.fxml"));

            Parent root = fxmlLoader.load();
            Home homeController = fxmlLoader.getController();
            homeController.setStage(stage);
            //homeController.setUsername(usernamefill);
            homeController.setUserID(userID);
            homeController.userLogin();

            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        currentIndex = 1;
        String imagePath = "/data/image/intro" + currentIndex + ".png";
        Image newImage = new Image(getClass().getResourceAsStream(imagePath));
        image.setImage(newImage);
    }
}
