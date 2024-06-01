package com.apakhomov.game;

import com.apakhomov.game.io.Msg;
import com.apakhomov.game.io.NotificationMsg;
import com.apakhomov.game.io.PromptMsg;

public interface PlayerInterface {
    void notify(NotificationMsg notification);

    Msg prompt(PromptMsg prompt);
}
