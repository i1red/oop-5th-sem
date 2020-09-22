package com.red.filesystem.audio;

public class MP3 extends AbstractAudioFile {
    private static final int BITRATE_TO_BYTE = 8;

    public MP3(String name, Genre genre, int trackLength) {
        super(name, genre, trackLength, 320);
    }

    public MP3(String name, Genre genre, int trackLength, int bitrate) {
        super(name, genre, trackLength, bitrate);
    }

    @Override
    public int getSize() {
        return this.getBitrate() * this.getTrackLength() / BITRATE_TO_BYTE;
    }
}
