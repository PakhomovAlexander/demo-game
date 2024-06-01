package com.apakhomov.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final List<Player> players;
    private final Map<Player, Shape> shapes;

    public Game(List<Player> players) {
        if (players.size() != 2) {
            throw new IllegalArgumentException("Only 2 players are supported for now");
        }

        this.players = players;
        this.shapes = new HashMap<>();
    }

    public Player start() {
        System.out.println("Game started, waiting for players to make a move");
        for (Player player : players) {
           shapes.put(player, player.move());
        }

        Player winner = getWinner();
        if (winner == null) {
            System.out.println("It's a draw!");
        } else {
            System.out.println(winner + " wins!");
        }

        return winner;
    }

    private Player getWinner() {
        if (shapes.get(players.get(0)) == shapes.get(players.get(1))) {
            return null;
        }

        if (shapes.get(players.get(0)) == Shape.ROCK && shapes.get(players.get(1)) == Shape.SCISSORS) {
            return players.get(0);
        }

        if (shapes.get(players.get(0)) == Shape.PAPER && shapes.get(players.get(1)) == Shape.ROCK) {
            return players.get(0);
        }

        if (shapes.get(players.get(0)) == Shape.SCISSORS && shapes.get(players.get(1)) == Shape.PAPER) {
            return players.get(0);
        }

        return players.get(1);
    }
}
