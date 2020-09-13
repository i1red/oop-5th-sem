package com.red.app;

import java.util.function.Function;
import java.util.function.Supplier;

public class Option {
    private final String name;
    private final Runnable func;

    public Option(String name, Runnable func) {
        this.name = name;
        this.func = func;
    }

    public void execute() {
        func.run();
    }

    public String getName() {
        return name;
    }
}
