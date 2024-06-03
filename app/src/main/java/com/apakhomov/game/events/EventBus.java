package com.apakhomov.game.events;

import com.apakhomov.game.Worker;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Event bus that allows to fire events and register listeners for them. The main mechanism of non-blocking
 * communication between components of the server.
 */
public final class EventBus {
    private final Worker worker;
    private final Map<EventType, List<Consumer<Event>>> listeners;

    public EventBus(Worker worker) {
        this.worker = worker;
        this.listeners = new ConcurrentHashMap<>();
    }

    /**
     * Fires the event asynchronously. All listeners for the event type will be notified.
     */
    public void fire(Event event) {
        worker.submit(() -> {
            log(event);
            var listenersForEvent = listeners.get(event.type());
            listenersForEvent.forEach(listener -> listener.accept(event));
        });
    }

    private static void log(Event event) {
        System.out.println(
                "Event: " + event.type() + " "
                        + (event.player() == null ? "null" : event.player().username())
        );
    }

    /**
     * Registers a listener for the event type. Important: listener must not throw any exceptions.
     */
    public void register(EventType type, Consumer<Event> listener) {
        listeners.merge(type, new CopyOnWriteArrayList<>(List.of(listener)), (oldValue, newValue) -> {
            oldValue.addAll(newValue);
            return oldValue;
        });
    }
}
