package io.github.tobiasz.integration.util.testbeans;

import io.github.tobiasz.annotation.ChannelConfig;
import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;
import java.io.IOException;

@ChannelConfig(channelName = "message")
public class MessageChannel extends Channel<String> {

    @Override
    public void onMessage(String message, Session session) {
        try {
            session.getBasicRemote().sendText("result from message channel");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
