package com.apakhomov.game;

import com.apakhomov.game.player.Player;
import com.apakhomov.game.player.PlayerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GameTest {
    InMemoryPlayer alice;
    InMemoryPlayer bob;

    Game game;

    public static Stream<Arguments> moves() {
        return Stream.of(
                Arguments.of("Alice", Shape.ROCK, Shape.SCISSORS),
                Arguments.of("Bob", Shape.PAPER, Shape.SCISSORS),
                Arguments.of("Bob", Shape.ROCK, Shape.PAPER),
                Arguments.of("Alice", Shape.PAPER, Shape.ROCK),
                Arguments.of("Alice", Shape.SCISSORS, Shape.PAPER),
                Arguments.of("Bob", Shape.PAPER, Shape.SCISSORS)
        );
    }

    @BeforeEach
    void setUp() {
        alice = new InMemoryPlayer("Alice");
        bob = new InMemoryPlayer("Bob");

        game = new Game(alice, bob);
    }

    @ParameterizedTest
    @MethodSource("moves")
    void test(String expectedWinnerUsername, Shape aliceMove, Shape bobMove) {
        // Given player moves in the order they are passed.
        alice.nextMove(aliceMove);
        bob.nextMove(bobMove);

        // When
        var result = game.start();

        // Then
        assertEquals(expectedWinnerUsername, result.winner().username());
        assertNotEquals(expectedWinnerUsername, result.looser().username());
    }

    private void setupMoves(Shape[] shapes) {
        for (int i = 0; i < shapes.length; i += 2) {
            alice.nextMove(shapes[i]);
            bob.nextMove(shapes[i + 1]);
        }
    }

    static class InMemoryPlayer implements Player {
        private final String username;
        private final Queue<Shape> shapes;

        public InMemoryPlayer(String username) {
            this.username = username;
            this.shapes = new ArrayDeque<>();
        }

        @Override
        public Shape move() {
            return shapes.poll();
        }

        @Override
        public void enterUsername() {

        }

        @Override
        public String username() {
            return username;
        }

        @Override
        public PlayerState state() {
            return PlayerState.WAITING_FOR_MOVE;
        }

        @Override
        public void notifyWin() {

        }

        @Override
        public void notifyLose() {

        }

        @Override
        public void notifyOpponentMove(Shape shape) {

        }

        @Override
        public void notifyDraw() {

        }

        @Override
        public void meetOpponent(String opponentUsername) {

        }

        public void nextMove(Shape shape) {
            shapes.add(shape);
        }
    }
}
