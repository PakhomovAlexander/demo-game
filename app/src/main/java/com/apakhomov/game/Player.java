package com.apakhomov.game;

import com.apakhomov.game.logic.Shape;
import com.apakhomov.game.player.PlayerState;

/**
 * The main player interface. The only way for the game to interact with the player.
 */
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
