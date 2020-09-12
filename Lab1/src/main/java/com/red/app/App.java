package com.red.app;

import com.red.demo.RandomGenerator;
import com.red.filesystem.ReadWriteFileStorage;
import com.red.filesystem.audio.AudioFile;

import java.util.concurrent.atomic.AtomicReference;

public class App {
    public static void main(String[] args) {
        var disc = new AtomicReference<ReadWriteFileStorage<AudioFile>>();

        var menu = new Menu();
        menu.addOption(new Option("Gen disc", () -> {
            disc.set(RandomGenerator.genDisc());
            return null;
        } ));
        menu.addOption(new Option("Print songs", () -> {
            disc.get().readAll().forEach(song ->
                    System.out.printf("Name: %s; genre: %s; length: %d%n", song.getName(), song.getGenre(), song.getTrackLength()));
            return null;
        } ));
        menu.start();
    }
}
