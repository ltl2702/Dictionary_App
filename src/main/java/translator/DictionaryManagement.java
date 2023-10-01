package translator;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

/**
 * The {@code DictionaryManagement} class provides methods to manage a dictionary.
 */
public class DictionaryManagement {

    /**
     * Inserts words from the command line into the dictionary.
     *
     * @param dict The Dictionary object to insert words into.
     */
    public static void insertFromCommandLine(Dictionary dict) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of words to add to the dictionary: ");
        int numOfWords = Integer.parseInt(scanner.nextLine());
        while (numOfWords > 0) {
            System.out.print("Enter the word to add to the dictionary: ");
            String wordSource = scanner.nextLine();
            System.out.print("Enter the meaning: ");
            String wordTarget = scanner.nextLine();
            Word newWord = new Word(wordSource, wordTarget);
            dict.insert(newWord);
            numOfWords--;
        }
    }

    /**
     * Inserts words from a file into the dictionary.
     *
     * @param dict The Dictionary object to insert words into.
     */
    public static void insertFromFile(Dictionary dict) {
        String path = new File("").getAbsolutePath() + "\\src\\main\\resources\\data\\dictionaries.txt";
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String wordSource = bufferedReader.readLine();
            String wordTarget = bufferedReader.readLine();
            while (wordSource != null && wordTarget != null) {
                Word newWord = new Word(wordSource, wordTarget);
                dict.insert(newWord);
                wordSource = bufferedReader.readLine();
                wordTarget = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("The specified file not found: " + fnfe);
        } catch (IOException ioe) {
            System.out.println("I/O Exception: " + ioe);
        }
    }

    /**
     * Looks up and prints the meaning of a word from the dictionary.
     *
     * @param dict The Dictionary object to look up words in.
     */
    public static void dictionaryLookup(Dictionary dict) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to translate: ");
        String findWord = scanner.nextLine().trim().toLowerCase();
        if (dict.getWords().containsKey(findWord)) {
            System.out.println("The meaning is: " + dict.translate(findWord));
        } else {
            System.out.println("Couldn't find the word in the dictionary!!");
        }
    }

    /**
     * Edits the meaning of a word in the dictionary.
     *
     * @param dict The Dictionary object to edit.
     */
    public static void dictionaryEdit(Dictionary dict) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word in the dictionary to edit: ");
        String editWord = scanner.nextLine().trim().toLowerCase();
        if (dict.getWords().containsKey(editWord)) {
            System.out.print("Enter the new meaning: ");
            String newMeaning = scanner.nextLine();
            dict.getWords().replace(editWord, newMeaning);
            System.out.println("The dictionary has been updated!");
        } else {
            System.out.println("Couldn't find the word in the dictionary!");
        }
    }

    /**
     * Removes a word from the dictionary.
     *
     * @param dict The Dictionary object to remove the word from.
     */
    public static void dictionaryRemove(Dictionary dict) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to remove from the dictionary: ");
        String removeWord = scanner.nextLine().trim().toLowerCase();
        if (dict.getWords().containsKey(removeWord)) {
            dict.removeWord(removeWord);
            System.out.println("The dictionary has been updated!");
        } else {
            System.out.println("Couldn't find the word in the dictionary!");
        }
    }

    /**
     * Exports the dictionary to a file.
     *
     * @param dict The Dictionary object to export.
     */
    public static void dictionaryExportToFile(Dictionary dict) {
        String path =
                new File("").getAbsolutePath() + "/src/main/resources/data/dictionaryModified.txt";
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, String> e : dict.getWords().entrySet()) {
                bw.write(e.getKey());
                bw.newLine();
                bw.write(e.getValue());
                bw.newLine();
            }
            bw.close();

        } catch (IOException ioe) {
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
    }
}
