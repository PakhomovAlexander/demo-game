package com.apakhomov.game;

import com.apakhomov.game.server.Server;
import com.apakhomov.game.server.ServerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.apakhomov.game.Await.assertEventually;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test demonstrates that the single server can handle
 * a lot of clients at the same time.
 */
public class ItMultiplayerGameTest {
    static final int PORT = 8083;
    static final int CLIENTS_COUNT = 10_000;

    AtomicInteger connectedClientsCount;

    Server server;

    @BeforeEach
    public void setUp() {
        connectedClientsCount = new AtomicInteger();
        server = new Server(new ServerConfiguration(PORT));
    }

    @Test
    @Disabled("It turned out to be a really good test, needs to be investigated.")
    public void test() {
        var serverThread = new Thread(() -> server.start());
        serverThread.start();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < CLIENTS_COUNT; i++) {
                executor.submit(() -> {
                    try (var sock = clientSocket()) {
                        connectedClientsCount.incrementAndGet();
                    } catch (IOException e) {
                    }
                });
            }
        }

        assertEventually(CLIENTS_COUNT, connectedClientsCount);
    }

    Socket clientSocket() throws IOException {
        return new Socket("localhost", PORT);
    }
}
