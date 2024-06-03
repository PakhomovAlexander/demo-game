package com.apakhomov.game.io.validation;

import com.apakhomov.game.InputValidator;
import com.apakhomov.game.io.PromptMsg;
import com.apakhomov.game.io.PromptType;

import java.util.Set;

public class UsernameInputValidator implements InputValidator {
    private static final int MAX_LENGTH = 50;

    private static final Set<Character> ALLOWED_CHARS = Set.of(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '_',
            '-', '.', ','
    );

    @Override
    public ValidationIssue validateOrNull(String input) {
        if (input.isBlank()) {
            return new ValidationIssue(ValidationIssueType.EMPTY);
        }

        if (input.length() > MAX_LENGTH) {
            return new ValidationIssue(ValidationIssueType.MAX_LENGTH_EXCEEDED);
        }

        for (var c : input.toCharArray()) {
            if (!ALLOWED_CHARS.contains(c)) {
                return new ValidationIssue(ValidationIssueType.INVALID_CHARACTER);
            }
        }
        return null;
    }

    @Override
    public boolean isApplicable(PromptMsg prompt) {
        return prompt.type() == PromptType.ENTER_USERNAME;
    }
}
