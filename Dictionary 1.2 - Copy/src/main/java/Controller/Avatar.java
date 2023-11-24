package Controller;

import Connect.ConnectDB;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Avatar {

    @FXML
    private JFXButton user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12, user13, user14, user15, user16;

    @FXML
    private Label avtLabel;

    private TextField username;
    //private Stage window;
    private AnchorPane mainpane;

    void display() {
        System.out.println(username.getText());
        user1.setOnAction(e -> {
            action(1);
        });
        user2.setOnAction(e -> {
            action(2);
        });
        user3.setOnAction(e -> {
            action(3);
        });
        user4.setOnAction(e -> {
            action(4);
        });
        user5.setOnAction(e -> {
            action(5);
        });
        user6.setOnAction(e -> {
            action(6);
        });
        user7.setOnAction(e -> {
            action(7);
        });
        user8.setOnAction(e -> {
            action(8);
        });
        user9.setOnAction(e -> {
            action(9);
        });
        user10.setOnAction(e -> {
            action(10);
        });
        user11.setOnAction(e -> {
            action(11);
        });
        user12.setOnAction(e -> {
            action(12);
        });
        user13.setOnAction(e -> {
            action(13);
        });
        user14.setOnAction(e -> {
            action(14);
        });
        user15.setOnAction(e -> {
            action(15);
        });
        user16.setOnAction(e -> {
            action(16);
        });
    }

    public void action(int imageNum) {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String userName = username.getText();
            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM account WHERE username = '" + userName + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);
            if (query.next()) {
                String getID = "SELECT ID FROM account WHERE username = '" + userName + "'";
                ResultSet IDquery = statement.executeQuery(getID);
                if (IDquery.next()) {
                    int id = IDquery.getInt("id");
                    String update = "UPDATE account SET image = '" + imageNum + "' WHERE id = '" + id + "'";
                    statement.executeUpdate(update);
                    avtLabel.setText("You have successfully changed your profile picture.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
    }

    @FXML
    void returnButtonOnAction(ActionEvent event) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(UpdateAcc.class.getResource("/data/fxml/updateAcc.fxml"));
                AnchorPane updatepane = fxmlLoader.load();
                mainpane.getChildren().setAll(updatepane);

                UpdateAcc updateController = fxmlLoader.getController();
                updateController.setusername(username);
                updateController.setuserImage();
                updateController.setMainpane(mainpane);
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
    }

    public void setusername(TextField username) {
        this.username = username;
    }

    //public void setStage(Stage window) {
        //this.window = window;
    //}

    public void setmainpane(AnchorPane mainpane) {
          this.mainpane = mainpane;
    }
}
