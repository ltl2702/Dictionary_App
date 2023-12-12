package Controller;

import Connect.ConnectDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Dictionary.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaveWord {

    @FXML
    private TableColumn<Word, Integer> ID;

    @FXML
    private TableColumn<Word, String> Word;

    @FXML
    private TableColumn<Word, String> Pronounce;

    @FXML
    private TableColumn<Word, String> Description;

    @FXML
    private TableView<Word> table;
    private String username;
    private int userID;

    public void display() {
        try (Connection connectDatabase = new ConnectDB().connect("dict_hh")) {
            String select = "SELECT av1.id, av1.word, av1.pronounce, av1.description " +
                    "FROM av1 " +
                    "WHERE av1.id IN (SELECT English_id FROM SavedWord WHERE SavedWord.User_id = '" + username + "')";
            Statement statement = connectDatabase.createStatement();
            ResultSet query = statement.executeQuery(select);
            List<Word> wordList = new ArrayList<>();
            int sequentialId = 1;

            while (query.next()) {
                int id = query.getInt("id");
                String word = query.getString("word");
                String pronounce = query.getString("pronounce");
                String description = query.getString("description");

                Word newWord = new Word(id, word, pronounce, description, sequentialId);
                wordList.add(newWord);
                sequentialId++;
            }

            // Chuyển đổi ArrayList thành ObservableList
            ObservableList<Word> observableWordList = FXCollections.observableArrayList(wordList);

            // Thiết lập dữ liệu cho TableView
            ID.setCellValueFactory(new PropertyValueFactory<>("sequentialId"));
            Word.setCellValueFactory(new PropertyValueFactory<>("word"));
            Pronounce.setCellValueFactory(new PropertyValueFactory<>("pronounce"));
            Description.setCellValueFactory(new PropertyValueFactory<>("description"));

            table.setItems(observableWordList);

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void setusername(String username) {
        this.username = username;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
