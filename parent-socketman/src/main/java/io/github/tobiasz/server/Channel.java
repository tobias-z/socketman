package io.github.tobiasz.server;

import static io.github.tobiasz.util.Console.print;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;

abstract public class Channel<T> {

    abstract public void onMessage(T message, Session session);

    public void onOpen(Session session) {
        print("Connected, sessionID = {}", session.getId());
    }

    public void onClose(Session session, CloseReason closeReason) {
        print("Session {} closed because {}", session.getId(), closeReason);
    }

    public void onError(Session session, Throwable throwable) {
        print("error at client '{}'", session.getId(), throwable);
    }
}
