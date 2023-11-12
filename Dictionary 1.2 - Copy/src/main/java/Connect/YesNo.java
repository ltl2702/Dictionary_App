package Connect;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class YesNo {
    static boolean answer;

    /**
     * @param title   the title of the window.
     * @param message the message you want to send to the user.
     */
    public static boolean display(String title, String message) {

        Stage window = new Stage();
        //Click ra ngoài window là không được phép.
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(150);
        Label label = new Label();
        label.setText(message);

        //Creates 2 buttons.
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

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);

        HBox layoutchild = new HBox(20);
        layoutchild.getChildren().addAll(yesButton, noButton);
        layoutchild.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(layoutchild);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //Displays window and waits for it to be closed before returning.
        window.showAndWait();

        return answer;
    }
}
