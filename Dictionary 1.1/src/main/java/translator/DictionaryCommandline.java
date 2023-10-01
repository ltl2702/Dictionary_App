package translator;
import java.io.IOException;
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

    public static void dictionarySearcher(Dictionary wordList) {
        System.out.print("Enter the word you want to search: ");
        String searchWord = scanner.nextLine().trim().toLowerCase();
        boolean found = false;
        for (Map.Entry<String, String> entry : wordList.getWords().entrySet()) {
            if (entry.getKey().startsWith(searchWord)) {
                System.out.println(entry.getKey() + " means: " + entry.getValue());
                found = true;
            }
        }
        if (!found) {
            System.out.println("There are no words that start with \"" + searchWord + "\" in the dictionary!");
        }
    }

    public static void dictionaryBasic() {
        Dictionary wordList = new Dictionary();
        insertFromCommandLine(wordList);
        showAllWords(wordList);
    }

    public static void dictionaryAdvanced() throws IOException {
        Dictionary wordList = new Dictionary();
        boolean out = false;
        while (!out) {
            int option = displayMenu();
            switch (option) {
                case 0:
                    out = true;
                    break;
                case 1:
                    insertFromCommandLine(wordList);
                    break;
                case 2:
                    dictionaryRemove(wordList);
                    break;
                case 3:
                    dictionaryEdit(wordList);
                    break;
                case 4:
                    showAllWords(wordList);
                    break;
                case 5:
                    dictionaryLookup(wordList);
                    break;
                case 6:
                    dictionarySearcher(wordList);
                    break;
                case 7:

                case 8:
                    insertFromFile(wordList);
                    System.out.println("Dictionary imported successfully!");
                    break;
                case 9:
                    dictionaryExportToFile(wordList);
                    System.out.println("Dictionary exported successfully!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
        System.out.println("Bye bye!");
    }

    public static int displayMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\t\t\tWelcome to My Application");
        System.out.println("0. Exit");
        System.out.println("1. Add new word to dictionary");
        System.out.println("2. Remove a word from dictionary");
        System.out.println("3. Change meaning of a word");
        System.out.println("4. Display all words in dictionary");
        System.out.println("5. Lookup a word");
        System.out.println("6. Search for words");
        System.out.println("7. Game");
        System.out.println("8. Import from file");
        System.out.println("9. Export to file");

        if (sc.hasNextInt()) {
            return sc.nextInt();
        } else {
            sc.next();
            return -1;
        }
    }

    // Hàm main để chạy ứng dụng
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\t\t\tWelcome to My Application");
        System.out.println("0. Exit");
        System.out.println("1. Basic Dictionary");
        System.out.println("2. Advanced Dictionary");
        System.out.println("Select mode:");

        int modeChoice = scanner.nextInt();
        switch (modeChoice) {
            case 0:
                System.out.println("Exiting the application.");
                break;
            case 1:
                dictionaryBasic();
                break;
            case 2:
                dictionaryAdvanced();
                break;
            default:
                System.out.println("Invalid mode choice. Please select a valid option.");
        }
        System.out.println("Bye bye!");
    }
}
