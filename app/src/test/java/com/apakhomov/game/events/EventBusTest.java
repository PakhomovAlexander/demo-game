package com.apakhomov.game.events;

import com.apakhomov.game.worker.VirtualThreadsWorker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.apakhomov.game.Await.assertEventually;
import static org.junit.jupiter.api.Assertions.*;

class EventBusTest {
    EventBus bus;

    AtomicInteger joinedEventsCount;
    AtomicInteger winEventsCount;
    AtomicInteger loseEventsCount;

    @BeforeEach
    void setUp() {
        bus = new EventBus(new VirtualThreadsWorker());

        joinedEventsCount = new AtomicInteger();
        winEventsCount = new AtomicInteger();
        loseEventsCount = new AtomicInteger();
    }

    @Test
    void asyncLoad() {
        // Given
        int eventsCount = 10_000;
        // And listeners that count the number of events
        bus.register(EventType.PLAYER_JOINED, event -> joinedEventsCount.incrementAndGet());
        bus.register(EventType.PLAYER_WIN, event -> winEventsCount.incrementAndGet());
        bus.register(EventType.PLAYER_LOSE, event -> loseEventsCount.incrementAndGet());

        // When generate events in multiple threads
        try(var executor = Executors.newFixedThreadPool(3)) {
            executor.submit(() -> {
                for (int i = 0; i < eventsCount; i++) {
                    bus.fire(new Event(EventType.PLAYER_JOINED, null));
                }
            });
            executor.submit(() -> {
                for (int i = 0; i < eventsCount; i++) {
                    bus.fire(new Event(EventType.PLAYER_WIN, null));
                }
            });
            executor.submit(() -> {
                for (int i = 0; i < eventsCount; i++) {
                    bus.fire(new Event(EventType.PLAYER_LOSE, null));
                }
            });
        }

        // Then all events are processed correctly
        assertEventually(eventsCount, joinedEventsCount);
        assertEventually(eventsCount, winEventsCount);
        assertEventually(eventsCount, loseEventsCount);
    }
}
