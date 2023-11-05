package main.java;

import main.java.Dictionary;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import static main.java.DictionaryManagement.*;

public class DictionaryCommandline {

    public static void showAllWords(main.java.Dictionary wordList) {
        System.out.printf("%-10s| %-20s\t| %s\n", "No", "English", "Vietnamese");
        int x = 0;
        for (Map.Entry<String, String> entry : wordList.getWords().entrySet()) {
            x++;
            System.out.printf("%-10d| %-20s\t| %s\n", x, entry.getKey(), entry.getValue());
        }
    }

    public static void dictionarySearcher(main.java.Dictionary wordList) {
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

    public static void dictionaryBasic(main.java.Dictionary wordList) {
        System.out.println("\n\t\t\tWelcome to Basic Dictionary");
        boolean out = false;
        while (!out) {
            System.out.println("0. Exit");
            System.out.println("1. Add new word to dictionary");
            System.out.println("2. Display all words in dictionary");
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            switch (option) {
            case 0:
                System.out.println("Exiting the application");
                return;
            case 1:
                insertFromCommandLine(wordList);
                break;
            case 2:
                showAllWords(wordList);
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
            }
        }
    }

    public static void dictionaryAdvanced(Dictionary wordList) throws IOException {
        System.out.println("\n\t\t\tWelcome to Advanced Dictionary");
        boolean out = false;
        while (!out) {
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
            System.out.println("Choose an option: ");
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
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
                    // Implement game functionality here if needed
                    break;
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
                    break;
            }
        }
    }
}
