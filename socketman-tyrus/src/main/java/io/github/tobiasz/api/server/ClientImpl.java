package io.github.tobiasz.api.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.tobiasz.common.client.Client;
import io.github.tobiasz.api.util.ChannelObserver;
import jakarta.websocket.Session;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ClientImpl implements Client<Session> {

    private final Session session;
    private final ChannelObserver channelObserver;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void sendSelf(String message) throws IOException {
        this.sendMessage(message);
    }

    @Override
    public void sendSelf(Object obj) throws IOException {
        this.sendMessage(gson.toJson(obj));
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @Override
    public void sendChannel(String message, String channelName, String... extraChannelNames) throws IOException {
        this.channelObserver.sendMessage(message, this.getChannels(channelName, extraChannelNames));
    }

    @Override
    public void sendChannel(Object obj, String channelName, String... extraChannelNames) throws IOException {
        this.channelObserver.sendMessage(gson.toJson(obj), this.getChannels(channelName, extraChannelNames));
    }

    @Override
    public String getId() {
        return this.session.getId();
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    private String[] getChannels(String channelName, String[] channelNames) {
        String[] channels = Arrays.copyOf(channelNames, channelNames.length + 1);
        channels[channels.length - 1] = channelName;
        return channels;
    }

}
