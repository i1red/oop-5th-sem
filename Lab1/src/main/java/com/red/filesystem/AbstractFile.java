package com.red.filesystem;

public abstract class AbstractFile implements File {
    private final String name;

    public AbstractFile(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("%s { name = %s}", this.getClass().getSimpleName(), this.getName());
    }
}
