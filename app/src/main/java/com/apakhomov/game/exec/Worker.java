package com.apakhomov.game.exec;

import java.io.Closeable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface Worker extends Closeable {
    void submit(Runnable... tasks);

    <T> Future<T> call(Callable<T> callable);
}
