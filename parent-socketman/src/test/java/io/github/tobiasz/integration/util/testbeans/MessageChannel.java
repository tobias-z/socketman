package io.github.tobiasz.integration.util.testbeans;

import io.github.tobiasz.Client;
import io.github.tobiasz.annotation.ChannelConfig;
import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;
import java.io.IOException;

@ChannelConfig(channelName = "message")
public class MessageChannel extends Channel<String> {

    @Override
    public void onMessage(String message, Client<Session> client) {
        try {
            client.sendSelf("result from message channel to session: " + client.getId());
            client.sendChannel("yo", "team", "message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(Client<Session> client) {
        try {
            client.sendChannel("User joined message channel" + client.getId(), "message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
