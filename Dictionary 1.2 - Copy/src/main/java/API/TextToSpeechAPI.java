package API;

import java.io.InputStream;
import java.net.http.HttpResponse;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class TextToSpeechAPI extends BaseAPI{

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
