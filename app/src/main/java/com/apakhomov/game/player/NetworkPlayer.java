package com.apakhomov.game.player;

import com.apakhomov.game.PlayerInterface;
import com.apakhomov.game.Shape;
import com.apakhomov.game.UserInputParser;
import com.apakhomov.game.io.NotificationMsg;
import com.apakhomov.game.io.PromptMsg;
import com.apakhomov.game.io.PromptType;

import static com.apakhomov.game.io.NotificationType.*;
import static com.apakhomov.game.io.PromptType.*;
import static com.apakhomov.game.player.PlayerState.*;

public class NetworkPlayer implements Player {
    private String myUsername;
    private String opponentUsername;
    private Shape myLastMove;
    private Shape opponentLastMove;
    private PlayerState myState;

    private final UserInputParser parser;

    private final PlayerInterface pi;

    public NetworkPlayer(PlayerInterface pi) {
        this.pi = pi;
        this.myState = USERNAME_NOT_SET;
        this.parser = new UserInputParser();
    }

    @Override
    public synchronized Shape move() {
        var shapeStr = pi.prompt(new PromptMsg(MOVE));
        myLastMove = parser.tryParse(shapeStr.content());

        while (myLastMove == null) {
            pi.notify(new NotificationMsg("Invalid input. Please try again.", INFO));
            shapeStr = pi.prompt(new PromptMsg(MOVE));
            myLastMove = parser.tryParse(shapeStr.content());
        }

        myState = switch (myState) {
            case MOVE_REQUIRED -> opponentLastMove == null ? WAITING_FOR_MOVE : IDLE;
            case USERNAME_NOT_SET, WAITING_FOR_MOVE, WAITING_FOR_START, IDLE, GAME_FINISHED -> null;
        };

        if (myState == WAITING_FOR_MOVE) {
            pi.notify(new NotificationMsg("Waiting for " + opponentUsername + " move...", INFO));
        }

        if (myState == IDLE) {
            pi.notify(new NotificationMsg(opponentUsername + " move: " + opponentLastMove, INFO));
        }

        if (myState == null) {
            System.out.println("Unexpected state: " + myState);
            throw new IllegalStateException();
        }

        return myLastMove;
    }

    @Override
    public void enterUsername() {
        myUsername = pi.prompt(new PromptMsg(ENTER_USERNAME)).content();
        myState = WAITING_FOR_START;
        pi.notify(new NotificationMsg("Waiting for opponent...", INFO));
    }

    @Override
    public String username() {
        return myUsername;
    }

    @Override
    public PlayerState state() {
        return myState;
    }

    @Override
    public void notifyWin() {
        myState = GAME_FINISHED;
        pi.notify(new NotificationMsg(YOU_WIN));
    }

    @Override
    public void notifyLose() {
        myState = GAME_FINISHED;
        pi.notify(new NotificationMsg(YOU_LOSE));
    }

    @Override
    public void notifyOpponentMove(Shape shape) {
        pi.notify(new NotificationMsg("Opponent move: " + shape, INFO));
        myState = switch (myState) {
            case WAITING_FOR_MOVE -> IDLE;
            case MOVE_REQUIRED -> {
                opponentLastMove = shape;
                yield MOVE_REQUIRED;
            }
            case USERNAME_NOT_SET, WAITING_FOR_START, IDLE, GAME_FINISHED -> null;
        };

        if (myState == null) {
            System.out.println("Unexpected state: " + myState);
            throw new IllegalStateException();
        }
    }

    @Override
    public void notifyDraw() {
        pi.notify(new NotificationMsg("Draw!", INFO));
        myState = MOVE_REQUIRED;
        myLastMove = null;
        opponentLastMove = null;
    }

    @Override
    public void meetOpponent(String opponentUsername) {
        this.opponentUsername = opponentUsername;

        pi.notify(new NotificationMsg("You are playing against " + opponentUsername, INFO));
        myState = MOVE_REQUIRED;
    }
}
