package com.apakhomov.game.events;

import com.apakhomov.game.Player;

/**
 * Event that can be fired by multiple components of the server and can be listened by multiple components
 * through the {@link EventBus}.
 */
public record Event(EventType type, Player player) {
}
