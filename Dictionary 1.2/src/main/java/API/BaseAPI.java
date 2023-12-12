package API;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public abstract class BaseAPI {
    protected String apiKey;
    protected String apiHost;
    protected String apiUrl;
    public BaseAPI(){}
    public BaseAPI(String apiKey, String apiHost, String apiUrl) {
        this.apiKey = apiKey;
        this.apiHost = apiHost;
        this.apiUrl = apiUrl;
    }

    protected HttpResponse<InputStream> makeGetRequest(String text, String lang) throws Exception {
        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
        String apiUrlWithParams = String.format("%s?text=%s&lang=%s", apiUrl, encodedText, lang);
        URI uri = new URI(apiUrlWithParams); // provide the full URL with the scheme
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", apiHost)
                .GET()
                .build();


        HttpResponse<InputStream> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofInputStream());

        return response;
    }

    protected HttpResponse<String> makePostRequest(String url, String body) throws IOException, InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", apiHost)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }
}
