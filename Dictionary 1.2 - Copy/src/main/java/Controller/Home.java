package Controller;

import cmd.DictionaryManagement;
import cmd.Word;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private Label Menu, MenuClose;

    @FXML
    private WebView webView;
    private WebEngine webEngine;
    private Stage stage;

    @FXML
    private Label InvalidWord;

    @FXML
    private AnchorPane slider;

    @FXML
    private Button UKspeakerButton, searchButton, USspeakerButton;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Word> listResult;

    private ObservableList<Word> list = FXCollections.observableArrayList();

    // Initialize the Trie with your word data
    private DictionaryManagement.TrieNode trie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the Trie with your word data
        trie = DictionaryManagement.buildTrie(datatable);

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> toggleSlider(true));
        MenuClose.setOnMouseClicked(event -> toggleSlider(false));

        webEngine = webView.getEngine();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> performSearch(newValue));

        listResult.setCellFactory(param -> new ListCell<Word>() {
            @Override
            protected void updateItem(Word item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getWordTarget() == null) {
                    setText(null);
                } else {
                    setText(item.getWordTarget());
                }
            }
        });

        listResult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue) && !list.isEmpty()) {
                showHtmlContent(newValue);
            }
        });
    }

    private void toggleSlider(boolean open) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);

        if (open) {
            slide.setToX(0);
            slide.setOnFinished((ActionEvent e) -> MenuClose.setVisible(true));
        } else {
            slide.setToX(-176);
            slide.setOnFinished((ActionEvent e) -> Menu.setVisible(true));
        }

        slide.play();
    }

    private void showHtmlContent(Word word) {
        String cautionHtml = """
            <html>
                <head>
                    <style>
                        body { font-family: 'Arial', sans-serif; background-color: #f8f8f8; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }
                        aside {; color: #ff0000; font-family: Roboto, sans-serif; font-size: 18px; padding: 12px 24px; text-align: center; }
                    </style>
                </head>
                <body>
                    <aside class='caution'>
                        <strong>Caution:</strong><br>
                        &nbsp; The word you are looking for is not in the dictionary.
                        <br>   Please try again or add a new word.
                    </aside>
                </body>
            </html>
            """;
        if (word != null) {
            String htmlContent = DictionaryManagement.searchWordInTrie(trie, word.getWordTarget());

            if (htmlContent != null && !htmlContent.isEmpty()) {
                webEngine.loadContent(htmlContent);
                if (InvalidWord != null) InvalidWord.setVisible(false);
            } else {
                webEngine.loadContent(cautionHtml != null && !cautionHtml.isEmpty() ? cautionHtml : "");
                if (InvalidWord != null) InvalidWord.setVisible(true);
            }

            // No need to set webView.setVisible(true) here
        } else {
            // If word is null, handle accordingly (e.g., set webView invisible)
            if (InvalidWord != null) InvalidWord.setVisible(false);
            if (webView != null) webView.setVisible(false);
        }
    }

    private void performSearch(String searchTerm) {
        searchTerm = searchTerm.toLowerCase().trim();

        list = FXCollections.observableArrayList();

        if (!searchTerm.isEmpty()) {
            // Trie-based search
            list = DictionaryManagement.searchWordsInTrie(trie, searchTerm);

            // Database search
            list.addAll(DictionaryManagement.dbSearchWord(searchTerm, datatable));
        }
        listResult.setItems(list);
        if (!list.isEmpty()) {
            Word firstWord = list.get(0);
            showHtmlContent(firstWord);
        } else {
            showHtmlContent(new Word(null, null)); // Passing null to show caution HTML
        }
    }

    @FXML
    void searchFieldOnAction(ActionEvent event) {
        String searchTerm = searchField.getText();
        performSearch(searchTerm);
    }

    @FXML
    void addButtonOnAction(ActionEvent event) {
        // Handle add button action...
    }

    @FXML
    void editButtonOnAction(ActionEvent event) {
        // Handle edit button action...
    }

    @FXML
    void eraseButtonOnAction(ActionEvent event) {
        // Handle erase button action...
    }

    @FXML
    void gameButtonOnAction(ActionEvent event) {
        // Handle game button action...
    }

    @FXML
    void translateButtonOnAction(ActionEvent event) {
        // Handle translate button action...
    }

    @FXML
    void userButtonOnAction(ActionEvent event) {
        // Handle user button action...
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
