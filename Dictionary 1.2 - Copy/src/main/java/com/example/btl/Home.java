package com.example.btl;

import cmd.DictionaryManagement;
import cmd.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class Home implements Initializable  {
    private final String tableNameInSearchGui = "av";
    private Stage stage;

    @FXML
    private Label UKLabel;

    @FXML
    private Button UKspeakerButton;

    @FXML
    private Button searchButton;

    @FXML
    private Label USLabel;

    @FXML
    private Button USspeakerButton;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button eraseButton;

    @FXML
    private Button gameButton;

    @FXML
    private ImageView homeImage;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView searchImage;

    @FXML
    private Button translateButton;

    @FXML
    private Button userButton;

    @FXML
    private ListView<Word> listResult;

    @FXML
    private Label pronounceWord;

    @FXML
    private TextArea definitionArea;

    private ObservableList<Word> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchButton.setOnAction(event -> {
            performSearch(searchField.getText());
        });

        list = DictionaryManagement.dbSearchWord("'a%'", tableNameInSearchGui);
        listResult.setItems(list);

        listResult.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                System.out.println("NOOOOOOO");
            } else {
                definitionArea.setText(newValue.getWordExplain());
                //pronounceWord.setText(newValue.getPronounce());
                definitionArea.setEditable(false);
            }
        });

        //   editTab.setVisible(false);
    }

    /**
     * onActionSearchBtn.
     */
    public void onActionSearchBtn() {
        performSearch(searchField.getText());
    }

    // Hàm thực hiện tìm kiếm và cập nhật ListView
    private void performSearch(String searchTerm) {
        list = DictionaryManagement.dbSearchWord("'" + searchTerm.toLowerCase().trim() + "%'", tableNameInSearchGui);
        listResult.setItems(list);

        // Hiển thị mô tả và giải thích của từ đầu tiên (nếu có)
        if (!list.isEmpty()) {
            Word firstWord = list.get(0);
            definitionArea.setText(firstWord.getWordExplain());
           // pronounceWord.setText(firstWord.getPronounce());
        }
    }

    /**
     * onMouseClickListView.
     */
    public void onMouseClickListView() {
        Word selectedWord = listResult.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            definitionArea.setText(selectedWord.getWordExplain());
            pronounceWord.setText(selectedWord.getPronounce());
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



