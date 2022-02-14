package io.github.tobiasz.common.client;

import java.io.IOException;

public interface Client<T> {

    /**
     * The message gets sent directly to the connected client
     * @param message The message you want to send
     * @throws IOException If sending the message went wrong
     */
    void sendSelf(String message) throws IOException;

    /**
     * A JSON string of your object gets sent directly to the connected client
     * @param obj The object you want to send
     * @throws IOException If sending the message went wrong
     */
    void sendSelf(Object obj) throws IOException;

    /**
     * The message gets sent directly to all clients connected to the channels provided
     * @param message The message you want to send
     * @throws IOException If sending the message went wrong
     */
    void sendChannel(String message, String channelName, String... extraChannelNames) throws IOException;

    /**
     * A JSON string of your object gets sent directly to all clients connected to the channels provided
     * @param obj The object you want to send
     * @throws IOException If sending the message went wrong
     */
    void sendChannel(Object obj, String channelName, String... extraChannelNames) throws IOException;

    /**
     * @return The unique id of a client session
     */
    String getId();

    /**
     * @return The underlying interface exposed for free use
     */
    T getSession();
}
