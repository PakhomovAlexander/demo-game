package com.apakhomov.game;

public class UserInputParser {
    public Shape tryParse(String input) {
        switch (input.toUpperCase()) {
            case "ROCK":
                return Shape.ROCK;
            case "PAPER":
                return Shape.PAPER;
            case "SCISSORS":
                return Shape.SCISSORS;
            default:
                return null;
        }
    }
}
