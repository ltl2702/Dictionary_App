package Controller;

import Connect.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class User implements Initializable {
    
    @FXML
    private JFXButton changeInfoButton;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastLabel;

    @FXML
    private JFXButton signoutButton;

    @FXML
    private ImageView userimage;

    @FXML
    private Label usernameLabel;
    
    private TextField usernameLogin;
    private TextField usernameSignup;
    private boolean checkLogin;
    private boolean checkSignup;
    private AnchorPane userpane;
    private Stage stage;

    void userLogin() {
        System.out.println("User check login: " + checkLogin);
        if(checkLogin == true) {
            try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
                String select = "SELECT firstname, lastname, username, image FROM account WHERE username = '" + usernameLogin.getText() + "'";
                Statement statement = connectDatabase.createStatement();
                ResultSet query = statement.executeQuery(select);
                if (query.next()) {
                    String firstname = query.getString("firstname");
                    String lastname = query.getString("lastname");
                    String username = query.getString("username");
                    int imageNumber = query.getInt("image");
                    Image image = new Image(getClass().getResourceAsStream("/data/user/user" + imageNumber + ".png"));

                    userimage.setImage(image);
                    //UpdateAcc a = new UpdateAcc();
                    //a.setuserImage(userimage);

                    firstnameLabel.setText(firstname);
                    lastLabel.setText(lastname);
                    usernameLabel.setText(username);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading image: " + e.getMessage());
                e.getCause();
            } finally {
                ConnectDB.closeConnection();
            }
        }
        if(checkSignup == true) {
            try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
                String select = "SELECT firstname, lastname, username, image FROM account WHERE username = '" + usernameSignup.getText() + "'";
                Statement statement = connectDatabase.createStatement();
                ResultSet query = statement.executeQuery(select);
                if (query.next()) {
                    String firstname = query.getString("firstname");
                    String lastname = query.getString("lastname");
                    String username = query.getString("username");
                    int imageNumber = query.getInt("image");
                    Image image = new Image(getClass().getResourceAsStream("/data/user/user" + imageNumber + ".png"));

                    userimage.setImage(image);
                    //UpdateAcc a = new UpdateAcc();
                    //a.setuserImage(userimage);

                    firstnameLabel.setText(firstname);
                    lastLabel.setText(lastname);
                    usernameLabel.setText(username);
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            } finally {
                ConnectDB.closeConnection();
            }
        }
    }

    @FXML
    void changeInfoButtonOnAction(ActionEvent event) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(UpdateAcc.class.getResource("/data/fxml/updateAcc.fxml"));
                AnchorPane updatepane = fxmlLoader.load();
                userpane.getChildren().setAll(updatepane);

                UpdateAcc updateController = fxmlLoader.getController();
                if(checkSignup) {
                    updateController.setusername(usernameSignup);
                    updateController.setuserImage();
                    updateController.setMainpane(userpane);
                }
                if(checkLogin) {
                    updateController.setusername(usernameLogin);
                    updateController.setuserImage();
                    updateController.setMainpane(userpane);
                }
                //signupController.setStage(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
    }

    @FXML
    void signoutButtonOnAction(ActionEvent event) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Signup.class.getResource("/data/fxml/background.fxml"));
                Parent root = fxmlLoader.load();
                Welcome welcomeController = fxmlLoader.getController();
                welcomeController.initializeStage(stage);

                Scene scene = new Scene(root, 900, 600);
                stage.setScene(scene);
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    public void setUsernameLogin(TextField username) {
        this.usernameLogin = username;
    }

    public void setCheckLogin(boolean checklogin) {
        this.checkLogin = checklogin;
    }

    public void setUsernameSignup(TextField username) {
        this.usernameSignup = username;
    }

    public void setCheckSignup(boolean checksignup) {
        this.checkSignup = checksignup;
    }

    public void setmainpane(AnchorPane homePane) {
        this.userpane = homePane;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
