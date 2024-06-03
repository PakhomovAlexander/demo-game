package com.apakhomov.game.io.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UsernameInputValidatorTest {
    UsernameInputValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UsernameInputValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   "})
    void emptyUsername(String input) {
        assertNotNull(validator.validateOrNull(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a", "1", "ab", "abc", "abcdefgh", "abc1defghi", "123abcdefghij", "abcdefghijk1234",
            "abcdefghijkl", "abcdefghijklm", "abcdefghijklmn", "abcdefghijklmno",
            "O_9", "a_pa", "_", "o-o", "...,.."
    })
    void validUsername(String input) {
        assertNull(validator.validateOrNull(input));
    }

    @Test
    void maxLen() {
        var tooLongUsername = "a".repeat(100);
        assertNotNull(validator.validateOrNull(tooLongUsername));
    }
}
