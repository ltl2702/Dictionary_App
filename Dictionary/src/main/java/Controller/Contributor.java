package Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import static java.awt.Desktop.getDesktop;

public class Contributor {

    @FXML
    private Hyperlink ltlHyperLink;

    @FXML
    private Hyperlink ntttHyperLink;

    @FXML
    private JFXButton okButton;

    @FXML
    private Hyperlink uetHyperLink;

    @FXML
    private Hyperlink vtqHyperLink;
    private int userID;

    @FXML
    void handleHyperLinkClick(ActionEvent event) {
        if (event.getSource() == ntttHyperLink) {
            try {
                getDesktop().browse(new URI("https://github.com/nttt2004"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == ltlHyperLink) {
            try {
                getDesktop().browse(new URI("https://github.com/ltl2702"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == vtqHyperLink) {
            try {
                getDesktop().browse(new URI("https://github.com/vtq0611"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == uetHyperLink) {
            try {
                getDesktop().browse(new URI("https://uet.vnu.edu.vn/"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void display(Scene scene) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        okButton.setOnAction(e -> window.close());
        window.setScene(scene);
        //Displays window and waits for it to be closed before returning.
        window.showAndWait();
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
