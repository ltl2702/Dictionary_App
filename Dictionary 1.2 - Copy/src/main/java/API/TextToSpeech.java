package API;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {

    private static final String VOICE_NAME = "kevin16";

    public static void convertTextToSpeech(String text) {
        setSystemProperty();
        Voice voice = getVoice();
        speakText(voice, text);
    }

    private static void setSystemProperty() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    }

    private static Voice getVoice() {
        Voice voice = VoiceManager.getInstance().getVoice(VOICE_NAME);
        if (voice != null) {
            System.out.println(VOICE_NAME + " voice found");
            voice.allocate();
        } else {
            throw new IllegalStateException("Cannot find voice: " + VOICE_NAME);
        }
        return voice;
    }

    private static void speakText(Voice voice, String text) {
        if (voice != null) {
            System.out.println("Speaking: " + text);
            voice.speak(text);
            voice.deallocate();
        } else {
            throw new IllegalStateException("Voice is not allocated");
        }
    }
}
