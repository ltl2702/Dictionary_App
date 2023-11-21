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

    private final String MY_API_KEY = "69b58ee62amshd04e099689f7f18p1e56e9jsn7d5a526f61de";
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
            // Encode the text for the URL
            String encodedText = URLEncoder.encode(textForTranslate, StandardCharsets.UTF_8);

            // Build the request URL and body
            String url = "https://google-translate113.p.rapidapi.com/api/v1/translator/text";
            String body = "from=" + URLEncoder.encode("en", StandardCharsets.UTF_8)
                    + "&to=" + URLEncoder.encode("vi", StandardCharsets.UTF_8)
                    + "&text=" + encodedText;


            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("X-RapidAPI-Key", MY_API_KEY)
                    .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            // Send the HTTP request and receive the response
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code of the response
            int statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            // Check if the request was successful (status code 2xx)
            if (statusCode >= 200 && statusCode < 300) {
                // After receiving the response, print the entire JSON response
                System.out.println("JSON Response: " + response.body());

                // Phân tích phản hồi JSON và đặt giá trị cho đoạn văn đã dịch
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());
                String translation = jsonNode.path("trans").asText();

                    // Kiểm tra xem bản dịch có trống không
                if (!translation.isEmpty()) {
                    this.translatedItem = translation;
                } else {
                    System.out.println("Không có bản dịch được tìm thấy trong phản hồi.");
                    System.out.println("Phản hồi JSON đã phân tích: " + jsonNode.toPrettyString());
                }


            } else {
                // Print the error message if the request was not successful
                System.out.println("Error: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}
