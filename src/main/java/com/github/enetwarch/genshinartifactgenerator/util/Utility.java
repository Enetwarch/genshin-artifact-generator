package com.github.enetwarch.genshinartifactgenerator.util;
import java.util.InputMismatchException;
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

    // Input helper method
    private static void inputMessage(String message) {
        System.out.printf("%s: ", message);
    }

    // getUserInputInt Helper method
    private static void inputErrorInt(int min, int max) {
        System.out.printf("%s. Accepts integers %d to %d.\n", INPUT_ERROR, min, max);
    }

    public static int getUserInputInt(String message, int min, int max) {
        int userInput; 
        while (true) {
            try {
                inputMessage(message);
                userInput = scanner.nextInt(); 
            } catch (InputMismatchException e) {
                inputErrorInt(min, max); 
                continue;
            } finally {
                scanner.nextLine(); // Absorbs \n
            }
            if (userInput >= min && userInput <= max) {
                return userInput; 
            } else {
                inputErrorInt(min, max);
            }
        }
    }

    public static void inputBuffer() {
        // Gives users time to read printed output.
        System.out.print("Press enter to proceed. ");
        scanner.nextLine();
        System.out.print("\n");
    }

}