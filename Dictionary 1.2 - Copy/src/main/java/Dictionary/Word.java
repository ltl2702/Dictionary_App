package Dictionary;

public class Word {
    private String wordTarget;
    private String html;

    public static final Word NO_RESULT = new Word("No result", "");

    public Word() {
    }

    public Word(String wordTarget, String html) {
        this.wordTarget = wordTarget;
        this.html = html;
    }
    public String getWordTarget(){return wordTarget;}

    public String getHtml() {
        return html;
    }

    @Override
    public String toString() {
        return wordTarget;
    }
}
