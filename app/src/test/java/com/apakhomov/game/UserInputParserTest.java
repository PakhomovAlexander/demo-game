package com.apakhomov.game;

import com.apakhomov.game.io.UserInputParser;
import com.apakhomov.game.logic.Shape;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.apakhomov.game.logic.Shape.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;

class UserInputParserTest {
    public static Stream<Arguments> inputs() {
        return Stream.of(
                of("1", ROCK),
                of("2", PAPER),
                of("3", SCISSORS),

                of("rock", ROCK),
                of("paper", PAPER),
                of("scissors", SCISSORS),

                of("Rock", ROCK),
                of("Paper", PAPER),
                of("Scissors", SCISSORS),

                of("ROCK", ROCK),
                of("PAPER", PAPER),
                of("SCISSORS", SCISSORS),

                of("   1   ", ROCK),
                of("   2   ", PAPER),
                of("   3   ", SCISSORS),

                of("", null),
                of(" ", null),
                of("4", null),
                of("rockk", null),
                of("rock paper", null),
                of("rock, paper", null),
                of("null", null),
                of("@", null),
                of("@#sd9", null)
        );
    }

    @ParameterizedTest
    @MethodSource("inputs")
    void testParse(String input, Shape expected) {
        assertEquals(expected, new UserInputParser().tryParse(input));
    }
}
