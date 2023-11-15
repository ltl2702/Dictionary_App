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


    private String cautionHtml = """
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

    private void showHtmlContent(String htmlContent) {
        Platform.runLater(() -> {
            if (searchField.getText().trim().isEmpty()) {
                webEngine.loadContent("");
            } else if (htmlContent != null && !htmlContent.isEmpty()) {
                webEngine.loadContent(htmlContent);
            } else {
                webEngine.loadContent(cautionHtml != null && !cautionHtml.isEmpty() ? cautionHtml : "");
            }
        });
    }


    private ExecutorService threadPool = Executors.newFixedThreadPool(2);

    private void performSearchInBackground(String searchTerm) {
        threadPool.submit(() -> {
            try {
                // Thực hiện tìm kiếm từ cơ sở dữ liệu
                ObservableList<Word> result = DictionaryManagement.dbSearchWord("'" + searchTerm.toLowerCase().trim() + "%'", datatable);

                // Cập nhật giao diện trên luồng giao diện chính
                Platform.runLater(() -> updateUI(result, searchTerm));
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý lỗi nếu cần
            }
        });
    }

    private void updateUI(ObservableList<Word> result, String searchTerm) {
        list = result;
        listResult.setItems(list);

        if (!list.isEmpty() && !searchTerm.trim().isEmpty()) {
            listResult.setVisible(true);
            Word firstWord = list.get(0);
            showHtmlContent(firstWord.getHtml());
        } else {
            listResult.setVisible(false);
            showHtmlContent(null);
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

    public void speakClick(ActionEvent actionEvent) {
    }

    /*
    @FXML
    void speakClick(MouseEvent event) throws JavaLayerException, IOException {
        GoogleAPI audio = GoogleAPI.getInstance();
        InputStream sound = null;
        switch (event.getSource()) {
            case UKspeakerButton:
                sound = audio.getAudio(word, "en-UK");
                break;
            case USspeakerButton:
                sound = audio.getAudio(word, "en-US");
                break;
        }

        if (sound != null) {
            audio.play(sound);
        }
    }

     */

}

