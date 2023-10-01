package translator;

public class Word {

    private String word_source;
    private String word_target;

    public Word(String word_source, String word_target) {
        this.word_source = word_source.trim().toLowerCase();
        this.word_target = word_target.trim().toLowerCase();
    }

    public Word() {
        this.word_source = "";
        this.word_target = "";
    }

    public String getWord_source() {
        return word_source;
    }

    public void setWord_source(String word_source) {
        this.word_source = word_source;
    }

    public String getWord_target() {
        return word_target;
    }
    
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }
}
