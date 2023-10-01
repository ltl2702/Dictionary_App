package translator;

/**
 * Represents a word and its explanation in a translation dictionary.
 */
public class Word {

    private String word_source;
    private String word_target;

    /**
     * Constructs a Word object with the specified explanation and target word.
     *
     * @param word_source The explanation of the word.
     * @param word_target  The target word to be translated.
     */
    public Word(String word_source, String word_target) {
        this.word_source = word_source.trim().toLowerCase();
        this.word_target = word_target.trim().toLowerCase();
    }

    /**
     * Constructs a default Word object with empty explanation and target word.
     */
    public Word() {
        this.word_source = "";
        this.word_target = "";
    }

    /**
     * Gets the explanation of the word.
     *
     * @return The explanation of the word.
     */
    public String getWord_source() {
        return word_source;
    }

    /**
     * Sets the explanation of the word.
     *
     * @param word_source The explanation of the word.
     */
    public void setWord_source(String word_source) {
        this.word_source = word_source;
    }

    /**
     * Gets the target word to be translated.
     *
     * @return The target word to be translated.
     */
    public String getWord_target() {
        return word_target;
    }

    /**
     * Sets the target word to be translated.
     *
     * @param word_target The target word to be translated.
     */
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }
}
