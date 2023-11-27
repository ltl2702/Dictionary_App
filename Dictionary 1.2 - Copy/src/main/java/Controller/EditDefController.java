package Controller;

import Connect.Alerter;
import Connect.ConnectDB;
import Dictionary.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.sql.*;

public class EditDefController {
    Stage window = new Stage();

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Button discardButton, submitButton;

    private Word selectedWord;

    public void setSelectedWord(Word word) {
        this.selectedWord = word;
    }

    public void setHtmlContent(String htmlContent) {
        if (htmlContent != null) {
            htmlEditor.setHtmlText(htmlContent);
        } else {
            htmlEditor.setHtmlText("");
        }
    }

    public void discardChanges(ActionEvent actionEvent) throws IOException {
        /*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Discard changes?");
        alert.setContentText("If you discard now, you will lose any changes you've made.");

        //ý là alert hiện ra, ấn ok thì nó quay lại nơi bắt đầu
        if(alert.showAndWait().get() == ButtonType.OK) {
            window.close();
        }

         */

        FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Alerter alertControler = fxmlLoader.getController();
        alertControler.display("Discard changes?\nIf you discard now, you will lose any changes you've made.", "/data/icon/notfun2.gif", scene);
        window.close();
    }

    public void submitEdition(ActionEvent actionEvent) throws IOException {
        String newdef = htmlEditor.getHtmlText();
        String newWord = getWordFromHtml(newdef);
        String newPro = getPronounceFromHtml(newdef);

        if (htmlEditor != null && newdef != null && !newdef.isEmpty() && !newdef.isBlank() && !newWord.isBlank() && !newWord.isEmpty()) {
            if (!newWord.equals(selectedWord.toString()) && !existedWord(newWord, selectedWord.toString())) {
                String updateQuery = "UPDATE av1 SET html = ? ,word = ?, pronounce = ? WHERE id = ?";

                try (Connection connection = ConnectDB.connect("dict_hh")) {
                    if (connection != null) {
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, newdef);
                        if (selectedWord != null) {
                            preparedStatement.setString(2, newWord);
                            preparedStatement.setString(3, newPro);
                            preparedStatement.setInt(4, selectedWord.getId());

                            int rowsAffected = preparedStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Cập nhật thành công!");
                                Home homeController = Home.getInstance();

                                FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root);
                                Alerter alertControler = fxmlLoader.getController();
                                alertControler.display("The word has been successfully updated.", "/data/icon/like.gif", scene);

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
            } else if (newWord.equals(selectedWord.toString())) {
                String updateQuery = "UPDATE av1 SET html = ? ,word = ?, pronounce = ? WHERE id = ?";

                try (Connection connection = ConnectDB.connect("dict_hh")) {
                    if (connection != null) {
                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, newdef);
                        if (selectedWord != null) {
                            preparedStatement.setString(2, newWord);
                            preparedStatement.setString(3, newPro);
                            preparedStatement.setInt(4, selectedWord.getId());

                            int rowsAffected = preparedStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Cập nhật thành công!");
                                Home homeController = Home.getInstance();

                                FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root);
                                Alerter alertControler = fxmlLoader.getController();
                                alertControler.display("The word has been successfully updated.", "/data/icon/like.gif", scene);

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
                FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Alerter alertControler = fxmlLoader.getController();
                alertControler.display("The word already exists. Please try again.", "/data/icon/angry.gif", scene);
            }
        } else {
            Home homeController = Home.getInstance();
            if (homeController != null) {
                // Sử dụng thể hiện của Home controller để cập nhật WebView
                homeController.updateWebView(selectedWord.getHtml());
            } else {
                System.out.println("Home Controller is null");
            }

            System.out.println("HTML content is null or empty.");
            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Alerter alertControler = fxmlLoader.getController();
            alertControler.display("Please enter your word.", "/data/icon/angry.gif", scene);
        }
    }

    public void display(Scene scene, double relativeX, double relativeY) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);

        window.setScene(scene);

        Screen primaryScreen = Screen.getPrimary();
        Rectangle2D screenBounds = primaryScreen.getBounds();

        double absoluteX = screenBounds.getMinX() + screenBounds.getWidth() * relativeX;
        double absoluteY = screenBounds.getMinY() + screenBounds.getHeight() * relativeY;

        window.setX(absoluteX);
        window.setY(absoluteY);

        window.showAndWait();
    }



    public boolean existedWord(String newword, String oldword) {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM av1 WHERE word = '" + newword + "' AND word <> '"+ oldword + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                int count = query.getInt("counter");
                //if (count == 1) {
                if (count > 0) {
                    System.out.println("thấy id");
                    return true;
                } else {
                    System.out.println("không thấy id");
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

    public String getWordFromHtml (String html) {
        Document doc = Jsoup.parse(html);
        String word = doc.select("h1").text();
        return word;
    }

    public String getPronounceFromHtml (String html) {
        Document doc = Jsoup.parse(html);
        String pronounce = doc.select("h3 i").text();
        return pronounce;
    }

}
