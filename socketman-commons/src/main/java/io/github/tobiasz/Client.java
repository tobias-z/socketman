package io.github.tobiasz;

import java.io.IOException;

public interface Client<T> {

    void sendSelf(String message) throws IOException;

    void sendSelf(Object obj) throws IOException;

    void sendChannel(String message, String channelName, String... extraChannelNames) throws IOException;

    void sendChannel(Object obj, String channelName, String... extraChannelNames) throws IOException;

    String getId();

    T getSession();
}
