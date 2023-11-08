package com.example.btl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Home {
    private Stage stage;

    @FXML
    private Label UKLabel;

    @FXML
    private Button UKspeakerButton;

    @FXML
    private Label USLabel;

    @FXML
    private Button USspeakerButton;

    @FXML
    private Button addButton;

    @FXML
    private TextArea definitionField;

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
