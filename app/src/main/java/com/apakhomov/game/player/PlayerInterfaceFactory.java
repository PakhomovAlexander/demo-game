package com.apakhomov.game.player;

import com.apakhomov.game.InputValidator;
import com.apakhomov.game.Player;
import com.apakhomov.game.PlayerInterface;
import com.apakhomov.game.TextRegistry;
import com.apakhomov.game.io.DefaultTextRegistry;
import com.apakhomov.game.io.InOutPlayerInterface;
import com.apakhomov.game.io.validation.UniqueUserValidator;
import com.apakhomov.game.io.validation.UsernameInputValidator;
import com.apakhomov.game.server.PlayersPool;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

public class PlayerInterfaceFactory {
    public PlayerInterface create(BufferedReader in, PrintWriter out, PlayersPool pool) {
       return new InOutPlayerInterface(
               in, out,
               new DefaultTextRegistry(),
               List.of(new UniqueUserValidator(pool), new UsernameInputValidator())
       );
    }
}
