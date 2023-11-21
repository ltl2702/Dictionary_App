package API;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class GoogleTextToSpeechAPI {
    public static final String GOOGLE_TTS_API_URL = "https://translate.google.com/translate_tts?";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom(); // Thread-safe
    private static final Base64.Encoder BASE64_ENCODER = Base64.getUrlEncoder(); // Thread-safe
    private static final String USER_AGENT = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";
    private static GoogleTextToSpeechAPI instance;

    private GoogleTextToSpeechAPI() {
    }

    public synchronized static GoogleTextToSpeechAPI getInstance() {
        if (instance == null) {
            instance = new GoogleTextToSpeechAPI();
        }
        return instance;
    }

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        SECURE_RANDOM.nextBytes(randomBytes);
        return BASE64_ENCODER.encodeToString(randomBytes);
    }

    private static String generateTTSUrl(String language, String text) {
        return GOOGLE_TTS_API_URL + "?ie=UTF-8" +
                "&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&tl=" + language +
                "&tk=" + generateNewToken() +
                "&client=tw-ob";
    }

    public InputStream getAudio(String text, String languageOutput) throws IOException {
        String urlString = generateTTSUrl(languageOutput, text);
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            InputStream audioSrc = urlConnection.getInputStream();
            return new BufferedInputStream(audioSrc);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public void play(InputStream sound) throws JavaLayerException, IOException {
        try {
            new Player(sound).play();
        } finally {
            if (sound != null) {
                sound.close();
            }
        }
    }

}
