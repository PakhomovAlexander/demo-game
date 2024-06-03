package com.apakhomov.game.logic;

import com.apakhomov.game.Worker;
import com.apakhomov.game.Player;

import java.util.concurrent.ExecutionException;

/**
 * The logic of the game. Given two players it will run the game and return the result.
 */
public class Game {
    private final Worker worker;
    private final Player p1;
    private final Player p2;

    public Game(Player p1, Player p2, Worker worker) {
        this.worker = worker;
        this.p1 = p1;
        this.p2 = p2;
    }

    public GameResult start() {
        p1.meetOpponent(p2.username());
        p2.meetOpponent(p1.username());

        try {
            var result = tryRound();

            result.winner().notifyWin();
            result.looser().notifyLose();

            return result;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private GameResult tryRound() throws ExecutionException, InterruptedException {
        var result = round();

        if (result == null) {
            p1.notifyDraw();
            p2.notifyDraw();

            return this.tryRound();
        }

        return result;
    }

    private GameResult round() throws ExecutionException, InterruptedException {
        var p1Move = worker.call(p1::move);
        var p2Move = worker.call(p2::move);

        var p1Shape = p1Move.get();
        var p2Shape = p2Move.get();

        if (p1Shape == p2Shape) {
            return null;
        }

        if (p1Shape == Shape.ROCK && p2Shape == Shape.SCISSORS) {
            return new GameResult(p1, p2);
        }

        if (p1Shape == Shape.PAPER && p2Shape == Shape.ROCK) {
            return new GameResult(p1, p2);
        }

        if (p1Shape == Shape.SCISSORS && p2Shape == Shape.PAPER) {
            return new GameResult(p1, p2);
        }

        return new GameResult(p2, p1);
    }
}
