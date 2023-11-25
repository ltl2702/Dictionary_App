package API;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class TextToSpeechAPI extends BaseAPI{


    //private static final String API_KEY = "3389d69e99msh7fb681519198bcfp12a68djsn33fd40a0c29c";
    /*
    private static final String API_URL = "https://text-to-speech-api3.p.rapidapi.com/speak";
    private static final String API_HOST = "text-to-speech-api3.p.rapidapi.com";
     */

    public TextToSpeechAPI(String apiKey, String apiHost, String apiUrl) {
        super(apiKey,apiHost,apiUrl);
    }

    public void convertTextToSpeech(String text, String lang) throws Exception {
        HttpResponse<InputStream> response = makeGetRequest(text, lang);
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