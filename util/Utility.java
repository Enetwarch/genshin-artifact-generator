package util;
import java.util.Scanner;

public class Utility {

    public static final Scanner scanner = new Scanner(System.in);
    private static final String INPUT_ERROR = "INPUT ERROR";


    ////// INPUT METHODS


    public static boolean getUserInputBoolean(String message) {
        String userInput;
        while (true) {
            System.out.printf("%s? (y/n): ", message);
            userInput = scanner.nextLine().toLowerCase();
            switch (userInput) {
                case "y" -> { return true; }
                case "n" -> { return false; }
                default -> System.out.printf("%s. Accepts y or n.\n", INPUT_ERROR);
            }
        }
    }

}