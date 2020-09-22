package com.red.filesystem.audio;

import com.red.filesystem.AbstractReadOnlyFileStorage;
import com.red.filesystem.ReadWriteFileStorage;
import com.red.filesystem.errors.FileWriteDeniedException;

import java.util.Collection;
import java.util.Optional;

public class CDR extends AbstractReadOnlyFileStorage<AudioFile> implements ReadWriteFileStorage<AudioFile> {
    private static final int KB_IN_SECOND = 148;
    private int tracksLengthLeft;

    public CDR() {this.tracksLengthLeft = 4800; }

    public CDR(int tracksMaxLength) {
        this.tracksLengthLeft = tracksMaxLength;
    }

    @Override
    public void write(AudioFile file) throws FileWriteDeniedException {
        if (this.tracksLengthLeft - file.getTrackLength() < 0) {
            throw new FileWriteDeniedException();
        }

        this.files.add(file);
        this.tracksLengthLeft -= file.getTrackLength();
    }

    @Override
    public void write(Collection<AudioFile> audioFiles) throws FileWriteDeniedException {
        Optional<Integer> lengthOfTracks = audioFiles.stream().map(AudioFile::getTrackLength).reduce(Integer::sum);
        if (lengthOfTracks.isPresent()) {
            if (this.tracksLengthLeft - lengthOfTracks.get() < 0) {
                throw new FileWriteDeniedException();
            }
            this.files.addAll(audioFiles);
            this.tracksLengthLeft -= lengthOfTracks.get();
        }
    }

    @Override
    public int getFreeMemory() {
        return KB_IN_SECOND * this.tracksLengthLeft;
    }
}
