package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.web.HTMLEditor;

public class EditDefController {
    @FXML
    AnchorPane mainpane;
    @FXML
    HTMLEditor htmlEditor;
    @FXML
    Button discardButton, submitButton;

    public void setmainpane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }

    public void discardChanges(ActionEvent event) {

    }

    public void submitEdition(ActionEvent event) {

    }
}
