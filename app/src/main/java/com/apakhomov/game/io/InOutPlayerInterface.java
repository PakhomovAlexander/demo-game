package com.apakhomov.game.io;

import com.apakhomov.game.PlayerInterface;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class InOutPlayerInterface implements PlayerInterface {
    private final BufferedReader in;
    private final PrintWriter out;

    public InOutPlayerInterface(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void notify(NotificationMsg notification) {
        out.println(notification.content());
    }

    @Override
    public Msg prompt(PromptMsg prompt) {
        out.println(prompt.prompt());
        out.flush();
        try {
            return new Msg(in.readLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
