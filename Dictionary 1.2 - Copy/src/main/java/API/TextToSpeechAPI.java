package API;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class TextToSpeechAPI {

    private static final String API_URL = "https://text-to-speech27.p.rapidapi.com/speech";
    private static final String API_KEY = "3389d69e99msh7fb681519198bcfp12a68djsn33fd40a0c29c";
    private static final String API_HOST = "text-to-speech27.p.rapidapi.com";

    public void convertTextToSpeech(String text, String lang) throws Exception {
        String encodedText = URLEncoder.encode(text, "UTF-8");
        String apiUrlWithParams = String.format("%s?text=%s&lang=%s", API_URL, encodedText, lang);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrlWithParams))
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<InputStream> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofInputStream());

        // Play audio if the request was successful (status code 200)
        if (response.statusCode() == 200) {
            playSpeech(response.body());
        } else {
            System.out.println("Error: " + response.body());
        }
    }

    private void playSpeech(InputStream audioStream) {
        try {
            AdvancedPlayer player = new AdvancedPlayer(audioStream);
            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent evt) {
                    System.out.println("Playback finished");
                }
            });
            player.play();

        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
