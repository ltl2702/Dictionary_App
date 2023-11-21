package API;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;

public class RapidApiExample {

    public static void main(String[] args) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-translate113.p.rapidapi.com/api/v1/translator/text"))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "69b58ee62amshd04e099689f7f18p1e56e9jsn7d5a526f61de")
                .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("from=auto&to=en&text=xin%20ch%C3%A0o"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
