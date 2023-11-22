package Connect;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Alerter {
    //Stage window = new Stage();

    @FXML
    private Label alertLabel;

    @FXML
    private ImageView alertImage;

    @FXML
    private JFXButton okButton;

    public void display(String label, String url, Scene scene) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        alertLabel.setText(label);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(url)));
        alertImage.setImage(image);
        okButton.setOnAction(e -> window.close());
        window.setScene(scene);
        //Displays window and waits for it to be closed before returning.
        window.showAndWait();
    }
}
