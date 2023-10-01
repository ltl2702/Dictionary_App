package translator;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a word and its explanation in a translation dictionary.
 */
public class Word {

    private String word_explain;
    private String word_target;

    /**
     * Constructs a Word object with the specified explanation and target word.
     *
     * @param word_explain The explanation of the word.
     * @param word_target  The target word to be translated.
     */
    public Word(String word_explain, String word_target) {
        this.word_explain = word_explain.trim().toLowerCase();
        this.word_target = word_target.trim().toLowerCase();
    }

    /**
     * Constructs a default Word object with empty explanation and target word.
     */
    public Word() {
        this.word_explain = "";
        this.word_target = "";
    }

    /**
     * Gets the explanation of the word.
     *
     * @return The explanation of the word.
     */
    public String getWord_explain() {
        return word_explain;
    }

    /**
     * Sets the explanation of the word.
     *
     * @param word_explain The explanation of the word.
     */
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
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
