package com.apakhomov.game.exec;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    public void close() throws IOException {
         service.shutdown();
    }
}
