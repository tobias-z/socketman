package io.github.tobiasz.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.tobiasz.Client;
import io.github.tobiasz.util.ChannelObserver;
import jakarta.websocket.Session;
import java.io.IOException;
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
    public void sendChannel(String message, String... channelNames) throws IOException {
        this.channelObserver.sendMessage(message, this.getId(), channelNames);
    }

    @Override
    public void sendChannel(Object obj, String... channelNames) throws IOException {
        this.channelObserver.sendMessage(gson.toJson(obj), this.getId(), channelNames);
    }

    @Override
    public String getId() {
        return this.session.getId();
    }

    @Override
    public Session getSession() {
        return this.session;
    }

}
