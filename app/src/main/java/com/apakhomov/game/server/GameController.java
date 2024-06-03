package com.apakhomov.game.server;

import com.apakhomov.game.events.Event;
import com.apakhomov.game.events.EventBus;
import com.apakhomov.game.logic.Game;
import com.apakhomov.game.Worker;
import com.apakhomov.game.Player;

import static com.apakhomov.game.events.EventType.*;

public class GameController {
    private final EventBus bus;
    private Player waitingPlayer;
    private final Worker worker;


    public GameController(EventBus bus, Worker worker) {
        this.bus = bus;
        this.waitingPlayer = null;
        this.worker = worker;
    }

    public void start() {
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
