package Controller;

import API.TextToSpeechFreetts;
import Dictionary.DictionaryManagement;
import Dictionary.Word;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Home implements Initializable {
    private final String datatable = "av1";
    public Button speakerButton, searchButton;
    public JFXButton gameButton;
    public ImageView menuimage1;
    public JFXButton translateButton, editButton, homeButton, userButton;
    public AnchorPane menuPane;
    public ImageView searchImage, homeImage;
    private Stage stage;

    @FXML
    private AnchorPane homePane, slider;

    @FXML
    private JFXButton Menu, MenuClose;

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Word> listResult;

    private ObservableList<Word> list = FXCollections.observableArrayList();
    private boolean checklogin, checksignup;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        slider.setTranslateX(-400);

        // Animation duration
        Duration animationDuration = Duration.millis(900);

        Menu.setOnMouseClicked(event -> animateMenu(0, animationDuration));
        MenuClose.setOnMouseClicked(event -> animateMenu(-400, animationDuration));

        webEngine = webView.getEngine();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            performSearch(newValue);
        });

        list = DictionaryManagement.dbSearchWord("%", datatable);
        listResult.setItems(list);
        listResult.setVisible(true);

        listResult.setOnMouseClicked(event -> {
            Word selectedWord = listResult.getSelectionModel().getSelectedItem();
            if (selectedWord != null && selectedWord != Word.NOT_FOUND) {
                updateSearchField(selectedWord);
                showHtmlContent(selectedWord.getHtml());
            }
        });
    }
    private void closeMenu() {
        animateMenu(-400, Duration.millis(900));
    }

    private void animateMenu(double toX, Duration duration) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(duration);
        slide.setNode(slider);

        slide.setToX(toX);
        slide.play();

        slider.setTranslateX(-toX);

        slide.setOnFinished((ActionEvent e) -> {
            Menu.setVisible(toX != 0);
            MenuClose.setVisible(toX == 0);
        });
    }

    private void showHtmlContent(String htmlContent) {
        Platform.runLater(() -> {
            if (htmlContent != null) {
                webEngine.loadContent(htmlContent);
            } else {
                webEngine.loadContent("");
            }
        });
    }

    private ExecutorService threadPool = Executors.newCachedThreadPool();

    private void performSearchInBackground(String searchTerm) {
        threadPool.submit(() -> {
            try {
                ObservableList<Word> result = DictionaryManagement.dbSearchWord(searchTerm.toLowerCase(), datatable);
                Platform.runLater(() -> updateUI(result, searchTerm));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void updateUI(ObservableList<Word> result, String searchTerm) {
        if (result.isEmpty() || searchTerm.trim().isEmpty()) {
            // Display "Not found" in the listResult
            listResult.setItems(FXCollections.observableArrayList(Word.NOT_FOUND));
            listResult.setVisible(true);
        } else {
            list = result;
            listResult.setItems(list);
            listResult.setVisible(true);
        }

        Word selectedWord = listResult.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            updateSearchField(selectedWord);
            showHtmlContent(selectedWord.getHtml());
        }
    }

    private void performSearch(String searchTerm) {
        performSearchInBackground(searchTerm);
    }

    private void updateSearchField(Word selectedWord) {
        Platform.runLater(() -> {
            searchField.setText(selectedWord.getWordTarget());
        });
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        if (!list.isEmpty()) {
            Word firstResult = list.get(0);
            showHtmlContent(firstResult.getHtml());
        }

        // Khi hiển thị definition, enable speakerButton
        speakerButton.setDisable(false);
    }

    private ExecutorService fxmlLoaderThreadPool = Executors.newCachedThreadPool();

    private void loadFXMLInBackground(String resource) {
        fxmlLoaderThreadPool.submit(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource(resource));
                AnchorPane pane = fxmlLoader.load();
                Platform.runLater(() -> homePane.getChildren().setAll(pane));
                closeMenu();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/data/fxml/wtf.fxml"));
            AnchorPane homepane2 = fxmlLoader.load();
            homePane.getChildren().setAll(homepane2);
            closeMenu();
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }
    @FXML
    void editButtonOnAction(ActionEvent event) {
        loadFXMLInBackground("/data/fxml/edit2.fxml");
    }

    @FXML
    void gameButtonOnAction(ActionEvent event) {
        loadFXMLInBackground("/data/fxml/gameController.fxml");
    }

    @FXML
    void translateButtonOnAction(ActionEvent event) {
        loadFXMLInBackground("/data/fxml/translate.fxml");
    }

    private TextField username;
    @FXML
    void userButtonOnAction(ActionEvent event) {
        System.out.println("Home check login: " + checklogin);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Edit.class.getResource("/data/fxml/user.fxml"));
            AnchorPane userpane = fxmlLoader.load();

            homePane.getChildren().setAll(userpane);
            closeMenu();

            User userController = fxmlLoader.getController();
            if(checklogin) {
                userController.setUsernameLogin(username);
                userController.setCheckLogin(checklogin);
            }
            else if(checksignup) {
                userController.setUsernameSignup(username);
                userController.setCheckSignup(checksignup);
            }
            userController.userLogin();
            userController.setmainpane(homePane);
            userController.setStage(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUsernameLogin(TextField usernamefill) {
        this.username = usernamefill;
    }

    public void setCheckLogin(boolean check) {
        this.checklogin = check;
    }

    public void setUsernameSignup(TextField usernamefill) {
        this.username = usernamefill;
    }

    public void setCheckSignup(boolean check) {
        this.checksignup = check;
    }

    public void speakClick(ActionEvent actionEvent) {
        ObservableList<Word> items = listResult.getItems();
        if (!items.isEmpty()) {
            Word selectedWord = listResult.getSelectionModel().getSelectedItem();
            if (selectedWord != null && selectedWord != Word.NOT_FOUND) {
                TextToSpeechFreetts.convertTextToSpeech(selectedWord.getWordTarget());
            } else if (items.get(0) != Word.NOT_FOUND) {
                selectedWord = items.get(0);
                TextToSpeechFreetts.convertTextToSpeech(selectedWord.getWordTarget());
            }
        }
    }
}

