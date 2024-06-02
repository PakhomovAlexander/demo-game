package com.apakhomov.game.io.validation;

import com.apakhomov.game.io.PromptMsg;

public interface InputValidator {
    ValidationIssue validateOrNull(String input);

    boolean isApplicable(PromptMsg prompt);
}
