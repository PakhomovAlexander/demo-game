package com.apakhomov.game;

import com.apakhomov.game.events.Event;
import com.apakhomov.game.events.EventBus;
import com.apakhomov.game.exec.Worker;
import com.apakhomov.game.player.Player;
import com.apakhomov.game.server.PlayersPool;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.apakhomov.game.events.EventType.*;

public class GameController {
    private final EventBus bus;
    private Player waitingPlayer;
    private final Worker worker;


    public GameController(EventBus bus, Worker worker) {
        this.bus = bus;
        this.waitingPlayer = null;
        this.worker = worker;
        bus.register(PLAYER_JOINED, event -> onPlayerJoined(event.player()));
    }

    public synchronized void onPlayerJoined(Player player) {
        if (waitingPlayer == null) {
            waitingPlayer = player;
        } else {
            var opponent = waitingPlayer;
            waitingPlayer = null;
            worker.submit(() -> {
                var result = new Game(opponent, player, worker).start();

                bus.fire(new Event(PLAYER_WIN, result.winner()));
                bus.fire(new Event(PLAYER_LOSE, result.looser()));
            });
        }
    }
}
