package com.apakhomov.game.worker;

import com.apakhomov.game.Worker;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The worker that uses virtual threads to perform async tasks.
 * The usage of virtual threads should allow a single server to handle millions of connections.
 */
public class VirtualThreadsWorker implements Worker {
    private final ExecutorService service;

    public VirtualThreadsWorker() {
        service = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void submit(Runnable... tasks) {
        for (Runnable task : tasks) {
            service.submit(task);
        }
    }

    @Override
    public <T> Future<T> call(Callable<T> callable) {
        return service.submit(callable);
    }

    @Override
    public void close() {
         service.shutdown();
    }
}
