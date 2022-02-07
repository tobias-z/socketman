package io.github.tobiasz.integration.util.testbeans;

import io.github.tobiasz.annotation.ChannelConfig;
import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;

@ChannelConfig(channelName = "team")
public class TeamChannel extends Channel<String> {

    @Override
    public void onMessage(String message, Session session) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
