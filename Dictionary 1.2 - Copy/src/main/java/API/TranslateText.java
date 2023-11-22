package API;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TranslateText {

    //private final String MY_API_KEY = "69b58ee62amshd04e099689f7f18p1e56e9jsn7d5a526f61de"; hết hạn
    private final String MY_API_KEY = "3389d69e99msh7fb681519198bcfp12a68djsn33fd40a0c29c";
    //dùng cái trên, cái dưới để lúc quan trọng dùng, dùng nhiều hết lượt :))) dùng 2 nick đấy các friend
    //private final String MY_API_KEY = "350c50c415msh8a0f5c71730baadp15d423jsnbe641e0ae6b8";
    private String languageFrom;
    private String languageTo;
    private String textForTranslate;
    private String translatedItem;

    public String getTranslatedItem() {
        return translatedItem;
    }

    public void setLanguageFrom(String languageFrom) {
        this.languageFrom = languageFrom;
    }

    public void setLanguageTo(String languageTo) {
        this.languageTo = languageTo;
    }

    public void setTextForTranslate(String textForTranslate) {
        this.textForTranslate = textForTranslate;
    }

    public void translate() {
        try {
            String encodedText = URLEncoder.encode(textForTranslate, StandardCharsets.UTF_8);

            String url = "https://google-translate113.p.rapidapi.com/api/v1/translator/text";
            String body = "from=" + URLEncoder.encode(getLanguageCode(languageFrom), StandardCharsets.UTF_8)
                    + "&to=" + URLEncoder.encode(getLanguageCode(languageTo), StandardCharsets.UTF_8)
                    + "&text=" + encodedText;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("X-RapidAPI-Key", MY_API_KEY)
                    .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            if (statusCode >= 200 && statusCode < 300) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());
                String translation = jsonNode.path("trans").asText();

                if (!translation.isEmpty()) {
                    this.translatedItem = translation;
                } else {
                    System.out.println("Không có bản dịch được tìm thấy trong phản hồi.");
                    System.out.println("Phản hồi JSON đã phân tích: " + jsonNode.toPrettyString());
                }

            } else {
                System.out.println("Error: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getLanguageCode(String language) {
        if (language == null) {
            throw new IllegalArgumentException("Ngôn ngữ không được hỗ trợ: null");
        }

        switch (language) {
            case "Vietnamese":
                return "vi";
            case "English":
                return "en";
            default:
                throw new IllegalArgumentException("Ngôn ngữ không được hỗ trợ: " + language);
        }
    }
}
