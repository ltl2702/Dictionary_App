package translator;

import java.util.TreeMap;

/**
 * The {@code Dictionary} class represents a dictionary that stores word translations.
 */
public class Dictionary {
    private TreeMap<String, String> words = new TreeMap<String, String>();

    /**
     * Get the words in the dictionary.
     *
     * @return A TreeMap containing words and their translations
     */
    public TreeMap<String, String> getWords() {
        return words;
    }

    /**
     * Inserts a word into the dictionary.
     *
     * @param word The Word object to be inserted
     */
    public void insert(Word word) {
        words.put(word.getWord_explain(), word.getWord_target());
    }

    /**
     * Removes a word from the dictionary.
     *
     * @param word_explain The source word to be removed
     */
    public void removeWord(String word_explain) {
        words.remove(word_explain.toLowerCase());
    }

    /**
     * Translates a word from the dictionary.
     *
     * @param word_explain The source word to be translated
     * @return The translated word or null if not found
     */
    public String translate(String word_explain) {
        return words.get(word_explain.toLowerCase());
    }
}
