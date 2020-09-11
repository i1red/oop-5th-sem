package com.red.filesystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public interface ReadOnlyFileStorage<T extends File> {
    ArrayList<T> readAll();
    void sort(Comparator<T> comparator);
    ArrayList<T> findFiles(Predicate<T> condition);
}
