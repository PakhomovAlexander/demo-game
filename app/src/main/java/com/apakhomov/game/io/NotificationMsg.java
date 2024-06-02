package com.apakhomov.game.io;

public record NotificationMsg(String content, NotificationType type) {
    public NotificationMsg(NotificationType type) {
        this("", type);
    }
}
