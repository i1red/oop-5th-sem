package com.red.filesystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractReadOnlyFileStorage<T extends File> implements ReadOnlyFileStorage<T> {
    protected final List<T> files = new ArrayList<T>();

    @Override
    public ArrayList<T> readAll() {
        return new ArrayList<T>(files);
    }

    @Override
    public void sort(Comparator<T> comparator) {
        files.sort(comparator);
    }

    @Override
    public ArrayList<T> findFiles(Predicate<T> condition) {
        return files.stream().filter(condition).collect(Collectors.toCollection(ArrayList::new));
    }
}
