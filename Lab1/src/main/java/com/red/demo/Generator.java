package com.red.demo;

import com.red.filesystem.ReadWriteFileStorage;
import com.red.filesystem.audio.*;
import com.red.filesystem.errors.FileWriteDeniedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Generator {
    static Random random = new Random();
    static AudioFile[] audioFiles = {
            new MP3("Skillet - Rise", Genre.ROCK, 263),
            new MP3("Korn - World Up!", Genre.ROCK, 173),
            new MP3("Podcast1", Genre.UNDEFINED,  600),
            new MP3("Dua Lipa - New Rules", Genre.POP,  224),
            new MP3("6ix9ine - Kooda", Genre.RAP,  144),
            new MP3("Y2K & bbno$ - lalala", Genre.RAP, 161),
            new MP3("Podcast2", Genre.UNDEFINED, 581),
            new MP3("The Weeknd - Call Out My Name", Genre.POP, 238),
            new MP3("Lana Del Rey - Summertime Sadness", Genre.POP, 283),
            new MP3("Lana Del Rey - Born To Die", Genre.POP, 286),
            new MP3("Podcast3", Genre.UNDEFINED,  821),
            new MP3("24kGoldn ft. Iann Dior - Mood", Genre.RAP,  142),
            new MP3("DaBaby - VIBEZ", Genre.RAP,  145),
            new MP3("Mustard ft. Roddy Rich - Ballin'", Genre.RAP, 180),
            new MP3("Podcast4", Genre.UNDEFINED, 602),
            new MP3("Ed Sheeran - Shape Of You", Genre.POP, 263),
            new MP3("Skillet - Undefeated", Genre.ROCK, 216),
            new MP3("Skillet - Savior", Genre.ROCK, 273),
            new MP3("Podcast5", Genre.UNDEFINED, 607),
            new MP3("ASAP Rocky - Babushka Boi", Genre.RAP, 187),
    };

    public static ReadWriteFileStorage<AudioFile> genDisc() {
        var disc = new CDR(4800);
        try {
            disc.write(genSongs());
            return disc;
        } catch (FileWriteDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<AudioFile> genSongs() {
        var result = new ArrayList<AudioFile>();
        var indices = new HashSet<Integer>();

        while (indices.size() < 10) {
            int index = random.nextInt(audioFiles.length);
            if (!indices.contains(index)) {
                indices.add(index);
                result.add(audioFiles[index]);
            }
        }

        return result;
    }

    public static AudioFile randomSong(Genre genre, int trackLength) {
        return new MP3(String.format("%s_%d", genre, trackLength), genre, trackLength);
    }
}
