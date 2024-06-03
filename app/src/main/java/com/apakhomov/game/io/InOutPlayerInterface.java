package com.apakhomov.game.io;

import com.apakhomov.game.InputValidator;
import com.apakhomov.game.PlayerInterface;
import com.apakhomov.game.TextRegistry;
import com.apakhomov.game.io.validation.ValidationIssue;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class InOutPlayerInterface implements PlayerInterface {
    private final BufferedReader in;
    private final PrintWriter out;
    private final TextRegistry textRegistry;
    private final List<InputValidator> validators;

    public InOutPlayerInterface(BufferedReader in, PrintWriter out,
                                TextRegistry textRegistry, List<InputValidator> validators) {
        this.in = in;
        this.out = out;
        this.textRegistry = textRegistry;
        this.validators = validators;
    }

    @Override
    public void notify(NotificationMsg notification) {
        out.println();
        out.println(textRegistry.notificationText(notification));
        out.println();

        out.flush();
    }

    @Override
    public Msg prompt(PromptMsg prompt) {
        out.println(textRegistry.initialText(prompt));
        out.flush();

        boolean valid = false;
        String userInput = null;
        while (!valid) {
            try {
                userInput = in.readLine();
                if (userInput == null) {
                    return null;
                }

                List<ValidationIssue> issues = validate(prompt, userInput);
                if (issues == null) {
                    valid = true;
                } else {
                    for (ValidationIssue issue : issues) {
                        out.println(textRegistry.invalidInputText(prompt, issue));
                        out.flush();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return new Msg(userInput);
    }

    private List<ValidationIssue> validate(PromptMsg prompt, String input) {
        List<ValidationIssue> issues = new ArrayList<>();
        for (InputValidator validator : validators) {
            if (validator.isApplicable(prompt)) {
                ValidationIssue issue = validator.validateOrNull(input);
                if (issue == null) {
                    continue;
                } else {
                    issues.add(issue);
                }
            }
        }
        return issues.isEmpty() ? null : issues;
    }
}
