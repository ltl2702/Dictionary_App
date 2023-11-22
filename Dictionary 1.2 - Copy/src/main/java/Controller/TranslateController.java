package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import API.TranslateText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

         //Set default selections
        //selectLanguage1.getSelectionModel().select("English");
        //selectLanguage2.getSelectionModel().select("Vietnamese");
    }

    private void initializeEventHandlers() {
        selectLanguage1.setOnAction(event -> updateTranslationDirection());
        changeLanguage.setOnAction(event -> swapLanguages());

        area1.textProperty().addListener((observable, oldValue, newValue) -> {
            handleTextChangedWithDelay(newValue);
            updateCharCount(newValue);
        });
        area1.setWrapText(true);
        area2.setWrapText(true);
    }

    private void handleTextChangedWithDelay(String newValue) {
        if (translationTask != null && translationTask.isRunning()) {
            translationTask.cancel();
        }

        // Tạo một Task mới để dịch với độ trễ
        translationTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(500); // Đợi 500ms trước khi dịch
                translate();
                return null;
            }
        };

        translationTask.setOnSucceeded(event -> {
            // Cập nhật UI sau khi dịch hoàn thành
            Platform.runLater(() -> {
                String translatedText = translator.getTranslatedItem();
                area2.setText(translatedText != null && !translatedText.isEmpty() ? translatedText : "Không có dữ liệu dịch để hiển thị.");
            });
        });

        translationTask.setOnCancelled(event -> {
            Thread.currentThread().interrupt();
        });

        translationTask.setOnFailed(event -> {
            // Xử lý lỗi (nếu cần)
        });

        // Bắt đầu Task mới
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(translationTask);
        executorService.shutdown();
    }

    private void swapLanguages() {
        String language1 = selectLanguage1.getValue();
        String language2 = selectLanguage2.getValue();

        selectLanguage1.setValue(language2);
        selectLanguage2.setValue(language1);

        String textInArea1 = area1.getText();
        area1.setText(area2.getText());
        area2.setText(textInArea1);

        updateTranslationDirection();

        translate();
    }


    private void updateTranslationDirection() {
        String selectedLanguage = selectLanguage1.getValue();
        String targetLanguage = ("Vietnamese".equals(selectedLanguage)) ? "English" : "Vietnamese";

        selectLanguage2.getSelectionModel().select(targetLanguage);

        translator.setLanguageFrom(selectedLanguage);
        translator.setLanguageTo(targetLanguage);

        translate();
    }

    private void translate() {
        String textToTranslate = area1.getText();

        try {
            translator.setTextForTranslate(textToTranslate);
            translator.translate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCharCount(String text) {
        int charCount = text.length();
        count.setText("Count: " + charCount);
    }
}
