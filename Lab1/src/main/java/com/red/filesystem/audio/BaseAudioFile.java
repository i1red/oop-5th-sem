package com.red.filesystem.audio;

import com.red.filesystem.BaseFile;

public class BaseAudioFile extends BaseFile implements AudioFile {
    private final Genre genre;
    private final int trackLength;

    public BaseAudioFile(String name, Genre genre, int size, int trackLength) {
        super(name, size);
        this.genre = genre;
        this.trackLength = trackLength;
    }

    @Override
    public Genre getGenre() {
        return this.genre;
    }

    @Override
    public int getTrackLength() {
        return this.trackLength;
    }
}
