package com.red.filesystem.audio;

import com.red.filesystem.ReadWriteFileStorage;
import com.red.filesystem.errors.FileWriteDeniedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CDR implements ReadWriteFileStorage<AudioFile> {
    private static final int BYTES_IN_SECOND = 148;
    private final ArrayList<AudioFile> audioFiles;
    private int tracksLengthLeft;

    public CDR(int tracksMaxLength) {
        audioFiles = new ArrayList<AudioFile>();
        tracksLengthLeft = tracksMaxLength;
    }

    @Override
    public void write(AudioFile file) throws FileWriteDeniedException {
        if (!audioFiles.isEmpty() | tracksLengthLeft - file.getTrackLength() < 0) {
            throw new FileWriteDeniedException();
        }

        audioFiles.add(file);
        tracksLengthLeft -= file.getTrackLength();
    }

    @Override
    public void write(Collection<AudioFile> files) throws FileWriteDeniedException {
        if (!audioFiles.isEmpty()) {
            throw new FileWriteDeniedException();
        }

        Optional<Integer> lengthOfTracks = files.stream().map(AudioFile::getTrackLength).reduce(Integer::sum);
        if (lengthOfTracks.isPresent()) {
            if (tracksLengthLeft - lengthOfTracks.get() < 0) {
                throw new FileWriteDeniedException();
            }
            audioFiles.addAll(files);
            tracksLengthLeft -= lengthOfTracks.get();
        }
    }

    @Override
    public ArrayList<AudioFile> readAll() {
        return new ArrayList<AudioFile>(audioFiles);
    }

    @Override
    public void sort(Comparator<AudioFile> comparator) {
        audioFiles.sort(comparator);
    }

    @Override
    public ArrayList<AudioFile> findFiles(Predicate<AudioFile> condition) {
        return audioFiles.stream().filter(condition).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public int getFreeMemory() {
        return BYTES_IN_SECOND * tracksLengthLeft;
    }
}
