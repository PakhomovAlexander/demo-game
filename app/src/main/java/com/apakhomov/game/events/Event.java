package com.apakhomov.game.events;

import com.apakhomov.game.player.Player;

public record Event(EventType type, Player player) {
}
