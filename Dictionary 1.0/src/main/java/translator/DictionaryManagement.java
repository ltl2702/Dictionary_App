package translator;

import java.util.Scanner;

public class DictionaryManagement {
    public static final Scanner scanner = new Scanner(System.in);

    public static void insertFromCommandLine(Dictionary wordList) {
        System.out.print("How many words do you want to add to the dictionary? ");
        int wordNumber = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < wordNumber; i++) {
            System.out.print("Enter the new word: ");
            String wordSource = scanner.nextLine();
            System.out.print("Enter the meaning: ");
            String wordTarget = scanner.nextLine();
            Word newWord = new Word(wordSource, wordTarget);
            wordList.insert(newWord);
        }
        System.out.println();
    }
}
