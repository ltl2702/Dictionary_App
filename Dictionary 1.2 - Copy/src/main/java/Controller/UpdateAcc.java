package Controller;

import Connect.Alerter;
import Connect.ConnectDB;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UpdateAcc implements Initializable {

    @FXML
    private TextField oldUsername, newUsername;

    @FXML
    private PasswordField oldPass, newPass;

    @FXML
    private JFXButton submitButton, changeavt;


    @FXML
    private ImageView userimage;

    private boolean check = false;
    private AnchorPane mainpane;

    @FXML
    void submitButtonOnAction(ActionEvent event) throws IOException {
        if (!newUsername.getText().isBlank() && !newPass.getText().isBlank() && !oldPass.getText().isBlank())
            update();
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Alerter alertControler = fxmlLoader.getController();
            alertControler.display("Please enter your change.", "/data/icon/notfun2.gif", scene);
        }
    }

    void update() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String oldusername = oldUsername.getText();
            String newusername = newUsername.getText();
            String oldpass = oldPass.getText();
            String newpass = newPass.getText();

            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM account WHERE username = '" + oldusername + "'AND password = '" + oldpass + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                String getID = "SELECT ID FROM account WHERE username = '" + oldusername + "'";
                ResultSet IDquery = statement.executeQuery(getID);
                if (IDquery.next()) {
                    int id = IDquery.getInt("id");
                    String update = "UPDATE account SET username = '" + newusername + "', password = '" + newpass + "' WHERE id = '" + id + "'";
                    statement.executeUpdate(update);

                    FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Alerter alertControler = fxmlLoader.getController();
                    alertControler.display("The account has been successfully updated.", "/data/icon/like2.gif", scene);

                    check = true;
                }
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Alerter alertControler = fxmlLoader.getController();
                alertControler.display("The account does not exist.", "/data/icon/cry.gif", scene);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
    }

    @FXML
    void changeAvtButtonOnAction(ActionEvent event) {
        try {
            Stage window = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Avatar.class.getResource("/data/fxml/avt.fxml"));
            //Parent root = fxmlLoader.load();
            AnchorPane Avtpane = fxmlLoader.load();
            mainpane.getChildren().setAll(Avtpane);
            Avatar avatarController = fxmlLoader.getController();

            //Scene scene = new Scene(root, 400, 400);
            //Tối ưu chưa???
            if(check)
                avatarController.setusername(newUsername);
            else
                avatarController.setusername(oldUsername);
            avatarController.setmainpane(mainpane);
            avatarController.display();
            //avatarController.setStage(window);
            //window.setTitle("Choose your avatar");
            //window.setScene(scene);
            //window.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    public void setusername(TextField username) {
        this.oldUsername = username;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setuserImage() {
        System.out.println(oldUsername.getText());
        //System.out.println(newUsername.getText());
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String getID = "SELECT ID FROM account WHERE username = '" + oldUsername.getText() + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet IDquery = statement.executeQuery(getID);
            if (IDquery.next()) {
                int id = IDquery.getInt("ID");
                System.out.println(id);
                String select = "SELECT image FROM account WHERE ID = '" + id + "'";
                ResultSet query = statement.executeQuery(select);
                if (query.next()) {
                    int imageNumber = query.getInt("image");
                    System.out.println(imageNumber);
                    Image image = new Image(getClass().getResourceAsStream("/data/user/user" + imageNumber + ".png"));
                    userimage.setImage(image);
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
            ex.getCause();
        } finally{
            ConnectDB.closeConnection();
        }
    }

    public void setMainpane(AnchorPane userpane) {
        this.mainpane = userpane;
    }

}
