package translator.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The DictionaryCommandline class provides a command-line interface for managing a dictionary.
 */
public class DictionaryCommandline {

    /**
     * Displays all words in the dictionary.
     *
     * @param dict the Dictionary object to display words from
     */
    public static void showAllWords(Dictionary dict) {
        System.out.printf("%-8s| %-20s\t| %s\n", "No", "English", "Vietnamese");
        int x = 0;
        for (Map.Entry<String, String> e : dict.getWords().entrySet()) {
            x++;
            System.out.printf("%-8d| %-20s\t| %s\n", x, e.getKey(), e.getValue());
        }
    }

    /**
     * Performs basic dictionary operations including inserting words and displaying all words.
     *
     * @param dict the Dictionary object to perform operations on
     */
    public static void dictionaryBasic(Dictionary dict) {
        DictionaryManagement.insertFromCommandLine(dict);
        showAllWords(dict);
    }

    /**
     * Performs advanced dictionary operations including inserting words from a file,
     * displaying all words, and looking up a word.
     *
     * @param dict the Dictionary object to perform operations on
     */
    public static void dictionaryAdvanced(Dictionary dict) {
        DictionaryManagement.insertFromFile(dict);
        showAllWords(dict);
        DictionaryManagement.dictionaryLookup(dict);
    }

    /**
     * Searches for words in the dictionary that start with a given prefix.
     *
     * @param dict the Dictionary object to search words in
     */
    public static void dictionarySearcher(Dictionary dict) {
        System.out.print("Enter the prefix: ");
        Scanner scanner = new Scanner(System.in);
        String searchWord = scanner.nextLine().trim().toLowerCase();
        List<String> listWords = new ArrayList<String>();
        System.out.println("List words starting with \"" + searchWord + "\" are: ");
        for (Map.Entry<String, String> e : dict.getWords().entrySet()) {
            if (e.getKey().length() >= searchWord.length()) {
                if (searchWord.equals(e.getKey().substring(0, searchWord.length()))) {
                    listWords.add(e.getKey());
                }
            }
        }
        if (!listWords.isEmpty()) {
            System.out.print(listWords.get(0));
            for (int i = 1; i < listWords.size(); i++) {
                System.out.print(", " + listWords.get(i));
            }
        } else {
            System.out.print("There are no words that start with \"" + searchWord + "\" in the dictionary!");
        }
    }

    /**
     * The main method for the DictionaryCommandline program.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        boolean out = false;
        while (!out) {
            System.out.println("\t\t\tDictionary CommandLine");
            System.out.println("1. Show all words in dictionary");
            System.out.println("2. Sync with dictionary data file");
            System.out.println("3. Translate");
            System.out.println("4. Change meaning of a word");
            System.out.println("5. Remove a word from dictionary");
            System.out.println("6. Add new word to dictionary");
            System.out.println("7. Export dictionary to data file");
            System.out.println("8. Quit");
            System.out.println("Choose an option: ");
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            switch (option) {
                case 1: {
                    DictionaryCommandline.showAllWords(dict);
                    break;
                }
                case 2: {
                    DictionaryManagement.insertFromFile(dict);
                    System.out.println("Sync successfully!");
                    break;
                }
                case 3: {
                    DictionaryManagement.dictionaryLookup(dict);
                    break;
                }
                case 4: {
                    DictionaryManagement.dictionaryEdit(dict);
                    break;
                }
                case 5: {
                    DictionaryManagement.dictionaryRemove(dict);
                    break;
                }
                case 6: {
                    DictionaryManagement.insertFromCommandLine(dict);
                    break;
                }
                case 7: {
                    DictionaryManagement.dictionaryExportToFile(dict);
                    System.out.println("Dictionary exported successfully!");
                    break;
                }
                case 8: {
                    out = true;
                    break;
                }
            }
        }
        System.out.println("See you next time!");
    }
}
