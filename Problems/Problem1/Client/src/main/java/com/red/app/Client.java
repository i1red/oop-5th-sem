package com.red.app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client implements AutoCloseable {
    private final Socket socket;
    private final DataOutputStream writer;
    private final BufferedReader reader;

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.writer = new DataOutputStream(this.socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void sendMessage(String message) throws IOException {
        this.writer.writeBytes(message + "\n");
    }

    public String getMessage() throws IOException {
        return this.reader.readLine();
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
        this.writer.close();
        this.reader.close();
    }
}
