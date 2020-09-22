package com.red.app;

public class Student {
    private final String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s { name = %s }", this.getClass().getName(), this.name);
    }
}
