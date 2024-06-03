package com.apakhomov.game.server;

import com.apakhomov.game.events.Event;
import com.apakhomov.game.events.EventBus;
import com.apakhomov.game.Worker;
import com.apakhomov.game.Player;
import com.apakhomov.game.player.PlayerState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.apakhomov.game.events.EventType.PLAYER_JOINED;
import static com.apakhomov.game.events.EventType.PLAYER_LEFT;

public final class PlayersPool {
    private final Map<String, Player> players;
    private final Worker worker;
    private final EventBus bus;

    public PlayersPool(EventBus bus, Worker worker) {
        this.worker = worker;
        this.players = new ConcurrentHashMap<>();
        this.bus = bus;
    }

    public void add(Player player) {
        worker.submit(() -> {
            if (player.state() == PlayerState.USERNAME_NOT_SET) {
                player.enterUsername();
            }
            players.put(player.username(), player);
            bus.fire(new Event(PLAYER_JOINED, player));
        });
    }

    public void remove(Player player) {
        worker.submit(() -> {
            players.remove(player.username());
            bus.fire(new Event(PLAYER_LEFT, player));
        });
    }

    public boolean contains(String username) {
        return players.containsKey(username);
    }
}
