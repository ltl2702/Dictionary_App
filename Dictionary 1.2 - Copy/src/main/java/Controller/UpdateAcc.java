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

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateAcc implements Initializable {

    @FXML
    private TextField newUsername;

    @FXML
    private PasswordField oldPass, newPass;

    @FXML
    private JFXButton submitButton, changeavt;

    @FXML
    private Label updateLabel;

    @FXML
    private ImageView userimage;

    private boolean check = false;
    private AnchorPane mainpane;
    private int userID;

    @FXML
    void submitButtonOnAction(ActionEvent event) {
        if (!newUsername.getText().isBlank() && !newPass.getText().isBlank() && !oldPass.getText().isBlank())
            update();
        else
            updateLabel.setText("Please enter your change.");
    }

    void update() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String oldusername = getUserName();
            String newusername = newUsername.getText();
            String oldpass = oldPass.getText();
            String newpass = newPass.getText();

            String verify = "SELECT username, password FROM account WHERE ID = '" + userID + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                String OLDPASS = query.getString("password");
                System.out.println("OLDPASS: " + OLDPASS);
                System.out.println("oldpass: " + oldpass);
                System.out.println("oldusername: " + oldusername);
                System.out.println("newusername: " + newusername);
                if (oldpass.equals(OLDPASS)) {
                    if (oldusername.equals(newusername) && !Objects.equals(newpass, oldpass)) {
                        String update = "UPDATE account SET username = '" + oldusername + "', password = '" + newpass + "' WHERE id = '" + userID + "'";
                        statement.executeUpdate(update);
                        String update2 = "UPDATE SavedWord SET User_id = '" + newusername + "' WHERE User_id = '" + oldusername + "'";
                        statement.executeUpdate(update2);

                        FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        Alerter alertControler = fxmlLoader.getController();
                        alertControler.display("The account has been successfully updated.", "/data/icon/like2.gif", scene);
                    }
                    if (oldusername.equals(newusername) && oldpass.equals(newpass)) {
                        FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        Alerter alertControler = fxmlLoader.getController();
                        alertControler.display("No change", "/data/icon/angry2.gif", scene);
                    }
                    if (!oldusername.equals(newusername)) {
                        if (check()) {
                            System.out.println("Check: true");
                            String update = "UPDATE account SET username = '" + newusername + "', password = '" + newpass + "' WHERE id = '" + userID + "'";
                            statement.executeUpdate(update);
                            String update2 = "UPDATE SavedWord SET User_id = '" + newusername + "' WHERE User_id = '" + oldusername + "'";
                            statement.executeUpdate(update2);

                            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                            Parent root = fxmlLoader.load();
                            Scene scene = new Scene(root);
                            Alerter alertControler = fxmlLoader.getController();
                            alertControler.display("The account has been successfully updated.", "/data/icon/like2.gif", scene);
                        } else {
                            System.out.println("Check: false");
                            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                            Parent root = fxmlLoader.load();
                            Scene scene = new Scene(root);
                            Alerter alertControler = fxmlLoader.getController();
                            alertControler.display("This account exists.", "/data/icon/angry2.gif", scene);
                        }
                    }
                } else {
                    FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Alerter alertControler = fxmlLoader.getController();
                    alertControler.display("The old password is not true.", "/data/icon/cry.gif", scene);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
    }

    public boolean check() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM account WHERE username = '" + newUsername.getText()  + "' AND username != '" + getUserName() +"'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                int count = query.getInt("counter");
                if (count > 0) {
                    System.out.println(query.getInt("counter"));
                    updateLabel.setText("The account exists.");
                    return false;
                } else {
                    updateLabel.setText("The account does not exist.");
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
        return false;
    }

    @FXML
    void changeAvtButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Avatar.class.getResource("/data/fxml/avt.fxml"));
            //Parent root = fxmlLoader.load();
            AnchorPane Avtpane = fxmlLoader.load();
            mainpane.getChildren().setAll(Avtpane);
            Avatar avatarController = fxmlLoader.getController();
            avatarController.setUserID(userID);

            avatarController.setmainpane(mainpane);
            avatarController.display();
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setuserImage() {
        //System.out.println(newUsername.getText());
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String select = "SELECT image FROM account WHERE ID = '" + userID + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet IDquery = statement.executeQuery(select);
            if (IDquery.next()) {
                int imageNumber = IDquery.getInt("image");
                System.out.println(imageNumber);
                Image image = new Image(getClass().getResourceAsStream("/data/user/user" + imageNumber + ".png"));
                userimage.setImage(image);
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


    public void signoutButtonOnAction(ActionEvent actionEvent) {
    }

    public void changeInfoButtonOnAction(ActionEvent actionEvent) {
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
}

