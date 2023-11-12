package com.example.btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {

    public Stage window;

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Welcome.class.getResource("background.fxml"));
        Parent root = fxmlLoader.load();
        Welcome welcomeController = fxmlLoader.getController();
        welcomeController.initializeStage(window);
        //stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 893, 540);

        //Closes the window.
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        window.setTitle("Hello!");
        window.setScene(scene);
        window.show();


    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Closes the program.
     */
    private void closeProgram() {
        boolean answer = YesNo.display("EXIT", "Are you sure?");
        if (answer) {
            window.close();
        }
    }
}