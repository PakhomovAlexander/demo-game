/*
 * This source file was generated by the Gradle 'init' task
 */
package com.apakhomov.game.server;

import com.apakhomov.game.events.EventBus;
import com.apakhomov.game.player.PlayerInterfaceFactory;
import com.apakhomov.game.worker.VirtualThreadsWorker;

import java.net.ServerSocket;

public class Server {
    private final ServerConfiguration configuration;

    public Server(ServerConfiguration configuration) {
        this.configuration = configuration;
    }

    public void start() {
        try (var socket = new ServerSocket(configuration.port());
             var worker = new VirtualThreadsWorker()) {

            System.out.println("Server started");

            var bus = new EventBus(worker);

            var controller = new GameController(bus, worker);
            controller.start();

            try (var manager = createConnectionManager(bus, worker)) {
                while (true) {
                    manager.handle(socket.accept());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ConnectionsManager createConnectionManager(EventBus bus, VirtualThreadsWorker worker) {
        return new ConnectionsManager(new PlayersPool(bus, worker), new PlayerInterfaceFactory(), bus);
    }


    public static void main(String[] args) {
        new Server(new ServerConfiguration()).start();
    }
}
