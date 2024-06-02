package com.apakhomov.game.io;

import com.apakhomov.game.io.validation.ValidationIssue;

public interface TextRegistry {
    String initialText(PromptMsg prompt);

    String invalidInputText(PromptMsg prompt, ValidationIssue issue);

    String notificationText(NotificationMsg notification);
}
