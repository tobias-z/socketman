package io.github.tobiasz.api.integration.util.testbeans;

import io.github.tobiasz.common.client.Client;
import io.github.tobiasz.api.annotation.ChannelConfig;
import io.github.tobiasz.api.server.Channel;
import jakarta.websocket.Session;

@ChannelConfig(channelName = "team")
public class TeamChannel extends Channel<String> {

    @Override
    public void onMessage(String message, Client<Session> session) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
