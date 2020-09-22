package com.red.app;

import com.google.gson.Gson;

import java.io.IOException;

public class App {
    private static final int PORT = 8000;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        var gson = new Gson();
        try (
                var client = new Client(HOST, PORT);
        )
        {
            client.sendMessage(gson.toJson(new Student("Ivan")));
            System.out.println(client.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
