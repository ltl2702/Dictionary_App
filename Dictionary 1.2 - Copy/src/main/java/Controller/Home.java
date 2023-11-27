package Controller;

import API.TextToSpeechFreetts;
import Connect.Alerter;
import Connect.ConnectDB;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Home implements Initializable {
    private final String datatable = "av1";

    private static Home instance;

    public static Home getInstance() {
        return instance;
    }

    public Button speakerButton, searchButton;
    public JFXButton gameButton;
    public ImageView menuimage1;
    public JFXButton translateButton, editButton, homeButton, userButton;
    public AnchorPane menuPane;
    public ImageView searchImage, homeImage;
    private Stage stage;

    @FXML
    private AnchorPane homePane, slider, DefinitionPane, FunctionalPane;

    @FXML
    private JFXButton Menu, MenuClose;

    @FXML
    private MenuButton menuButton;

    @FXML
    private MenuItem menuitemContributor, menuitemInfo, menuitemSignout;

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Word> listResult;

    @FXML
    private Button saveButton;

    @FXML
    private ImageView userimage;

    @FXML
    private Button favoriteButton;

    @FXML
    private ImageView saveImage;

    @FXML
    private Button editDefButton;

    private ObservableList<Word> list = FXCollections.observableArrayList();
    private int userID;

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
                // Update the saveImage based on whether the word is saved or not
                updateSaveImage(selectedWord.getId());
            }
        });
        instance = this;
    }

    private void updateSaveImage(int wordId) {
        boolean isSaved = isWordSaved(wordId, getUserName());
        if (isSaved) {
            Image image = new Image(getClass().getResourceAsStream("/data/icon/love2.png"));
            saveImage.setImage(image);
        } else {
            Image image = new Image(getClass().getResourceAsStream("/data/icon/love1.png"));
            saveImage.setImage(image);
        }
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
            searchField.setText(selectedWord.getWord());
        });
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String searchTerm = searchField.getText().trim();

        if (!list.isEmpty() && !searchTerm.isEmpty()) {
            Word firstResult = list.get(0);
            int id = firstResult.getId();
            showHtmlContent(firstResult.getHtml());
            speakerButton.setDisable(false);
            saveButton.setDisable(false);
            editDefButton.setDisable(false);
            updateSaveImage(id);
        } else {
            speakerButton.setDisable(true); // Tắt speakerButton
            saveButton.setDisable(true); //Tắt saveButton
            editDefButton.setDisable(true);
        }
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

    public boolean isWordSaved(int wordId, String username) {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM SavedWord WHERE English_id = '" + wordId + "' AND User_id = '" + username + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                int count = query.getInt("counter");
                //if (count == 1) {
                if (count > 0) {
                    System.out.println("thấy id");
                    Image image = new Image(getClass().getResourceAsStream("/data/icon/love2.png"));
                    saveImage.setImage(image);
                    return true;
                } else {
                    System.out.println("không thấy id");
                    Image image = new Image(getClass().getResourceAsStream("/data/icon/love1.png"));
                    saveImage.setImage(image);
                    return false;
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
            ex.getCause();
        } finally{
            ConnectDB.closeConnection();
        }
        return false;
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        ObservableList<Word> items = listResult.getItems();

        if (!items.isEmpty()) {
            Word selectedWord = listResult.getSelectionModel().getSelectedItem();

            if (selectedWord != null && selectedWord != Word.NOT_FOUND) {
                int wordId = selectedWord.getId();

                // Check if the word is already saved
                boolean isSaved = isWordSaved(wordId, getUserName());

                if (isSaved) {
                    // If the word is saved, remove it from the SavedWord table
                    DictionaryManagement.removeSavedWord(wordId, getUserName());
                    Image image = new Image(getClass().getResourceAsStream("/data/icon/love1.png"));
                    saveImage.setImage(image);

                    // Update UI or provide feedback as needed
                    System.out.println("Word removed from SavedWord table.");
                } else {
                    // If the word is not saved, save it to the SavedWord table
                    DictionaryManagement.saveWordToSavedWord(wordId, getUserName());
                    Image image = new Image(getClass().getResourceAsStream("/data/icon/love2.png"));
                    saveImage.setImage(image);

                    // Update UI or provide feedback as needed
                    System.out.println("Word saved to SavedWord table.");
                }
            } else if (items.get(0) != Word.NOT_FOUND && !searchField.getText().trim().isEmpty()) {
                selectedWord = items.get(0);
                int wordId = selectedWord.getId();

                // Check if the word is already saved
                boolean isSaved = isWordSaved(wordId, getUserName());

                if (isSaved) {
                    // If the word is saved, remove it from the SavedWord table
                    DictionaryManagement.removeSavedWord(wordId, getUserName());
                    Image image = new Image(getClass().getResourceAsStream("/data/icon/love1.png"));
                    saveImage.setImage(image);

                    // Update UI or provide feedback as needed
                    System.out.println("Word removed from SavedWord table.");
                } else {
                    // If the word is not saved, save it to the SavedWord table
                    DictionaryManagement.saveWordToSavedWord(wordId, getUserName());
                    Image image = new Image(getClass().getResourceAsStream("/data/icon/love2.png"));
                    saveImage.setImage(image);

                    // Update UI or provide feedback as needed
                    System.out.println("Word saved to SavedWord table.");
                }
            } else {
                // Handle the case when no word is selected
                System.out.println("No word selected.");
            }
        }
    }

    @FXML
    void homeButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/data/fxml/home.fxml"));
            AnchorPane homepane2 = fxmlLoader.load();
            homePane.getChildren().setAll(homepane2);
            Home homeController = fxmlLoader.getController();
            //homeController.setUsername(username);
            homeController.setUserID(userID);
            homeController.userLogin();
            homeController.setStage(stage);
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
    void favoriteButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SaveWord.class.getResource("/data/fxml/SaveWord.fxml"));
            AnchorPane homepane2 = fxmlLoader.load();
            homePane.getChildren().setAll(homepane2);

            SaveWord saveController = fxmlLoader.getController();
            saveController.setusername(getUserName());
            saveController.display();
            closeMenu();
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    void gameButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GameController.class.getResource("/data/fxml/gameController.fxml"));
            AnchorPane gamepane = fxmlLoader.load();
            homePane.getChildren().setAll(gamepane);
            closeMenu();
            GameController gameController = fxmlLoader.getController();
            //gameController.setUsername(username);
            gameController.setUserID(userID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void translateButtonOnAction(ActionEvent event) {
        loadFXMLInBackground("/data/fxml/translate.fxml");
    }

    @FXML
    void userButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Edit.class.getResource("/data/fxml/user.fxml"));
            AnchorPane userpane = fxmlLoader.load();

            homePane.getChildren().setAll(userpane);
            closeMenu();

            User userController = fxmlLoader.getController();
            //userController.setUsername(username);
            userController.setUserID(userID);
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

    public void speakClick(ActionEvent actionEvent) {
        ObservableList<Word> items = listResult.getItems();

        if (!items.isEmpty()) {
            Word selectedWord = listResult.getSelectionModel().getSelectedItem();

            if (selectedWord != null && selectedWord != Word.NOT_FOUND) {
                TextToSpeechFreetts.convertTextToSpeech(selectedWord.getWord());
            } else if (items.get(0) != Word.NOT_FOUND && !searchField.getText().trim().isEmpty()) {
                selectedWord = items.get(0);
                TextToSpeechFreetts.convertTextToSpeech(selectedWord.getWord());
            }
        }
    }

    @FXML
    void editDefinition(ActionEvent event) {
        ObservableList<Word> items = listResult.getItems();

        if (!items.isEmpty()) {
            Word selectedWord = listResult.getSelectionModel().getSelectedItem();
            if (selectedWord != null && selectedWord != Word.NOT_FOUND) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/data/fxml/EditDefScene.fxml"));
                    Parent root = fxmlLoader.load();
                    EditDefController editDefController = fxmlLoader.getController();
                    editDefController.setHtmlContent(selectedWord.getHtml());
                    editDefController.setSelectedWord(selectedWord);

                    Scene scene = new Scene(root);
                    editDefController.display(scene);
                    //editDefController.setHomeController(this);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (items.get(0) != Word.NOT_FOUND && !searchField.getText().trim().isEmpty()) {
                selectedWord = items.get(0);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/data/fxml/EditDefScene.fxml"));
                    Parent root = fxmlLoader.load();
                    EditDefController editDefController = fxmlLoader.getController();
                    editDefController.setHtmlContent(selectedWord.getHtml());
                    editDefController.setSelectedWord(selectedWord);

                    Scene scene = new Scene(root);
                    editDefController.display(scene);
                    //editDefController.setHomeController(this);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No word selected for editing definition.");
            }
        }
    }

    @FXML
    void menuitemContributorOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Contributor.class.getResource("/data/fxml/Contributor.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Contributor contributorControler = fxmlLoader.getController();
            contributorControler.display(scene);

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void menuitemInfoOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Edit.class.getResource("/data/fxml/user.fxml"));
            AnchorPane userpane = fxmlLoader.load();

            homePane.getChildren().setAll(userpane);
            closeMenu();

            User userController = fxmlLoader.getController();
            //userController.setUsername(username);
            userController.setUserID(userID);
            userController.userLogin();
            userController.setmainpane(homePane);
            userController.setStage(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @FXML
    void menuitemSignoutOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Welcome.class.getResource("/data/fxml/background.fxml"));
            Parent root = fxmlLoader.load();
            Welcome welcomeController = fxmlLoader.getController();
            welcomeController.initializeStage(stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    void userLogin() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String select = "SELECT image FROM account WHERE ID = '" + userID + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(select);
            if (query.next()) {
                int imageNumber = query.getInt("image");
                System.out.println(imageNumber);
                Image image = new Image(getClass().getResourceAsStream("/data/user/user" + imageNumber + ".png"));

                userimage.setImage(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image: " + e.getMessage());
            e.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String verify = "SELECT username FROM account WHERE ID = '" + userID + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);
            if (query.next()) {
                String USERNAME = query.getString("username");
                return USERNAME;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
        return null;
    }

    public WebView getWebView() {
        return webView;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    // Method to update the WebView with new HTML content
    public void updateWebView(String newHtmlContent) {
        Platform.runLater(() -> {
            if (newHtmlContent != null) {
                webView.getEngine().loadContent(newHtmlContent);
            } else {
                webView.getEngine().loadContent("");
            }
        });
    }
}