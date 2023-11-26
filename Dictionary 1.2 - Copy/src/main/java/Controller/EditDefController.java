package Controller;

import Connect.ConnectDB;
import Dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditDefController {
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
        discardButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Discard changes?");
            alert.setContentText("If you discard now, you will lose any changes you've made.");

            //ý là alert hiện ra, ấn ok thì nó quay lại nơi bắt đầu
            if(alert.showAndWait().get() == ButtonType.OK) {

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/data/fxml/wtf.fxml"));
                    AnchorPane newPane = loader.load();
                    mainpane.getChildren().setAll(newPane);
                    EditDefController editDefController = loader.getController();
                    editDefController.setMainPane(mainpane);

                    Home home = loader.getController();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }

        });
    }

    public void submitEdition(ActionEvent actionEvent) {
        String newdef = htmlEditor.getHtmlText();
        if (htmlEditor != null && newdef != null && !newdef.isEmpty()) {
            String updateQuery = "UPDATE av1 SET html = ? WHERE word = ?";

            try (Connection connection = ConnectDB.connect("dict_hh")){
                if(connection != null) {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                    preparedStatement.setString(1, newdef);
                    if (selectedWord != null) {
                        preparedStatement.setString(2, selectedWord.toString());
                        // rest of your code
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.out.println("Không tìm thấy từ để cập nhật.");
                        }
                    } else {
                        System.out.println("No word selected to update.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("HTML content is null or empty.");
        }
    }
    }

