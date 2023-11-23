package Controller;

import Connect.Alerter;
import Connect.ConnectDB;
import Connect.HTML;
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
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
    void addSubOnAction(ActionEvent event) throws IOException {
        if (!addword.getText().isBlank())
            add();
        else {
            addLabel.setText("Please enter your word.");
            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Alerter alertControler = fxmlLoader.getController();
            alertControler.display("Please enter your word.", "/data/icon/angry2.gif", scene);
        }
    }

    void add() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String word = addword.getText().toLowerCase().trim();
            String word2 = addword.getText().trim();
            word = word.replaceAll("\\s+", " ").replaceAll("\'", "''");
            word2 = word2.replaceAll("\\s+", " ").replaceAll("\'", "''");
            System.out.println(word);
            String pronounce = addPro.getText().trim();
            String description = addDes.getText().trim();
            description = description.replaceAll("\\s+", " ").replaceAll("\'", "''");
            pronounce = pronounce.replaceAll("\\s+", " ").replaceAll("\'", "''");

            //Converts to HTML.
            /*
            StringBuilder convertHTML = new StringBuilder();
            convertHTML.append("<h1>").append(word2).append("</h1>");
            convertHTML.append("<h3><i>/").append(pronounce).append("/</i></h3>");
            convertHTML.append("<h2>").append(description).append("</h2>");
             */
            if (pronounce != null)
                pronounce = pronounce.replaceAll("\'", "''");
            else pronounce = "";

            if (description != null)
                description = description.replaceAll("\'", "''");
            else description = "";

            String convertHTML;
            convertHTML = HTML.convertToHtml(word, pronounce, description);

            //Verifies word.
            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM av1 WHERE LOWER(word) = '" + word + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                int count = query.getInt("counter");
                //if (count == 1)
                if (count > 0) {
                    addLabel.setText("The word already exists. Please try again.");
                    FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Alerter alertControler = fxmlLoader.getController();
                    alertControler.display("The word already exists. Please try again.", "/data/icon/cry.gif", scene);
                }
                else {
                    String add = "INSERT INTO av1(word, html, pronounce, description) VALUES ('"
                            + word2 + "','" + convertHTML + "','" + pronounce + "','" + description + "')";
                    statement.executeUpdate(add);
                    addLabel.setText("The word is added.");

                    FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Alerter alertControler = fxmlLoader.getController();
                    alertControler.display("The word is added.", "/data/icon/like2.gif", scene);
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
    void removeSubOnAction(ActionEvent event) throws IOException {
        if (!removeword.getText().isBlank())
            remove();
        else {
            removeLabel.setText("Please enter your word.");
            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Alerter alertControler = fxmlLoader.getController();
            alertControler.display("Please enter your word.", "/data/icon/angry2.gif", scene);
        }
    }

    void remove() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String wordremove = removeword.getText().toLowerCase().trim();
            wordremove = wordremove.replaceAll("\\s+", " ").replaceAll("\'", "''");

            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM av1 WHERE LOWER(word) = '" + wordremove + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            while (query.next()) {
                int count = query.getInt("counter");
                //if (count == 1) {
                if (count > 0) {
                    String remove = "DELETE FROM av1 WHERE LOWER(word) = '" + wordremove + "'";
                    statement.executeUpdate(remove);
                    removeLabel.setText("The word is removed.");
                    FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Alerter alertControler = fxmlLoader.getController();
                    alertControler.display("The word is removed.", "/data/icon/like2.gif", scene);
                } else {
                    removeLabel.setText("The word does not exist. Please try again.");
                    FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    Alerter alertControler = fxmlLoader.getController();
                    alertControler.display("The word does not exist. Please try again.", "/data/icon/cry.gif", scene);
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
    void updateSubOnAction(ActionEvent event) throws IOException {
        //old word = null
        if (oldword.getText().isBlank() || (oldword.getText().isBlank() && newword.getText().isBlank() && updateDes.getText().isBlank() && updatePro.getText().isBlank())) {
            updateLabel.setText("Please enter your word.");
            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Alerter alertControler = fxmlLoader.getController();
            alertControler.display("Please enter your word.", "/data/icon/angry2.gif", scene);
        }
        else if (newword.getText().isBlank() && updateDes.getText().isBlank() && updatePro.getText().isBlank() && !oldword.getText().isBlank()) {
            updateLabel.setText("No change.");
            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Alerter alertControler = fxmlLoader.getController();
            alertControler.display("Your word has no change.", "/data/icon/notfun2.gif", scene);
        } else
            update();
    }

    void update() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String oldWord = oldword.getText().toLowerCase().trim();
            String newWord = newword.getText().trim();
            String description = updateDes.getText().trim();
            String pronounce = updatePro.getText().trim();

            oldWord = oldWord.replaceAll("\\s+", " ").replaceAll("\'", "''");
            newWord = newWord.replaceAll("\\s+", " ").replaceAll("\'", "''");
            description = description.replaceAll("\\s+", " ").replaceAll("\'", "''");
            pronounce = pronounce.replaceAll("\\s+", " ").replaceAll("\'", "''");

            //Converts all information to HTML
            //StringBuilder convertHTML = new StringBuilder();
            //convertHTML.append("<h1>").append(newWord2).append("</h1>");
            //convertHTML.append("<h3><i>/").append(pronounce).append("/</i></h3>");
            //convertHTML.append("<h2>").append(description).append("</h2>");
            String authword = null, authpro = null, authdes = null;
            int newchange = 0;
            String convertHTML;
            //convertHTML = HTML.convertToHtml(authword, authpro, authdes);

            String verify = "SELECT COUNT(*) AS counter" +
                    " FROM av1 WHERE LOWER(word) = '" + oldWord + "'";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(verify);

            if (query.next()) {
                String getinfo = "SELECT id, word, html, description, pronounce FROM av1 WHERE LOWER(word) = '" + oldWord + "'";
                ResultSet IFquery = statement.executeQuery(getinfo);
                if (IFquery.next()) {
                    int id = IFquery.getInt("id");
                    String fakeword = IFquery.getString("word");
                    String fakedes = IFquery.getString("description");
                    String fakepro = IFquery.getString("pronounce");
                    String fakehtml = IFquery.getString("html");

                    fakeword = fakeword.replaceAll("\'", "''");
                    if (fakepro != null)
                       fakepro = fakepro.replaceAll("\'", "''");
                    else fakepro = "";

                    if (fakedes != null)
                        fakedes = fakedes.replaceAll("\'", "''");
                    else fakedes = "";

                    //Tìm vị trí của "</h3>"
                    int index = fakehtml.indexOf("</h3>");
                    //Tìm thấy
                    if (index != -1) {
                        fakehtml = fakehtml.substring(index + "</h3>".length());
                    }
                    //Không tìm thấy thì giữ nguyên

                    if (!newWord.isBlank()) {
                        authword = newWord;
                        if (!authword.equals(fakeword))
                            newchange++;
                    } else {
                        authword = fakeword;
                    }

                    if (!description.isBlank()) {
                        authdes = description;
                        if (!authdes.equals(fakedes))
                            newchange++;
                    } else {
                        authdes = fakedes;
                    }

                    if (!pronounce.isBlank()) {
                        authpro = pronounce;
                        if (!authpro.equals(fakepro))
                            newchange++;
                    } else {
                        authpro = fakepro;
                    }
                    convertHTML = HTML.convertToHtml(authword, authpro, authdes);

                    StringBuilder html = new StringBuilder();
                    html.append("<h1>").append(authword).append("</h1>");
                    html.append("<h3><i>/").append(authpro).append("/</i></h3>");
                    html.append(fakehtml);

                    System.out.println(newchange);
                    System.out.println(authword);
                    System.out.println(authpro);
                    System.out.println(authdes);
                    System.out.println(convertHTML);
                    System.out.println(html);

                    if (newchange > 0) {
                        if (description.isBlank()) {
                            System.out.println("true");
                            String update = "UPDATE av1 SET word = '" + authword + "', html = '" + html.toString() + "', pronounce = '" + authpro + "' WHERE LOWER(word) = '" + oldWord + "'";

                            statement.executeUpdate(update);
                            updateLabel.setText("The word has been successfully updated.");

                            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                            Parent root = fxmlLoader.load();
                            Scene scene = new Scene(root);
                            Alerter alertControler = fxmlLoader.getController();
                            alertControler.display("The word has been successfully updated.", "/data/icon/like2.gif", scene);
                        }
                        else {
                            System.out.println("false");
                            String update = "UPDATE av1 SET word = '" + authword + "', html = '" + convertHTML + "', description = '" + authdes + "', pronounce = '" + authpro + "' WHERE LOWER(word) = '" + oldWord + "'";

                            statement.executeUpdate(update);
                            updateLabel.setText("The word has been successfully updated.");

                            FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                            Parent root = fxmlLoader.load();
                            Scene scene = new Scene(root);
                            Alerter alertControler = fxmlLoader.getController();
                            alertControler.display("The word has been successfully updated.", "/data/icon/like2.gif", scene);
                        }
                    } else {
                        updateLabel.setText("No change.");
                        FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        Alerter alertControler = fxmlLoader.getController();
                        alertControler.display("Your word has no change.", "/data/icon/notfun2.gif", scene);
                    }
                }
            }
            else {
                updateLabel.setText("The word does not exist.");
                FXMLLoader fxmlLoader = new FXMLLoader(Alerter.class.getResource("/data/fxml/Alert.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Alerter alertControler = fxmlLoader.getController();
                alertControler.display("The word does not exist. Please try again.", "/data/icon/cry.gif", scene);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getCause();
        } finally {
            ConnectDB.closeConnection();
        }
    }
}
