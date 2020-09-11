package com.red.filesystem;

public class BaseFile implements File{
    private final String name;
    private final int size;

    public BaseFile(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        return String.format("%s { name = %s, size = %d}", this.getClass().getSimpleName(), this.getName(), this.getSize());
    }
}
