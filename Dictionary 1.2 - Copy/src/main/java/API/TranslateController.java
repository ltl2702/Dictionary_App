package API;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class TranslateController {
    public Label count;
    public JFXButton changeLanguage;
    public JFXButton speak1, speak2;

    @FXML
    private JFXComboBox<String> selectLanguage1;
    @FXML
    private JFXComboBox<String> selectLanguage2;
    @FXML
    private TextArea area1;
    @FXML
    private TextArea area2;

    private final GoogleTranslateAPI translator = new GoogleTranslateAPI("350c50c415msh8a0f5c71730baadp15d423jsnbe641e0ae6b8", "google-translate113.p.rapidapi.com", "https://google-translate113.p.rapidapi.com/api/v1/translator/text");
    private Task<Void> translationTask;
    private int userID;

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
        selectLanguage1.setOnAction(event -> {
            updateTranslationDirection();
            area1.setDisable(false);
            area2.setDisable(false);
        });
        changeLanguage.setOnAction(event -> swapLanguages());

        speak1.setOnAction(event -> {
            String textToSpeak = area1.getText();
            String selectedLanguage = selectLanguage1.getValue();
            if (!textToSpeak.isEmpty() && selectedLanguage != null) {
                speak(textToSpeak, GoogleTranslateAPI.getLanguageCode(selectedLanguage));
            }
        });

        speak2.setOnAction(event -> {
            String textToSpeak = area2.getText();
            String selectedLanguage = selectLanguage2.getValue();
            if (!textToSpeak.isEmpty() && selectedLanguage != null) {
                speak(textToSpeak, GoogleTranslateAPI.getLanguageCode(selectedLanguage));
            }
        });


        area1.textProperty().addListener((observable, oldValue, newValue) -> {
            delayedTranslate();  // Thay vì gọi translate trực tiếp, gọi delayedTranslate
            updateCharCount(newValue);
        });
        area1.setWrapText(true);
        area2.setWrapText(true);

        area1.setDisable(true);
        area2.setDisable(true);
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

    private void translate() throws IOException, InterruptedException {
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
                System.out.println("Translation: " + translatedText);
            } else {
                System.out.println("No translation found.");
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
            protected Void call() throws IOException, InterruptedException {
                // Đợi 500ms trước khi thực hiện dịch
                try {
                    Thread.sleep(300);
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

    private volatile boolean isSpeaking = false;
    private void speak(String textToSpeak, String lang) {
        if (isSpeaking) {
            System.out.println("Speech is already in progress. Ignoring request.");
            return;
        }
        try {
            isSpeaking = true;
            TextToSpeechAPI textToSpeechApi = new TextToSpeechAPI("350c50c415msh8a0f5c71730baadp15d423jsnbe641e0ae6b8", "text-to-speech27.p.rapidapi.com", "https://text-to-speech27.p.rapidapi.com/speech");
            new Thread(() -> {
                try {
                    textToSpeechApi.convertTextToSpeech(textToSpeak, lang);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isSpeaking = false;
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            isSpeaking = false;
        }
    }
    public void speakAction(ActionEvent actionEvent) {
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
