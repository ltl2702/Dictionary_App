package Controller;

import Connect.ConnectDB;
import Dictionary.Word;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class EditDefController {
    Stage window = new Stage();
    @FXML
    private AnchorPane mainpane;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Button discardButton, submitButton;

    private Word selectedWord;

    public void setSelectedWord(Word word) {
        this.selectedWord = word;
    }

    public void setMainPane(AnchorPane mainpane) {
        this.mainpane = mainpane;
    }

    public void setHtmlContent(String htmlContent) {
        if (htmlContent != null) {
            htmlEditor.setHtmlText(htmlContent);
        } else {
            htmlEditor.setHtmlText("");
        }
    }

    public void discardChanges(ActionEvent actionEvent) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Discard changes?");
            alert.setContentText("If you discard now, you will lose any changes you've made.");

            //ý là alert hiện ra, ấn ok thì nó quay lại nơi bắt đầu
            if(alert.showAndWait().get() == ButtonType.OK) {
                window.close();
            }
    }

    public void submitEdition(ActionEvent actionEvent) {
        String newdef = htmlEditor.getHtmlText();
        if (htmlEditor != null && newdef != null && !newdef.isEmpty()) {
            String updateQuery = "UPDATE av1 SET html = ? WHERE word = ?";

            try (Connection connection = ConnectDB.connect("dict_hh")) {
                if (connection != null) {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(1, newdef);
                    if (selectedWord != null) {
                        preparedStatement.setString(2, selectedWord.toString());
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Cập nhật thành công!");
                            //if (homeController != null) {
                                System.out.println("home Controller is not null.");

                                Home homeController = Home.getInstance();

                            if (homeController != null) {
                                // Sử dụng thể hiện của Home controller để cập nhật WebView
                                homeController.updateWebView(newdef);
                                window.close();
                            } else {
                                System.out.println("Home Controller is null");
                            }
                        } else {
                            System.out.println("WebView is null.");
                        }
                    } else {
                        System.out.println("Không tìm thấy từ để cập nhật.");
                    }
                } else {
                    System.out.println("No word selected to update.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("HTML content is null or empty.");
        }
    }

    public void display(Scene scene) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);

        window.setScene(scene);
        //Displays window and waits for it to be closed before returning.
        window.showAndWait();

    }
}

