package com.red.filesystem.audio;

import com.red.filesystem.File;

public interface AudioFile extends File {
    public Genre getGenre();
    public int getTrackLength();
}
