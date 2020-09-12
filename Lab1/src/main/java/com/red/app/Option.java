package com.red.app;

import java.util.function.Function;
import java.util.function.Supplier;

public class Option {
    private String name;
    private Supplier<Void> func;

    public Option(String name, Supplier<Void> func) {
        this.name = name;
        this.func = func;
    }

    public void execute() {
        func.get();
    }

    public String getName() {
        return name;
    }
}
