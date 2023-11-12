package cmd;

public class Word {
    private String wordTarget;
    private String html;

    public Word() {
        // Default constructor
    }

    public Word(String wordTarget, String html) {
        this.wordTarget = wordTarget;
        this.html = html;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public String toString() {
        return wordTarget; // or return any other information you want to display
    }
}
