package com.apakhomov.game.io;

import com.apakhomov.game.io.validation.InputValidator;
import com.apakhomov.game.io.validation.ValidationIssue;
import com.apakhomov.game.io.validation.ValidationIssueType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

import static com.apakhomov.game.io.PromptType.*;
import static org.junit.jupiter.api.Assertions.*;

class InOutPlayerInterfaceTest {
    StringWriter outSink;
    PrintWriter out;
    BufferedReader in;
    InOutPlayerInterface io;

    String nextString;

    public static Stream<Arguments> validUsernames() {
        return Stream.of(
               "J", "John", "WowwwwwKa", "123456"
        ).map(Arguments::of);
    }

    public static Stream<Arguments> invalidUsernames() {
        return Stream.of(
                "stop", "(((" // invalid symbols just for test
        ).map(Arguments::of);
    }

    @BeforeEach
    void setUp() {
        in = new BufferedReader(new TestReader());

        outSink = new StringWriter();
        out = new PrintWriter(outSink);

        io = new InOutPlayerInterface(
                in, out,
                new DefaultTextRegistry(),
                List.of(new TestInputValidator("stop"), new TestInputValidator("(("))
        );
    }

    @ParameterizedTest
    @MethodSource("validUsernames")
    void promptUsernameValidCase(String username) {
        // Given user input
        nextString = username;

        // When asking for username
        Msg userAnswer = io.prompt(new PromptMsg(ENTER_USERNAME));

        // Then
        assertNotNull(userAnswer);
        assertEquals(username, userAnswer.content());
    }

    @ParameterizedTest
    @MethodSource("invalidUsernames")
    void promptUsernameInvalidCase(String username) {
        // Given user input
        nextString = username;

        // When asking for username
        Msg userAnswer = io.prompt(new PromptMsg(ENTER_USERNAME));

        // Then
        assertNull(userAnswer);
    }

    class TestInputValidator implements InputValidator {
        private final String stopWord;

        TestInputValidator(String stopWord) {
            this.stopWord = stopWord;
        }

        @Override
        public ValidationIssue validateOrNull(String input) {
            return input.contains(stopWord) ? new ValidationIssue(ValidationIssueType.INVALID_CHARACTER_USERNAME) : null;
        }

        @Override
        public boolean isApplicable(PromptMsg prompt) {
            return true;
        }
    }

    class TestReader extends Reader {

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            if (nextString == null) {
                return -1;
            }
            char[] chars = nextString.toCharArray();
            System.arraycopy(chars, 0, cbuf, off, chars.length);
            if (chars.length < len) {
                nextString = null;
            } else {
                nextString = nextString.substring(len);
            }

            return chars.length;
        }

        @Override
        public void close() throws IOException {

        }
    }
}
