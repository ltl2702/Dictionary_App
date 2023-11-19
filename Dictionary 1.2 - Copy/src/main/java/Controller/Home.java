package Controller;

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
    private final String datatable = "av";
    private Stage stage;
    private String word;
    @FXML
    private AnchorPane homePane, slider;

    @FXML
    private JFXButton editButton, homeButton, userButton;

    @FXML
    private Button UKspeakerButton, USspeakerButton;

    @FXML
    private Label Menu, MenuClose;

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
        slider.setTranslateX(-176);

        // Animation duration
        Duration animationDuration = Duration.millis(300);

        Menu.setOnMouseClicked(event -> animateMenu(0, animationDuration));
        MenuClose.setOnMouseClicked(event -> animateMenu(-176, animationDuration));

        webEngine = webView.getEngine();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            performSearch(newValue);
        });

        list = DictionaryManagement.dbSearchWord("''", datatable);
        listResult.setItems(list);
        listResult.setVisible(false);

        listResult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showHtmlContent(newValue.getHtml());
            }
        });
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
                // If htmlContent is null, clear the WebView
                webEngine.loadContent("");
            }
        });
    }

    private ExecutorService threadPool = Executors.newFixedThreadPool(2);

    private void performSearchInBackground(String searchTerm) {
        threadPool.submit(() -> {
            try {
                ObservableList<Word> result = DictionaryManagement.dbSearchWord("'" + searchTerm.toLowerCase().trim() + "%'", datatable);

                Platform.runLater(() -> updateUI(result, searchTerm));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void updateUI(ObservableList<Word> result, String searchTerm) {
        list = result;
        listResult.setItems(list);

        if (!list.isEmpty() && !searchTerm.trim().isEmpty()) {
            listResult.setVisible(true);
        } else {
            // Display "No result" in the listResult
            listResult.setItems(FXCollections.observableArrayList(Word.NO_RESULT));
            listResult.setVisible(true);
        }
    }


    private void performSearch(String searchTerm) {
        performSearchInBackground(searchTerm);
    }

    @FXML
    void searchFieldOnAction(ActionEvent event) {
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/data/fxml/homecopy.fxml"));
                AnchorPane homepane2 = fxmlLoader.load();
                homePane.getChildren().setAll(homepane2);
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
    }

    @FXML
    void editButtonOnAction(ActionEvent event) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Edit.class.getResource("/data/fxml/edit2.fxml"));
                AnchorPane editpane = fxmlLoader.load();
                homePane.getChildren().setAll(editpane);
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
    }

    @FXML
    void gameButtonOnAction(ActionEvent event) {
        // Handle game button action...
    }

    @FXML
    void translateButtonOnAction(ActionEvent event) {
        // Handle translate button action...
    }

    private TextField username;
    @FXML
    void userButtonOnAction(ActionEvent event) {
            System.out.println("Home check login: " + checklogin);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Edit.class.getResource("/data/fxml/user.fxml"));
                AnchorPane userpane = fxmlLoader.load();
                homePane.getChildren().setAll(userpane);

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

    public void speakClick(ActionEvent actionEvent, String voiceType) {
        Word selectedWord = listResult.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            if ("UK".equals(voiceType)) {
                DictionaryManagement.textToSpeechUK(selectedWord.getWordTarget());
            } else if ("US".equals(voiceType)) {
                DictionaryManagement.textToSpeechUS(selectedWord.getWordTarget());
            }
        }
    }

    public void UKspeakClick(ActionEvent actionEvent) {
        speakClick(actionEvent, "UK");
    }

    public void USspeakClick(ActionEvent actionEvent) {
        speakClick(actionEvent, "US");
    }
}

