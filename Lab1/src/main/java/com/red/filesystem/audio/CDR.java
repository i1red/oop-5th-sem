package com.red.filesystem.audio;

import com.red.filesystem.BaseReadOnlyFileStorage;
import com.red.filesystem.ReadWriteFileStorage;
import com.red.filesystem.errors.FileWriteDeniedException;

import java.util.Collection;
import java.util.Optional;

public class CDR extends BaseReadOnlyFileStorage<AudioFile> implements ReadWriteFileStorage<AudioFile> {
    private static final int BYTES_IN_SECOND = 148;
    private int tracksLengthLeft;

    public CDR(int tracksMaxLength) {
        tracksLengthLeft = tracksMaxLength;
    }

    @Override
    public void write(AudioFile file) throws FileWriteDeniedException {
        if (!files.isEmpty() | tracksLengthLeft - file.getTrackLength() < 0) {
            throw new FileWriteDeniedException();
        }

        files.add(file);
        tracksLengthLeft -= file.getTrackLength();
    }

    @Override
    public void write(Collection<AudioFile> audioFiles) throws FileWriteDeniedException {
        if (!files.isEmpty()) {
            throw new FileWriteDeniedException();
        }

        Optional<Integer> lengthOfTracks = audioFiles.stream().map(AudioFile::getTrackLength).reduce(Integer::sum);
        if (lengthOfTracks.isPresent()) {
            if (tracksLengthLeft - lengthOfTracks.get() < 0) {
                throw new FileWriteDeniedException();
            }
            files.addAll(audioFiles);
            tracksLengthLeft -= lengthOfTracks.get();
        }
    }

    @Override
    public int getFreeMemory() {
        return BYTES_IN_SECOND * tracksLengthLeft;
    }
}
