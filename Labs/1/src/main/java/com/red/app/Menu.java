package com.red.app;

import java.util.ArrayList;

public class Menu {
    private final ArrayList<Option> options = new ArrayList<Option>();

    public void addOption(Option option) {
        options.add(option);
    }

    public void start() {
        printOptions();
        loop();
    }

    private void loop() {
        Integer optionNo = null;

        while (true) {
            optionNo = Console.read("Select option");

            if (optionNo != null) {
                if (optionNo < -2 | optionNo >= options.size()) {
                    Console.printInfo("Invalid option");
                    continue;
                }

                if (optionNo == -2) {
                    Console.close();
                    break;
                }

                if (optionNo == -1) {
                    printOptions();
                }
                else {
                    options.get(optionNo).execute();
                }
            }
        }
    }

    private void printOptions() {
        Console.printInfo(String.format("%d - %s", -2, "exit"));
        Console.printInfo(String.format("%d - %s", -1, "print options"));
        for (int i = 0; i < options.size(); ++i) {
            Console.printInfo(String.format("%d - %s", i, options.get(i).getName()));
        }
    }
}
