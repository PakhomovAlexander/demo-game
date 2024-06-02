package com.apakhomov.game;

import com.apakhomov.game.io.PromptMsg;
import com.apakhomov.game.io.PromptType;

// TODO rename
public class NetworkPlayer implements Player {
    private final String username;
    private final PlayerInterface pi;

    public NetworkPlayer(PlayerInterface pi) {
        this.pi = pi;
        this.username = pi.prompt(new PromptMsg(PromptType.ENTER_USERNAME)).content();
    }

    @Override
    public Shape move() {
        var shapeStr = pi.prompt(new PromptMsg(PromptType.MOVE));
        return Shape.valueOf(shapeStr.content());
    }

    @Override
    public String username() {
        return username;
    }
}
