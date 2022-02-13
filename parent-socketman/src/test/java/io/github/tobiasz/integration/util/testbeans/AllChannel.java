package io.github.tobiasz.integration.util.testbeans;

import io.github.tobiasz.Client;
import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;

public class AllChannel extends Channel<Object> {

    @Override
    public void onMessage(Object message, Client<Session> session) {
        System.out.println("hit all channel channel");
    }

    @Override
    public void onError(Client<Session> client, Throwable throwable) {
        System.out.println("error happened: " + throwable.getMessage());
    }

    @Override
    public void onOpen(Client<Session> client) {
        System.out.println("opened: " + client.getId());
    }
}
