package Controller;

import Connect.ConnectDB;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.w3c.dom.events.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Edit {
    int index = -1;

    //Add words.....................................
    @FXML
    private TextArea addDes;

    @FXML
    private Label addLabel;

    @FXML
    private TextField addPro;

    @FXML
    private JFXButton addSub;

    @FXML
    private Tab addTab;

    @FXML
    private TextField addword;

    //Update words...................................
    @FXML
    private TextField newword;

    @FXML
    private TextField oldword;

    @FXML
    private TextArea updateDes;

    @FXML
    private Label updateLabel;

    @FXML
    private TextField updatePro;

    @FXML
    private JFXButton updateSub;

    @FXML
    private Tab updateTab;

    //Remove words...................................
    @FXML
    private Label removeLabel;

    @FXML
    private JFXButton removeSub;

    @FXML
    private Tab removeTab;

    @FXML
    private TextField removeword;

    @FXML
    void addSubOnAction(ActionEvent event) {
        if (!addword.getText().isBlank())
            add();
        else
            addLabel.setText("Please enter your word.");
    }

    void add() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String word = addword.getText();
            String pronounce = addPro.getText();
            String description = addDes.getText();

            //Converts to HTML.
            StringBuilder convertHTML = new StringBuilder();
            convertHTML.append("<h1>").append(word).append("</h1>");
            convertHTML.append("<h3><i>/").append(pronounce).append("/</i></h3>");
            convertHTML.append("<h2>").append(description).append("</h2>");

            //Verifies word.
            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM av WHERE word = '" + word + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                int count = query.getInt("counter");
                if (count == 1)
                    addLabel.setText("The word already exists. Please try again.");
                else {
                    String add = "INSERT INTO av(word, html, pronounce, description) VALUES ('"
                            + word + "','" + convertHTML.toString() + "','" + pronounce + "','" + description + "')";
                    statement.executeUpdate(add);
                    addLabel.setText("The word is added.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
    }

    @FXML
    void removeSubOnAction(ActionEvent event) {
        if (!removeword.getText().isBlank())
            remove();
        else
            removeLabel.setText("Please enter your word.");
    }

    void remove() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String wordremove = removeword.getText();

            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM av WHERE word = '" + wordremove + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            while (query.next()) {
                int count = query.getInt("counter");
                if (count == 1) {
                    /*
                    String getID = "SELECT id FROM av WHERE word = '" + wordremove + "'";
                    ResultSet IDquery = statement.executeQuery(getID);
                    if (IDquery.next()) {
                        int id = IDquery.getInt("id");
                     */

                        String remove = "DELETE FROM av WHERE word = '" + wordremove + "'";
                        statement.executeUpdate(remove);
                        /*
                        String updateID = "UPDATE av SET id = id - 1 WHERE id > " + id;
                        statement.executeUpdate(updateID);
                        */

                        removeLabel.setText("The word is removed.");
                    /*
                    }
                    else{
                            removeLabel.setText("Error fetching ID.");
                        }
                    */
                    } else {
                        removeLabel.setText("The word does not exist. Please try again.");
                    }
                }
        } catch(Exception ex){
                ex.printStackTrace();
                ex.getCause();
            } finally{
                ConnectDB.closeConnection();
            }

    }

    @FXML
    void updateSubOnAction(ActionEvent event) {
        if (!newword.getText().isBlank() && !oldword.getText().isBlank())
            update();
        else
            updateLabel.setText("Please enter your word.");
    }

    void update() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String oldWord = oldword.getText();
            String newWord = newword.getText();
            String description = updateDes.getText();
            String pronounce = updatePro.getText();

            //Converts all information to HTML
            StringBuilder convertHTML = new StringBuilder();
            convertHTML.append("<h1>").append(newWord).append("</h1>");
            convertHTML.append("<h3><i>/").append(pronounce).append("/</i></h3>");
            convertHTML.append("<h2>").append(description).append("</h2>");

            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM av WHERE word = '" + oldWord + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                String getID = "SELECT id FROM av WHERE word = '" + oldWord + "'";
                ResultSet IDquery = statement.executeQuery(getID);
                if (IDquery.next()) {
                    int id = IDquery.getInt("id");
                    String update = "UPDATE av SET word = '" + newWord + "', html = '" + convertHTML.toString() + "', description = '" + description + "', pronounce = '" + pronounce + "' WHERE id = '" + id + "'";
                    statement.executeUpdate(update);
                    updateLabel.setText("The word has been successfully updated.");
                }
            }
            else {
                updateLabel.setText("The word does not exist.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
    }
}
