package com.red.app;

import com.google.gson.Gson;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class App {
    private static final int PORT = 8000;
    private static final int MIN_UPTIME = 5;

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
        var gson = new Gson();

        try (var server = new Server(PORT)){
            while (Duration.between(start, LocalDateTime.now()).toSeconds() < MIN_UPTIME) {
                server.handleConnection(
                        (s) -> String.format("Got serialized object: %s. Deserialized object: %s",
                                s, gson.fromJson(s, Student.class))
                    );
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
