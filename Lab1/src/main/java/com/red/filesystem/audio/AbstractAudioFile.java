package com.red.filesystem.audio;

import com.red.filesystem.AbstractFile;

public abstract class AbstractAudioFile extends AbstractFile implements AudioFile {
    private final Genre genre;
    private final int trackLength;
    private final int bitrate;

    public AbstractAudioFile(String name, Genre genre, int trackLength, int bitrate) {
        super(name);
        this.genre = genre;
        this.trackLength = trackLength;
        this.bitrate = bitrate;
    }

    @Override
    public Genre getGenre() {
        return this.genre;
    }

    @Override
    public int getTrackLength() {
        return this.trackLength;
    }

    @Override
    public int getBitrate() { return this.bitrate; }
}
