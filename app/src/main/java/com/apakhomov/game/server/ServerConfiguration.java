package com.apakhomov.game.server;

public record ServerConfiguration(int port) {
    public ServerConfiguration() {
        this(8080);
    }

    public ServerConfiguration {
        if (port < 2000 || port > 65535) {
            throw new IllegalArgumentException("Port must be in range 2000-65535");
        }
    }
}
