package com.apakhomov.game.player;

import com.apakhomov.game.Shape;

public interface Player {
    Shape move();

    void enterUsername();

    String username();

    PlayerState state();

    void notifyWin();

    void notifyLose();

    void notifyOpponentMove(Shape shape);

    void notifyDraw();

    void meetOpponent(String opponentUsername);
}
