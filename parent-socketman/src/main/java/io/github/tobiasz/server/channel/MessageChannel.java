package io.github.tobiasz.server.channel;

import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;

public class MessageChannel extends Channel<String> {

    @Override
    public void onMessage(String message, Session session) {
        System.out.println(message);
    }
}
