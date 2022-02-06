package io.github.tobiasz.server;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;

abstract public class Channel<T> {

    abstract public void onMessage(T message, Session session);

    public void onOpen(Session session) {
        System.out.println("Connected, sessionID = " + session.getId());
    }

    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() +
            " closed because " + closeReason);
    }
}
