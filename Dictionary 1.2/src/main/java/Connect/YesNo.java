package Connect;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class YesNo {
    private static boolean answer;

    /**
     * Display a Yes/No confirmation dialog.
     *
     * @param title   the title of the window.
     * @param message the message you want to send to the user.
     * @return true if the user clicks "Yes", false otherwise.
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();

        // Clicking outside the window is not allowed.
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(150);

        Label label = new Label(message);

        // Create buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);

        HBox buttonLayout = new HBox(20);
        buttonLayout.getChildren().addAll(yesButton, noButton);

        layout.getChildren().addAll(buttonLayout);
        layout.setAlignment(Pos.CENTER);
        buttonLayout.setAlignment(Pos.CENTER);

        // Scene and show
        Scene scene = new Scene(layout);
        window.setScene(scene);

        // Display window and wait for it to be closed before returning.
        window.showAndWait();

        return answer;
    }
}
