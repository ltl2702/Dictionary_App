package main.java;

import java.util.TreeMap;

public class Dictionary {
    public final TreeMap<String, String> wordList = new TreeMap<>();

    public TreeMap<String, String> getWords() {
        return wordList;
    }

    public void insert(Word word) {
        wordList.put(word.getWord_source(), word.getWord_target());
    }
}
