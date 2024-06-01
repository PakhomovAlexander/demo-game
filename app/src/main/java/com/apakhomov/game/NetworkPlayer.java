package com.apakhomov.game;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NetworkPlayer implements Player {
    private final Socket socket;
    private final String username;
    private final Writer out;
    private final BufferedReader in;

    public NetworkPlayer(Socket socket) {
        this.socket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.write("Enter your username: ");
            out.flush();
            this.username = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Shape move() {
        try {
            out.write("Your move: ");
            out.flush();
            return Shape.valueOf(in.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String username() {
        return username;
    }
}
