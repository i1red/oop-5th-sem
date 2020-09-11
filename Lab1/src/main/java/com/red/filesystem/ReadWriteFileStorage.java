package com.red.filesystem;

import com.red.filesystem.errors.FileWriteDeniedException;

import java.util.Collection;

public interface ReadWriteFileStorage<T extends File> extends ReadOnlyFileStorage<T> {
    void write(T file) throws FileWriteDeniedException;
    void write(Collection<T> files) throws FileWriteDeniedException;
    int getFreeMemory();
}
