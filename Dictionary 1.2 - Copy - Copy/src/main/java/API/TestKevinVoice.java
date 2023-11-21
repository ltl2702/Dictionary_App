package API;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TestKevinVoice {
    public static void main(String[] args) {
        // Set đường dẫn của VoiceManager để sử dụng giọng "kevin"
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        // Lấy danh sách giọng đang có
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();

        if (voices.length > 0) {
            // Chọn giọng "Kevin"
            Voice kevinVoice = voiceManager.getVoice("kevin");

            // Thực hiện các thao tác với giọng Kevin
            kevinVoice.allocate();
                System.out.println("Kevin voice is available.");

                kevinVoice.speak("Hello, I am Kevin. How can I help you?");

                kevinVoice.deallocate();
                System.out.println("Kevin voice deallocated.");
            } else {
                System.out.println("Kevin voice is not available.");
            }
        }
    }