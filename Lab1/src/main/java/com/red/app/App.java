package com.red.app;

import com.red.demo.RandomGenerator;
import com.red.filesystem.ReadWriteFileStorage;
import com.red.filesystem.audio.AudioFile;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

public class App {
    private static final String TABLE_COLUMNS = String.format("%-50s%-20s%-20s", "NAME", "GENRE", "LENGTH");
    private static final String TABLE_ROW = "%-50s%-20s%-20d";

    private static String songInfo(AudioFile song) {
        return String.format(TABLE_ROW, song.getName(), song.getGenre(), song.getTrackLength());
    }

    public static void main(String[] args) {
        var disc = new AtomicReference<ReadWriteFileStorage<AudioFile>>(RandomGenerator.genDisc());

        var menu = new Menu();

        menu.addOption(new Option("Regen disc", () -> disc.set(RandomGenerator.genDisc())));

        menu.addOption(new Option("Print songs", () -> {
            Console.printInfo(TABLE_COLUMNS);
            disc.get().readAll().forEach(song -> Console.printInfo(songInfo(song)));
        }));

        menu.addOption(new Option("Find songs", () -> {
            Integer minLength = Console.read("Min track length");
            Integer maxLength = Console.read("Max track length");

            Console.printInfo(TABLE_COLUMNS);
            disc.get().findFiles(song -> {
                if (minLength != null & song.getTrackLength() < minLength) {
                    return false;
                }
                return !(maxLength != null & song.getTrackLength() > maxLength);
            }).forEach(song -> Console.printInfo(songInfo(song)));
        }));

        menu.addOption(new Option("Sort by genre", () -> {
            disc.get().sort(Comparator.comparing(AudioFile::getGenre));
        }));

        menu.start();
    }
}
