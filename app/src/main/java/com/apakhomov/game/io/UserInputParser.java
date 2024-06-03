package com.apakhomov.game.io;

import com.apakhomov.game.logic.Shape;

public class UserInputParser {
    public Shape tryParse(String input) {
        switch (input.toUpperCase().trim()) {
            case "ROCK":
            case "1":
                return Shape.ROCK;
            case "PAPER":
            case "2":
                return Shape.PAPER;
            case "SCISSORS":
            case "3":
                return Shape.SCISSORS;
            default:
                return null;
        }
    }
}
