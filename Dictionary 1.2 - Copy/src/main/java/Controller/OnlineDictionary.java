package Controller;

import Dictionary.Word;
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

public class OnlineDictionary {

    @FXML
    private Hyperlink CamHyperLink;

    @FXML
    private Hyperlink OxfordHyperLink;

    @FXML
    private JFXButton okButton;

    @FXML
    private Hyperlink uetHyperLink;
    private Word selectedWord;

    @FXML
    void handleHyperLinkClick(ActionEvent event) {
        if (event.getSource() == CamHyperLink) {
            try {
                getDesktop().browse(new URI("https://dictionary.cambridge.org/dictionary/english/" + selectedWord.toString()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == OxfordHyperLink) {
            try {
                getDesktop().browse(new URI("https://www.oxfordlearnersdictionaries.com/definition/english/" + selectedWord.toString() + "?q=" + selectedWord.toString()));
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

    public void setSelectedWord(Word selectedWord) {
        this.selectedWord = selectedWord;
    }
}
