package com.apakhomov.game.server;

public record ServerConfiguration(int port, int maxParallelConnections) {
    public ServerConfiguration() {
        this(8080, 8);
    }

    public ServerConfiguration {
        if (port < 2000 || port > 65535) {
            throw new IllegalArgumentException("Port must be in range 2000-65535");
        }
        if (maxParallelConnections < 1) {
            throw new IllegalArgumentException("Max parallel connections must be at least 1");
        }
    }
}
