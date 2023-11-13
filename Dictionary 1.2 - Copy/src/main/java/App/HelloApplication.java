package App;

import Controller.Welcome;
import Connect.YesNo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public Stage window;

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Welcome.class.getResource("/data/fxml/background.fxml"));
        Parent root = fxmlLoader.load();
        Welcome welcomeController = fxmlLoader.getController();
        welcomeController.initializeStage(window);
        Scene scene = new Scene(root, 900, 600);

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
    private void closeProgram() {
        boolean answer = YesNo.display("EXIT", "Are you sure?");
        if (answer) {
            window.close();
        }
    }
}