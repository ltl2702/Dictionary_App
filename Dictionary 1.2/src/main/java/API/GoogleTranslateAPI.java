package API;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
public class GoogleTranslateAPI extends BaseAPI implements Language {

    private String languageFrom;
    private String languageTo;
    private String textForTranslate;
    private String translatedItem;

    String url = "https://google-translate113.p.rapidapi.com/api/v1/translator/text";

    public GoogleTranslateAPI(String apiKey, String apiHost, String apiUrl) {
        super(apiKey,apiHost,apiUrl);
    }
    public void translate() throws IOException, InterruptedException {
        String encodedText = URLEncoder.encode(textForTranslate, StandardCharsets.UTF_8);
        String body = "from=" + URLEncoder.encode(getLanguageCode(languageFrom), StandardCharsets.UTF_8)
                + "&to=" + URLEncoder.encode(getLanguageCode(languageTo), StandardCharsets.UTF_8)
                + "&text=" + encodedText;

        HttpResponse<String> response = makePostRequest(url, body);

        int statusCode = response.statusCode();

        if (statusCode == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
            String translation = jsonNode.path("trans").asText();
            if (!translation.isEmpty()) {
                this.translatedItem = translation;
            } else {
                System.out.println("Không có bản dịch được tìm thấy trong phản hồi.");
                System.out.println("Phản hồi JSON đã phân tích: " + jsonNode.toPrettyString());
            }
        }
    }
    static String getLanguageCode(String language) {
        if (language == null) {
            throw new IllegalArgumentException("Ngôn ngữ không được hỗ trợ: null");
        }
        return switch (language) {
            case "Vietnamese" -> "vi";
            case "English" -> "en";
            default -> throw new IllegalArgumentException("Ngôn ngữ không được hỗ trợ: " + language);
        };
    }

    @Override
    public String getTranslatedItem() {
        return translatedItem;
    }

    @Override
    public void setLanguageFrom(String languageFrom) {
        this.languageFrom = languageFrom;
    }

    @Override
    public void setLanguageTo(String languageTo) {
        this.languageTo = languageTo;
    }

    @Override
    public void setTextForTranslate(String textForTranslate) {
        this.textForTranslate = textForTranslate;
    }
}
