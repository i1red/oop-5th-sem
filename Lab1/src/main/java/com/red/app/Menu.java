package com.red.app;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private final ArrayList<Option> options = new ArrayList<Option>();

    public void addOption(Option option) {
        options.add(option);
    }

    public void start() {
        printMenu();
        execOption();
    }

    private int read() {
        var scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private void execOption() {
        int optionNo = read();
        if (optionNo < -2 | optionNo >= options.size()) {
            System.out.println("Invalid option");
            execOption();
            return;
        }

        if (optionNo == -2) {
            return;
        }

        if (optionNo == -1) {
            System.out.println("opt -1");
        }
        else {
            options.get(optionNo).execute();
        }

        execOption();
    }

    private void printMenu() {
        System.out.printf("%d - %s%n", -2, "exit");
        System.out.printf("%d - %s%n", -1, "clear");
        for (int i = 0; i < options.size(); ++i) {
            System.out.printf("%d - %s%n", i, options.get(i).getName());
        }
    }
}
