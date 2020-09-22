package com.red.filesystem.audio;

import com.red.filesystem.File;

public interface AudioFile extends File {
    Genre getGenre();
    int getTrackLength();
    int getBitrate();
}
