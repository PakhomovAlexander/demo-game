package com.apakhomov.game.events;

import com.apakhomov.game.exec.Worker;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventBus {
    private final Map<EventType, List<Consumer<Event>>> listeners;
    private final Worker worker;

    public EventBus(Worker worker) {
        this.worker = worker;
        listeners = new ConcurrentHashMap<>();
    }

    public void fire(Event event) {
        worker.submit(() -> {
            System.out.println("Event: " + event.type() + " " + event.player().username());
            var listenersForEvent = listeners.get(event.type());
            listenersForEvent.forEach(listener -> listener.accept(event));
        });
    }

    public void register(EventType type, Consumer<Event> listener) {
        listeners.merge(type, new CopyOnWriteArrayList<>(List.of(listener)), (oldValue, newValue) -> {
            oldValue.addAll(newValue);
            return oldValue;
        });
    }
}
