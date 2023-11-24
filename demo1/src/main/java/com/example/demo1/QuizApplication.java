package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //createTables();
        FXMLLoader fxmlLoader = new FXMLLoader(QuizApplication.class.getResource("QuizStartScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();


    }

    /*private void createTables() {
        Quiz.createTable();
        Question.createTable();
    }
     */
    public static void main(String[] args) {
        launch();
    }
}