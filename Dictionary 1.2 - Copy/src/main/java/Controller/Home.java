package Controller;

import cmd.DictionaryManagement;
import cmd.Word;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    private final String datatable = "av";

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;

    @FXML
    private Button UKspeakerButton;

    @FXML
    private Button searchButton;
    
    @FXML
    private Button USspeakerButton;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXButton gameButton;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton translateButton;

    @FXML
    private JFXButton userButton;

    @FXML
    private ImageView homeImage;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView searchImage;

    @FXML
    private ListView<Word> listResult;

    @FXML
    private WebView webView; // New WebView

    private ObservableList<Word> list = FXCollections.observableArrayList();
    private WebEngine webEngine;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });

        webEngine = webView.getEngine();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            performSearch(newValue);
        });

        list = DictionaryManagement.dbSearchWord("'a%'", datatable);
        listResult.setItems(list);

        listResult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("NOOOOOOO");
            } else {
                showHtmlContent(newValue.getHtml());
            }
        });
    }

    private void showHtmlContent(String htmlContent) {
        webEngine.loadContent(htmlContent);
    }

    /**
     * onActionSearchBtn.
     */
    public void onActionSearchBtn() {
        performSearch(searchField.getText());
    }

    private void performSearch(String searchTerm) {
        list = DictionaryManagement.dbSearchWord("'" + searchTerm.toLowerCase().trim() + "a%'", datatable);
        listResult.setItems(list);

        if (!list.isEmpty()) {
            Word firstWord = list.get(0);
            showHtmlContent(firstWord.getHtml());
        }
    }


    /**
     * onMouseClickListView.
     */
    public void onMouseClickListView() {
        Word selectedWord = listResult.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            showHtmlContent(selectedWord.getHtml());
        }
    }

    @FXML
    void searchFieldOnAction(ActionEvent event) {
    }

    @FXML
    void addButtonOnAction(ActionEvent event) {
    }

    @FXML
    void editButtonOnAction(ActionEvent event) {
    }

    @FXML
    void eraseButtonOnAction(ActionEvent event) {
    }

    @FXML
    void gameButtonOnAction(ActionEvent event) {
    }

    @FXML
    void translateButtonOnAction(ActionEvent event) {
    }

    @FXML
    void userButtonOnAction(ActionEvent event) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
