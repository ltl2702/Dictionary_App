package API;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TestAlanVoice {
    public static void main(String[] args) {
        // Set đường dẫn của VoiceManager để sử dụng giọng "alan"
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory");

        // Lấy danh sách giọng đang có
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();

        if (voices.length > 0) {
            // Chọn giọng "Alan"
            Voice alanVoice = voiceManager.getVoice("alan");

            // Thực hiện các thao tác với giọng Alan
            alanVoice.allocate();

                System.out.println("Alan voice is available.");

                alanVoice.speak("Hello, I am Alan. How can I help you?");

                alanVoice.deallocate();
                System.out.println("Alan voice deallocated.");
            } else {
                System.out.println("Alan voice is not available.");
            }
        }
    }
