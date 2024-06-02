package com.apakhomov.game.player;

import com.apakhomov.game.PlayerInterface;
import com.apakhomov.game.Shape;
import com.apakhomov.game.io.Msg;
import com.apakhomov.game.io.NotificationMsg;
import com.apakhomov.game.io.PromptMsg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class NetworkPlayerStateTransitionsTest {

    NetworkPlayer player;

    @BeforeEach
    void setUp() {
        var moves = new ArrayDeque<Msg>();
        moves.add(new Msg("ROCK"));
        moves.add(new Msg("PAPER"));

        player = new NetworkPlayer(new TestPlayerInterface("TestUsername", moves));
    }

    @Test
    void test() {
        assertEquals(PlayerState.USERNAME_NOT_SET, player.state());

        player.enterUsername();
        assertEquals(PlayerState.WAITING_FOR_START, player.state());

        player.meetOpponent("OpponentUsername");
        assertEquals(PlayerState.MOVE_REQUIRED, player.state());

        player.move();
        assertEquals(PlayerState.WAITING_FOR_MOVE, player.state());

        player.notifyOpponentMove(Shape.PAPER);
        assertEquals(PlayerState.IDLE, player.state());

        player.notifyDraw();
        assertEquals(PlayerState.MOVE_REQUIRED, player.state());

        player.notifyOpponentMove(Shape.ROCK);
        assertEquals(PlayerState.MOVE_REQUIRED, player.state());

        player.move();
        assertEquals(PlayerState.IDLE, player.state());

        player.notifyLose();
        assertEquals(PlayerState.GAME_FINISHED, player.state());

        // Game is finished, no more moves allowed.
        assertThrows(Exception.class, () -> player.move());
    }


    class TestPlayerInterface implements PlayerInterface {
        private final String username;
        private final Queue<Msg> msgQueue;

        TestPlayerInterface(String username, Queue<Msg> msgQueue) {
            this.username = username;
            this.msgQueue = msgQueue;
        }

        @Override
        public void notify(NotificationMsg notification) {

        }

        @Override
        public Msg prompt(PromptMsg prompt) {
            return switch (prompt.type()) {
                case ENTER_USERNAME -> new Msg(username);
                case MOVE -> msgQueue.poll();
            };
        }
    }
}
