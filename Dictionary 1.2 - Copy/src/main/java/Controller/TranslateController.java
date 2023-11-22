package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import API.TranslateText;

public class TranslateController {

    public Label count;
    public JFXButton changeLanguage;

    @FXML
    private JFXComboBox<String> selectLanguage1;
    @FXML
    private JFXComboBox<String> selectLanguage2;
    @FXML
    private TextArea area1;
    @FXML
    private TextArea area2;

    private TranslateText translator = new TranslateText();
    private Task<Void> translationTask;

    @FXML
    private void initialize() {
        initializeLanguageComboBoxes();
        initializeEventHandlers();
    }

    private void initializeLanguageComboBoxes() {
        selectLanguage1.getItems().addAll("English", "Vietnamese");
        selectLanguage2.getItems().addAll("English", "Vietnamese");
    }

    private void initializeEventHandlers() {
        selectLanguage1.setOnAction(event -> updateTranslationDirection());
        changeLanguage.setOnAction(event -> swapLanguages());

        area1.textProperty().addListener((observable, oldValue, newValue) -> {
            delayedTranslate();  // Thay vì gọi translate trực tiếp, gọi delayedTranslate
            updateCharCount(newValue);
        });
        area1.setWrapText(true);
        area2.setWrapText(true);
    }

    private void swapLanguages() {
        String language1 = selectLanguage1.getValue();
        String language2 = selectLanguage2.getValue();

        String textInArea1 = area1.getText();
        String textInArea2 = area2.getText();

        selectLanguage1.setValue(language2);
        selectLanguage2.setValue(language1);

        area1.setText(textInArea2);
        area2.setText(textInArea1);

        updateTranslationDirection();
        delayedTranslate();  // Thay vì gọi translate trực tiếp, gọi delayedTranslate
    }

    private void updateTranslationDirection() {
        String selectedLanguage = selectLanguage1.getValue();
        String targetLanguage = ("Vietnamese".equals(selectedLanguage)) ? "English" : "Vietnamese";

        selectLanguage2.getSelectionModel().select(targetLanguage);

        translator.setLanguageFrom(selectedLanguage);
        translator.setLanguageTo(targetLanguage);

        delayedTranslate();  // Thay vì gọi translate trực tiếp, gọi delayedTranslate
    }

    private void translate() {
        String textToTranslate = area1.getText();

        // Check if the text to translate is empty
        if (textToTranslate.isEmpty()) {
            Platform.runLater(() -> area2.clear());
            return;
        }
        // Simulate a delay
        translator.setTextForTranslate(textToTranslate);
        translator.translate();

        String translatedText = translator.getTranslatedItem();

        Platform.runLater(() -> {
            if (translatedText != null && !translatedText.isEmpty()) {
                area2.setText(translatedText);
            } else {
                System.out.println("Không có dữ liệu dịch để hiển thị.");
            }
        });
    }

    private void delayedTranslate() {
        // Hủy bỏ task trước đó (nếu có)
        if (translationTask != null) {
            translationTask.cancel();
        }

        translationTask = new Task<Void>() {
            @Override
            protected Void call(){
                // Đợi 500ms trước khi thực hiện dịch
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // Restore interrupted status and return
                    Thread.currentThread().interrupt();
                    return null;
                }

                translate();

                return null;
            }
        };

        // Thực hiện task sau 500ms nếu không có thay đổi mới
        translationTask.setOnSucceeded(event -> {
            if (!translationTask.isCancelled()) {
                new Thread(translationTask).start();
            }
        });

        new Thread(translationTask).start();
    }


    private void updateCharCount(String text) {
        int charCount = text.length();
        count.setText("Count: " + charCount);
    }
}
