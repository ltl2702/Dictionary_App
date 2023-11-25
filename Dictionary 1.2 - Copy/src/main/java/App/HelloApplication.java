package App;

import API.TextToSpeechAPI;
import Connect.YesNo;
import Controller.Welcome;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Stage window;

    @Override
    public void start(Stage stage) {
        window = stage;
        initUI();
    }

    private void initUI() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/data/fxml/background.fxml"));
            Parent root = fxmlLoader.load();

            Welcome welcomeController = fxmlLoader.getController();
            welcomeController.initializeStage(window);

            Scene scene = new Scene(root);

            // Closes the window.
            window.setOnCloseRequest(e -> {
                e.consume();
                closeProgram();
            });

            window.setTitle("Hello!");
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeProgram() {
        boolean answer = YesNo.display("EXIT", "Are you sure you want to exit?");
        if (answer) {
            window.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
