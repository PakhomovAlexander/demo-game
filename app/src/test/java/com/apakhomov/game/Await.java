package com.apakhomov.game;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Await {
    public static void assertEventually(int expected, AtomicInteger actual) {
        int attempts = 0;
        while (actual.get() != expected && attempts < 100) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            attempts++;
        }
        assertEquals(expected, actual.get());
    }
}
