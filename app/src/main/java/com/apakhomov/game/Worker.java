package com.apakhomov.game;

import java.io.Closeable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * The worker that should be used to perform async tasks in the game.
 */
public interface Worker extends Closeable {
    void submit(Runnable... tasks);

    <T> Future<T> call(Callable<T> callable);
}
