package Controller;

import Connect.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    void userLogin() {
        System.out.println("User check login: " + checkLogin);
        if(checkLogin == true) {
            try (Connection connectDatabase = new ConnectDB().connect("userinfo")) {
                String select = "SELECT firstname, lastname, username FROM account WHERE username = '" + usernameLogin.getText() + "'";
                Statement statement = connectDatabase.createStatement();
                ResultSet query = statement.executeQuery(select);
                if (query.next()) {
                    String firstname = query.getString("firstname");
                    String lastname = query.getString("lastname");
                    String username = query.getString("username");

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
        if(checkSignup == true) {
            try (Connection connectDatabase = new ConnectDB().connect("userinfo")) {
                String select = "SELECT firstname, lastname, username FROM account WHERE username = '" + usernameSignup.getText() + "'";
                Statement statement = connectDatabase.createStatement();
                ResultSet query = statement.executeQuery(select);
                if (query.next()) {
                    String firstname = query.getString("firstname");
                    String lastname = query.getString("lastname");
                    String username = query.getString("username");

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

    }

    @FXML
    void signoutButtonOnAction(ActionEvent event) {

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
}
