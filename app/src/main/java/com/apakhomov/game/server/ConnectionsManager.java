package com.apakhomov.game.server;

import com.apakhomov.game.Game;
import com.apakhomov.game.events.EventBus;
import com.apakhomov.game.events.EventType;
import com.apakhomov.game.io.DefaultTextRegistry;
import com.apakhomov.game.io.InOutPlayerInterface;
import com.apakhomov.game.player.NetworkPlayer;
import com.apakhomov.game.player.Player;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionsManager implements Closeable {
    private final Map<Player, PlayerResources> resources;
    private final PlayersPool playersPool;

    public ConnectionsManager(PlayersPool playersPool, EventBus bus) {
        this.playersPool = playersPool;
        this.resources = new ConcurrentHashMap<>();

        bus.register(EventType.PLAYER_WIN, event -> {
            closeResources(event.player());
        });
        bus.register(EventType.PLAYER_LOSE, event -> {
            closeResources(event.player());
        });
    }

    private void closeResources(Player player) {
        var resources = this.resources.get(player);
        playersPool.remove(player);
        try {
            resources.writer().close();
            resources.reader().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void handle(Socket socket) {
        try {
            var in = reader(socket);
            var out = writer(socket);
            var resources = new PlayerResources(in, out);

            Player p = new NetworkPlayer(new InOutPlayerInterface(in, out, new DefaultTextRegistry(), List.of()));

            playersPool.add(p);
            this.resources.put(p, resources);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    PrintWriter writer(Socket socket) throws IOException {
        return new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    BufferedReader reader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void close() throws IOException {
        resources.forEach((player, resources) -> {
            try {
                resources.reader().close();
                resources.writer().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
