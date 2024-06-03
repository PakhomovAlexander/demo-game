package com.apakhomov.game.io.validation;

import com.apakhomov.game.InputValidator;
import com.apakhomov.game.io.PromptMsg;
import com.apakhomov.game.io.PromptType;
import com.apakhomov.game.server.PlayersPool;

import java.util.Locale;

public class UniqueUserValidator implements InputValidator {
    private final PlayersPool pool;

    public UniqueUserValidator(PlayersPool pool) {
        this.pool = pool;
    }

    @Override
    public ValidationIssue validateOrNull(String input) {
        if (pool.contains(input.trim().toLowerCase(Locale.ROOT))) {
            return new ValidationIssue(ValidationIssueType.ALREADY_EXISTS);
        }
        return null;
    }

    @Override
    public boolean isApplicable(PromptMsg prompt) {
        return prompt.type() == PromptType.ENTER_USERNAME;
    }
}
