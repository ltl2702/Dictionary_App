package translator;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class DictionaryManagement {
    public static final Scanner scanner = new Scanner(System.in);
    public static void insertFromCommandLine(Dictionary wordList) {
        System.out.print("How many words do you want to add to the dictionary? ");
        int wordNumber = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i< wordNumber; i++) {
            System.out.print("Enter the new word: ");
            String wordSource = scanner.nextLine();
            System.out.print("Enter the meaning: ");
            String wordTarget = scanner.nextLine();
            Word newWord = new Word(wordSource, wordTarget);
            wordList.insert(newWord);
        }
        System.out.println();
    }

    public static void insertFromFile(Dictionary wordList) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/dictionaries.txt"));
        scanner.useDelimiter("\t");
        while (scanner.hasNext()) {
            Word word = new Word();
            word.setWord_source(scanner.next());
            word.setWord_target((scanner.nextLine()).trim());
            wordList.insert(word);
        }
        scanner.close();
    }

    public static void dictionaryLookup(Dictionary wordList) {
        System.out.print("Enter the word to translate: ");
        String findWord = scanner.nextLine().trim().toLowerCase();

        if (findWord.isEmpty()) {
            System.out.println("Please enter a word to translate.");
        } else {
            if (wordList.getWords().containsKey(findWord)) {
                System.out.println("The meaning is: " + wordList.translate(findWord));
            } else {
                System.out.println("The word '" + findWord + "' does not exist in the dictionary.");
            }
        }
    }


    public static void dictionaryEdit(Dictionary dict) {
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

    public static void dictionaryRemove(Dictionary wordList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to remove from the dictionary: ");
        String removeWord = scanner.nextLine().trim().toLowerCase();

        if (wordList.getWords().containsKey(removeWord)) {
            wordList.removeWord(removeWord);
            System.out.println("The dictionary has been updated!");
        } else {
            System.out.println("Couldn't find the word in the dictionary!");
        }
    }

    public static void dictionaryExportToFile(Dictionary wordList) throws IOException {
        File file = new File("src/main/resources/dictionaryExport.txt");
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        for (Map.Entry<String, String> entry : wordList.getWords().entrySet()) {
            outputStreamWriter.write(entry.getKey());
            outputStreamWriter.write("\t");
            outputStreamWriter.write(entry.getValue());
            outputStreamWriter.write("\n");
        }
        outputStreamWriter.flush();
    }
}
