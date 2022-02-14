package io.github.tobiasz.api.server;

import static io.github.tobiasz.common.util.Console.print;

import io.github.tobiasz.common.client.Client;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;

abstract public class Channel<T> {

    /**
     * Called when a message is sent to the specified channel
     * If no ChannelConfig is given, it is considered a global channel which is called every time a message is sent
     */
    abstract public void onMessage(T message, Client<Session> client);

    /**
     *  When a client first sends a message to a channel the onOpen is called before onMessage
     */
    public void onOpen(Client<Session> client) {
        print("Connected, sessionID = {}", client.getId());
    }

    /**
     *  Whenever a client leaves this method is called
     */
    public void onClose(Client<Session> client, CloseReason closeReason) {
        print("Session {} closed because {}", client.getId(), closeReason);
    }

    /**
     *  onError is called whenever an unknown error happens
     */
    public void onError(Client<Session> client, Throwable throwable) {
        print("error at client '{}'", client.getId(), throwable);
    }
}
