package API;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ListVoices {
    public static void main(String[] args) {
        // Lấy danh sách giọng đang có
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();

// In danh sách giọng
        System.out.println("Available Voices:");
        for (Voice voice : voices) {
            voice.load();
            System.out.println(voice.getName());
        }
    }
    }
