package com.red.filesystem.audio;

import com.red.demo.RandomGenerator;
import com.red.filesystem.errors.FileWriteDeniedException;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CDRTest {
    @Test
    public void testWrite_passSingleFile_succeeds() throws FileWriteDeniedException {
        var disc = new CDR(300);
        AudioFile song = RandomGenerator.randomSong(Genre.UNDEFINED, 120);

        disc.write(song);

        assertEquals(song, disc.readAll().get(0));
    }

    @Test
    public void testWrite_passMultipleFiles_succeeds() throws FileWriteDeniedException {
        var disc = new CDR(4800);
        ArrayList<AudioFile> songs = RandomGenerator.genSongs();

        disc.write(songs);

        assertArrayEquals(songs.toArray(), disc.readAll().toArray());
    }

    @Test(expected = FileWriteDeniedException.class)
    public void testWrite_passSingleFile_throwsException() throws FileWriteDeniedException {
        var disc = new CDR(300);
        AudioFile song = RandomGenerator.randomSong(Genre.UNDEFINED, 301);
        disc.write(song);
    }

    @Test
    public void testSort_succeeds() throws FileWriteDeniedException {
        var disc = new CDR(4800);
        Genre[] genresExpected = { Genre.UNDEFINED, Genre.ROCK, Genre.RAP, Genre.POP, Genre.JAZZ, Genre.CLASSIC };

        var inputGenres = Arrays.asList(genresExpected.clone());
        Collections.shuffle(inputGenres);

        ArrayList<AudioFile> songs = inputGenres.stream().map(genre ->
                RandomGenerator.randomSong(genre, 100)).collect(Collectors.toCollection(ArrayList::new));

        disc.write(songs);
        disc.sort(Comparator.comparing(AudioFile::getGenre));

        assertArrayEquals(genresExpected, disc.readAll().stream().map(AudioFile::getGenre).toArray());
    }

    @Test
    public void testFindFiles_succeeds() throws FileWriteDeniedException {
        var disc = new CDR(4800);

        var random = new Random();

        int min = 100;
        int max = 200;

        int[] lengthsUnexpected = {random.nextInt(min), random.nextInt(min), max + random.nextInt(100)};
        int[] lengthsExpected = {
                min + 1 + random.nextInt(max - min - 2),
                min + 1 + random.nextInt(max - min - 2),
                min + 1 + random.nextInt(max - min - 2),
        };

        ArrayList<AudioFile> songsUnexpected = Arrays.stream(lengthsUnexpected).mapToObj(length ->
                RandomGenerator.randomSong(Genre.UNDEFINED, length)).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<AudioFile> songsExpected = Arrays.stream(lengthsExpected).mapToObj(length ->
                RandomGenerator.randomSong(Genre.UNDEFINED, length)).collect(Collectors.toCollection(ArrayList::new));

        disc.write(songsUnexpected);
        disc.write(songsExpected);

        assertArrayEquals(songsExpected.toArray(), disc.findFiles(song -> song.getTrackLength() > min && song.getTrackLength() < max).toArray());
    }
}