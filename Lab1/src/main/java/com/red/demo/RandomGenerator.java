package com.red.demo;

import com.red.filesystem.ReadWriteFileStorage;
import com.red.filesystem.audio.AudioFile;
import com.red.filesystem.audio.BaseAudioFile;
import com.red.filesystem.audio.CDR;
import com.red.filesystem.audio.Genre;
import com.red.filesystem.errors.FileWriteDeniedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RandomGenerator {
    static Random random = new Random();
    static AudioFile[] audioFiles = {
            new BaseAudioFile("Skillet - Rise", Genre.ROCK, 6_012_122, 263),
            new BaseAudioFile("Korn - World Up!", Genre.ROCK, 4_221_819, 173),
            new BaseAudioFile("Podcast1", Genre.UNDEFINED, 10_521_819, 600),
            new BaseAudioFile("Dua Lipa - New Rules", Genre.POP, 5_797_966, 224),
            new BaseAudioFile("6ix9ine - Kooda", Genre.RAP, 4_012_122, 144),
            new BaseAudioFile("Y2K & bbno$ - lalala", Genre.RAP, 4_518_119, 161),
            new BaseAudioFile("Podcast2", Genre.UNDEFINED, 10_221_819, 581),
            new BaseAudioFile("The Weeknd - Call Out My Name", Genre.POP, 5_897_966, 238),
            new BaseAudioFile("Lana Del Rey - Summertime Sadness", Genre.POP, 8_012_122, 283),
            new BaseAudioFile("Lana Del Rey - Born To Die", Genre.POP, 8_421_819, 286),
            new BaseAudioFile("Podcast3", Genre.UNDEFINED, 15_621_819, 821),
            new BaseAudioFile("24kGoldn ft. Iann Dior - Mood", Genre.RAP, 4_012_122, 142),
            new BaseAudioFile("DaBaby - VIBEZ", Genre.RAP, 4_218_001, 145),
            new BaseAudioFile("Mustard ft. Roddy Rich - Ballin'", Genre.RAP, 5_012_122, 180),
            new BaseAudioFile("Podcast4", Genre.UNDEFINED, 10_218_219, 602),
            new BaseAudioFile("Ed Sheeran - Shape Of You", Genre.POP, 7_000_966, 263),
            new BaseAudioFile("Skillet - Undefeated", Genre.ROCK, 6_012_122, 216),
            new BaseAudioFile("Skillet - Savior", Genre.ROCK, 7_218_019, 273),
            new BaseAudioFile("Podcast5", Genre.UNDEFINED, 10_218_119, 607),
            new BaseAudioFile("ASAP Rocky - Babushka Boi", Genre.RAP, 5_797_966, 187),
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

    private static ArrayList<AudioFile> genSongs() {
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
}
