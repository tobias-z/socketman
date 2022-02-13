package io.github.tobiasz.integration.util.testbeans;

import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;

public class AllChannel extends Channel<Object> {

    @Override
    public void onMessage(Object message, Session session) {
        System.out.println("hit all channel channel");
    }

    @Override
    public void onError(Session session, Throwable throwable) {
        System.out.println("error happened: " + throwable.getMessage());
    }
}
