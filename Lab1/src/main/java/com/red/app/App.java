package com.red.app;

import com.red.demo.RandomGenerator;
import com.red.filesystem.ReadWriteFileStorage;
import com.red.filesystem.audio.AudioFile;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class App {
    public static void main(String[] args) {
        var disc = new AtomicReference<ReadWriteFileStorage<AudioFile>>(RandomGenerator.genDisc());

        var menu = new Menu();

        menu.addOption(new Option("Regen disc", () -> disc.set(RandomGenerator.genDisc())));

        menu.addOption(new Option("Print songs", () -> {
            disc.get().readAll().forEach(song ->
                    System.out.printf("Name: %s; genre: %s; length: %d%n", song.getName(), song.getGenre(), song.getTrackLength()));
        } ));

        menu.addOption(new Option("Find songs", () -> {
            Integer minLength = Console.read("Min track length");
            Integer maxLength = Console.read("Max track length");

            disc.get().findFiles(song -> {
                if (minLength != null & song.getTrackLength() < minLength) {
                    return false;
                }
                return !(maxLength != null & song.getTrackLength() > maxLength);
            }).forEach(song ->
                    System.out.printf("Name: %s; genre: %s; length: %d%n", song.getName(), song.getGenre(), song.getTrackLength()));
        }));

        menu.addOption(new Option("Sort by genre", () -> {
            disc.get().sort(Comparator.comparing(AudioFile::getGenre));
        }));
        menu.start();
    }
}
