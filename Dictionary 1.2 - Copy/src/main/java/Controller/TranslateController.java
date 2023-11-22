package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import API.TranslateText;

import java.util.concurrent.*;

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
    private static final String ENGLISH = "English";
    private static final String VIETNAMESE = "Vietnamese";

    private ExecutorService translationExecutor = Executors.newSingleThreadExecutor();
    private final ScheduledExecutorService delayExecutor = Executors.newScheduledThreadPool(1);
    private static final int TRANSLATION_DELAY_MILLIS = 500; // Độ trễ 500 milliseconds

    @FXML
    private void initialize() {
        initializeLanguageComboBoxes();
        initializeEventHandlers();
    }

    private void initializeLanguageComboBoxes() {
        addLanguagesToComboBox(selectLanguage1);
        addLanguagesToComboBox(selectLanguage2);
    }

    private void addLanguagesToComboBox(JFXComboBox<String> comboBox) {
        comboBox.getItems().addAll(ENGLISH, VIETNAMESE);
    }

    private void initializeEventHandlers() {
        selectLanguage1.setOnAction(event -> updateTranslationDirection());
        changeLanguage.setOnAction(event -> swapLanguages());

        area1.textProperty().addListener((observable, oldValue, newValue) -> {
            // Hủy bỏ bất kỳ tác vụ dịch nào đang đợi và đặt lại độ trễ
            delayExecutor.schedule(() -> translateAsync(), TRANSLATION_DELAY_MILLIS, TimeUnit.MILLISECONDS);
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
        translateAsync();
    }

    private void updateTranslationDirection() {
        String selectedLanguage = selectLanguage1.getValue();
        String targetLanguage = (VIETNAMESE.equals(selectedLanguage)) ? ENGLISH : VIETNAMESE;

        selectLanguage2.getSelectionModel().select(targetLanguage);

        translator.setLanguageFrom(selectedLanguage);
        translator.setLanguageTo(targetLanguage);

        translateAsync();
    }

    private void translateAsync() {
        // Hủy bỏ bất kỳ tác vụ dịch nào đang chờ
        translationExecutor.shutdownNow();

        // Khởi tạo một luồng mới cho quá trình dịch
        translationExecutor = Executors.newSingleThreadExecutor();

        // Thực hiện dịch trong một luồng mới
        CompletableFuture.runAsync(() -> translate(), translationExecutor);
    }

    private void translate() {
        String textToTranslate = area1.getText();

        // Check if the text to translate is empty
        if (textToTranslate.isEmpty()) {
            Platform.runLater(() -> area2.clear());
            return;
        }

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
