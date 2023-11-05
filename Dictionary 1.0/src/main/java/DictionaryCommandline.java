package main.java;
import java.io.IOException;
import java.util.Map;

public class DictionaryCommandline {
    public static void showAllWords(main.java.Dictionary wordList) {
        System.out.printf("%-10s| %-20s\t| %s\n", "No", "English", "Vietnamese");
        int x = 0;
        for (Map.Entry<String, String> entry : wordList.getWords().entrySet()) {
            x++;
            System.out.printf("%-10d| %-20s\t| %s\n", x, entry.getKey(), entry.getValue());
        }
    }

    public static void dictionaryBasic(main.java.Dictionary wordList) {
        main.java.DictionaryManagement.insertFromCommandLine(wordList);
        showAllWords(wordList);
    }


    public static void main(String[] args) throws IOException {
        main.java.Dictionary wordList = new Dictionary();
        DictionaryManagement.insertFromCommandLine(wordList);
        showAllWords(wordList);
    }
}
