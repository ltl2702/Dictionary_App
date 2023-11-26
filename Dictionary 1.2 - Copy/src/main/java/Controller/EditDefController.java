package Controller;

import Dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;

public class EditDefController {
    @FXML
    private AnchorPane mainpane;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Button discardButton, submitButton;

    private Word selectedWord;


    public void setSelectedWord(Word word) {
        this.selectedWord = word;
    }

    public void setMainPane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }

    public void setHtmlContent(String htmlContent) {
        if (htmlContent != null) {
            htmlEditor.setHtmlText(htmlContent);
        } else {
            htmlEditor.setHtmlText("");
        }
    }

    public void discardChanges(ActionEvent actionEvent) {
    }

    public void submitEdition(ActionEvent actionEvent) {
    }

}
