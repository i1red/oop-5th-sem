package com.red.app;

import java.util.Scanner;

public class Console {
    private final static Scanner scanner = new Scanner(System.in);

    public static void printInfo(String info) {
        System.out.println(info);
    }

    public static void printQuestion(String question) {
        System.out.printf("%s: %n", question);
    }

    public static Integer read(String question) {
        printQuestion(question);

        if (scanner.hasNextInt()) {
            Integer value = scanner.nextInt();
            scanner.nextLine();
            return value;
        }

        return null;
    }

    public static void close() {
        scanner.close();
    }
}
