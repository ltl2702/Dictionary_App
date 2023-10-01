package translator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static translator.DictionaryManagement.*;

public class DictionaryCommandline {
    public static void showAllWords(Dictionary wordList) {
        System.out.printf("%-10s| %-20s\t| %s\n", "No", "English", "Vietnamese");
        int x = 0;
        for (Map.Entry<String, String> entry : wordList.getWords().entrySet()) {
            x++;
            System.out.printf("%-10d| %-20s\t| %s\n", x, entry.getKey(), entry.getValue());
        }
    }



    public static void dictionaryBasic(Dictionary wordList) {
        DictionaryManagement.insertFromCommandLine(wordList);
        showAllWords(wordList);
    }

    public static void dictionaryAdvanced(Dictionary wordList) throws IOException {
        DictionaryManagement.insertFromFile(wordList);
        showAllWords(wordList);
        DictionaryManagement.dictionaryLookup(wordList);
        dictionarySearcher(wordList);
        dictionaryExportToFile(wordList);
        dictionaryEdit(wordList);
        showAllWords(wordList);
        dictionaryRemove(wordList);
        showAllWords(wordList);
    }


    public static void dictionarySearcher(Dictionary wordList) {
        System.out.print("Enter the word you want to search: ");
        String searchWord = scanner.nextLine().trim().toLowerCase();

        List<String> listWords = new ArrayList<String>();
        System.out.println("List words starting with \"" + searchWord + "\" are: ");
        for (Map.Entry<String, String> e : wordList.getWords().entrySet()) {
            if (e.getKey().length() >= searchWord.length() && e.getKey().startsWith(searchWord)) {
                listWords.add(e.getKey());
            }
        }
        if (!listWords.isEmpty()) {
            System.out.println(String.join(", ", listWords));
        } else {
            System.out.println("There are no words that start with \"" + searchWord + "\" in the dictionary!");
        }
    }



    public static int displayMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\t\t\tDictionary CommandLine");
        System.out.println("1. Show all words in dictionary");
        System.out.println("2. Sync with dictionary data file");
        System.out.println("3. Translate");
        System.out.println("4. Change meaning of a word");
        System.out.println("5. Remove a word from dictionary");
        System.out.println("6. Add new word to dictionary");
        System.out.println("7. Export dictionary to data file");
        System.out.println("8. Quit");
        System.out.println("Choose an option: ");
        if (sc.hasNextInt()) {
            return sc.nextInt();
        } else {
            sc.next();
            return -1;
        }
    }

    // Hàm main để chạy ứng dụng
    public static void main(String[] args) throws IOException {
        Dictionary wordList = new Dictionary();
        boolean out = false;

        while (!out) {
            int option = displayMenu();
            switch (option) {
                case 1:
                    showAllWords(wordList);
                    break;
                case 2:
                    DictionaryManagement.insertFromFile(wordList);
                    System.out.println("Sync successfully!");
                    break;
                case 3:
                    DictionaryManagement.dictionaryLookup(wordList);
                    break;
                case 4:
                    dictionaryEdit(wordList);
                    break;
                case 5:
                    dictionaryRemove(wordList);
                    break;
                case 6:
                    DictionaryManagement.insertFromCommandLine(wordList);
                    break;
                case 7:
                    dictionaryExportToFile(wordList);
                    System.out.println("Dictionary exported successfully!");
                    break;
                case 8:
                    out = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }

        System.out.println("Bye bye!");
    }

}
