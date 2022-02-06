package io.github.tobiasz.integration.util.testbeans;

import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;

public class AllChannel extends Channel<Object> {

    @Override
    public void onMessage(Object message, Session session) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
