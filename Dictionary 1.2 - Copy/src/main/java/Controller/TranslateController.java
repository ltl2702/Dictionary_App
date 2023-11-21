package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import API.TranslateText;

import java.io.IOException;

public class TranslateController {
    public Label count;
    public Label count1;
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

    @FXML
    private void initialize() {
        selectLanguage1.getItems().addAll("English", "Vietnamese");
        selectLanguage2.getItems().addAll("English", "Vietnamese");

        // Set default selections
        //selectLanguage1.getSelectionModel().select("English");
        //selectLanguage2.getSelectionModel().select("Vietnamese");

        selectLanguage1.setOnAction(event -> updateTranslationDirection());
        changeLanguage.setOnAction(event -> swapLanguages());

        area1.textProperty().addListener((observable, oldValue, newValue) -> {
            translate();
            updateCharCount(newValue);
        });
        area1.setWrapText(true);
        area2.setWrapText(true);
    }
    private void swapLanguages() {
        // Lưu trữ ngôn ngữ hiện tại của cả hai vùng văn bản
        String language1 = selectLanguage1.getValue();
        String language2 = selectLanguage2.getValue();

        String textInArea1 = area1.getText();
        String textInArea2 = area2.getText();

        // Hoán đổi ngôn ngữ
        selectLanguage1.setValue(language2);
        selectLanguage2.setValue(language1);

        // Hoán đổi nội dung giữa area1 và area2
        area1.setText(textInArea2);
        area2.setText(textInArea1);

        // Cập nhật ngôn ngữ đối tượng dịch
        updateTranslationDirection();

        // Gọi lại translate để cập nhật nội dung mới của area1
        translate();
    }


    private void updateTranslationDirection() {
        String selectedLanguage = selectLanguage1.getValue();

        String targetLanguage = (selectedLanguage.equals("English")) ? "Vietnamese" : "English";

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

            String translatedText = translator.getTranslatedItem();

            Platform.runLater(() -> {
                if (translatedText != null && !translatedText.isEmpty()) {
                    area2.setText(translatedText);
                } else {
                    System.out.println("Không có dữ liệu dịch để hiển thị.");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateCharCount(String text) {
        int charCount = text.length();
        count.setText("Count: " + charCount);
    }
}
