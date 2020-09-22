package com.red.app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class Server implements AutoCloseable {
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void handleConnection(Function<String, String> messageHandler) throws IOException {
        try (
                Socket connectionSocket = this.serverSocket.accept();
                var inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                var outToClient = new DataOutputStream(connectionSocket.getOutputStream())
        ) {
            String message = inFromClient.readLine();
            if (message != null) {
                outToClient.writeBytes(messageHandler.apply(message) + "\n");
            }
        }
    }

    @Override
    public void close() throws IOException {
        this.serverSocket.close();
    }
}
