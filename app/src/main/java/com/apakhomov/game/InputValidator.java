package com.apakhomov.game;

import com.apakhomov.game.io.PromptMsg;
import com.apakhomov.game.io.validation.ValidationIssue;

/**
 * User input validator, which can validate user input and return a validation issue if the input is invalid.
 */
public interface InputValidator {
    /**
     * Validates the input and returns a validation issue if the input is invalid.
     *
     * @param input the input to validate
     * @return a validation issue if the input is invalid, or null if the input is valid
     */
    ValidationIssue validateOrNull(String input);

    /**
     * Checks if this validator is applicable to the given prompt.
     *
     * @param prompt the prompt to check
     * @return true if this validator is applicable to the given prompt, false otherwise
     */
    boolean isApplicable(PromptMsg prompt);
}
