package com.apakhomov.game;

import com.apakhomov.game.player.Player;

public record GameResult(Player winner, Player looser) {
}
