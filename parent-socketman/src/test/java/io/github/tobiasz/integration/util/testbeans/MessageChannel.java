package io.github.tobiasz.integration.util.testbeans;

import io.github.tobiasz.annotation.ChannelType;
import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;

@ChannelType(channelType = "message")
public class MessageChannel extends Channel<String> {

    @Override
    public void onMessage(String message, Session session) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
