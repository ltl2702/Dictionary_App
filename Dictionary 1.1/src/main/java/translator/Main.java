package translator;

import java.io.IOException;
import java.util.Scanner;

import static translator.DictionaryCommandline.dictionaryAdvanced;
import static translator.DictionaryCommandline.dictionaryBasic;

public class Main {
    public static void main(String[] args) throws IOException {
        Dictionary wordList = new Dictionary();
        boolean out = false;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\t\t\tWelcome to My Application");
        System.out.println("0. Exit");
        System.out.println("1. Basic Dictionary");
        System.out.println("2. Advanced Dictionary");
        System.out.println("Select mode:");

        int modeChoice = scanner.nextInt();
        scanner.nextLine();

        switch (modeChoice) {
            case 0:
                System.out.println("Exiting the application.");
                break;
            case 1:
                dictionaryBasic(wordList);
                break;
            case 2:
                dictionaryAdvanced(wordList);
                break;
            default:
                System.out.println("Invalid mode choice. Please select a valid option.");
        }
        System.out.println("Bye bye!");
    }
}
