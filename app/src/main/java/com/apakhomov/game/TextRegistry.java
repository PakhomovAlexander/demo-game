package com.apakhomov.game;

import com.apakhomov.game.io.NotificationMsg;
import com.apakhomov.game.io.PromptMsg;
import com.apakhomov.game.io.validation.ValidationIssue;

/**
 * Text registry, which provides text for different prompts and notifications.
 */
public interface TextRegistry {
    String initialText(PromptMsg prompt);

    String invalidInputText(PromptMsg prompt, ValidationIssue issue);

    String notificationText(NotificationMsg notification);
}
