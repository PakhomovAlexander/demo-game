package com.apakhomov.game.io.validation;

import com.apakhomov.game.io.PromptMsg;
import com.apakhomov.game.io.PromptType;

public class UsernameInputValidator implements InputValidator {
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{3,20}$";

    @Override
    public ValidationIssue validateOrNull(String input) {
        if (!input.matches(USERNAME_PATTERN)) {
            return new ValidationIssue(ValidationIssueType.INVALID_CHARACTER_USERNAME);
        }
        return null;
    }

    @Override
    public boolean isApplicable(PromptMsg prompt) {
        return prompt.type() == PromptType.ENTER_USERNAME;
    }
}
