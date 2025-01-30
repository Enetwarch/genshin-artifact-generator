package com.github.enetwarch.genshinartifactgenerator.util;
import java.text.DecimalFormat;
import java.util.Random;

public class Output {

    public static final Random random = new Random();

    public static void printSpace() {
        System.out.printf("%n");
    }

    public static void terminateProgram() {
        System.out.printf("Genshin Artifact Generator%n");
        System.out.printf("Code by: Enetwarch%n");
        Input.scanner.close();
        System.exit(0);
    }

}