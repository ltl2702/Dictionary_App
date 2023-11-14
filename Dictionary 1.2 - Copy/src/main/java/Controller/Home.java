package Controller;

import cmd.DictionaryManagement;
import cmd.Word;
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
    private final String datatable = "av";
    private Stage stage;

    @FXML
    private AnchorPane homePane;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXButton homeButton;

    @FXML
    private JFXButton userButton;

    @FXML
    private Label Menu, MenuClose;

    @FXML
    private WebView webView;
    private WebEngine webEngine;

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
    private boolean checklogin;
    private boolean checksignup;


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

        list = DictionaryManagement.dbSearchWord("''", datatable);
        listResult.setItems(list);
        listResult.setVisible(false);

        listResult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showHtmlContent(newValue.getHtml());
            }
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
        if (searchField.getText().trim().isEmpty()) {
            // Nếu trường tìm kiếm trống, tải nội dung trống vào WebView
            webEngine.loadContent("");

        } else if (htmlContent != null && !htmlContent.isEmpty()) {
            // Nếu htmlContent không null và không trống, tải nó vào WebView
            webEngine.loadContent(htmlContent);

        } else {
            // Nếu htmlContent null hoặc trống, tải cautionHtml vào WebView
            webEngine.loadContent(cautionHtml != null && !cautionHtml.isEmpty() ? cautionHtml : "");
            }
        }

    private ExecutorService threadPool = Executors.newFixedThreadPool(1);

    private void performSearch(String searchTerm) {
        threadPool.submit(() -> {
            list = DictionaryManagement.dbSearchWord("'" + searchTerm.toLowerCase().trim() + "%'", datatable);

            Platform.runLater(() -> {
                listResult.setItems(list);

                if (!list.isEmpty() && !searchTerm.trim().isEmpty()) {
                    listResult.setVisible(true);
                    // If there are matching words and the search term is not empty, display the HTML content of the first word
                    Word firstWord = list.get(0);
                    showHtmlContent(firstWord.getHtml());
                } else {
                    listResult.setVisible(false);
                    showHtmlContent(null);
                }
            });
        });
    }



    @FXML
    void searchFieldOnAction(ActionEvent event) {
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
        homeButton.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/data/fxml/homecopy.fxml"));
                AnchorPane homepane2 = fxmlLoader.load();
                homePane.getChildren().setAll(homepane2);
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
        });
    }

    @FXML
    void editButtonOnAction(ActionEvent event) {
        // Handle edit button action...
        editButton.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Edit.class.getResource("/data/fxml/edit2.fxml"));
                AnchorPane editpane = fxmlLoader.load();
                homePane.getChildren().setAll(editpane);
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
        });
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
        // Handle user button action...
        userButton.setOnAction(e -> {
            System.out.println("Home check login: " + checklogin);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Edit.class.getResource("/data/fxml/user.fxml"));
                AnchorPane userpane = fxmlLoader.load();
                homePane.getChildren().setAll(userpane);

                User userController = fxmlLoader.getController();
                if(checklogin == true) {
                    userController.setUsernameLogin(username);
                    userController.setCheckLogin(checklogin);
                }
                else if(checksignup == true) {
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

        });
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
}
