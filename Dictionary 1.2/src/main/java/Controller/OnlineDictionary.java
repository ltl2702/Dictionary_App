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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
    private int userID;

    @FXML
    void handleHyperLinkClick(ActionEvent event) {
        if (event.getSource() == CamHyperLink) {
            try {
                String encodedWord = URLEncoder.encode(selectedWord.toString(), StandardCharsets.UTF_8);
                getDesktop().browse(new URI("https://dictionary.cambridge.org/dictionary/english/" + encodedWord.replace("+", "-")));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == OxfordHyperLink) {
            try {
                String encodedWord = URLEncoder.encode(selectedWord.toString(), StandardCharsets.UTF_8);
                getDesktop().browse(new URI("https://www.oxfordlearnersdictionaries.com/definition/english/" + encodedWord.replace("+", "-") + "?q=" + encodedWord));
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

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
