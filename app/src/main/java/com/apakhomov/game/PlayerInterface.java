package com.apakhomov.game;

import com.apakhomov.game.io.Msg;
import com.apakhomov.game.io.NotificationMsg;
import com.apakhomov.game.io.PromptMsg;

/**
 * The only way for the game to interact with the player.
 */
public interface PlayerInterface {
    void notify(NotificationMsg notification);

    Msg prompt(PromptMsg prompt);
}
