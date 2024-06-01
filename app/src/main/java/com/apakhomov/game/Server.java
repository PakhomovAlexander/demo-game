/*
 * This source file was generated by the Gradle 'init' task
 */
package com.apakhomov.game;

import com.apakhomov.game.io.InOutPlayerInterface;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    public Server() {
    }

    public void start() {
        try(ServerSocket socket = new ServerSocket(8080)) {
            System.out.println("Server started");

            Socket socket1 = socket.accept();
            try (var in = reader(socket1); var out = writer(socket1)) {
                Player p1 = new NetworkPlayer(new InOutPlayerInterface(in, out));
                System.out.println("Connected to client: " + p1.username());

                Socket socket2 = socket.accept();
                try(var in2 = reader(socket2); var out2 = writer(socket2)) {
                    Player p2 = new NetworkPlayer(new InOutPlayerInterface(in2, out2));
                    System.out.println("Connected to client: " + p2.username());

                    Player winner = new Game(List.of(p1, p2)).start();
                    System.out.println("Game over, winner is: " + winner.username());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    PrintWriter writer(Socket socket) throws IOException {
        return new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    BufferedReader reader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
