package io.github.tobiasz.util;

import static io.github.tobiasz.enums.ChannelType.ALL_CHANNEL_TYPES;
import static io.github.tobiasz.enums.ChannelType.getChannelName;
import static java.util.Objects.hash;
import static java.util.Objects.isNull;

import io.github.tobiasz.Client;
import io.github.tobiasz.annotation.ChannelConfig;
import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChannelObserver {

    private static ChannelObserver instance;

    private ChannelObserver() {
    }

    public static ChannelObserver getInstance() {
        if (isNull(instance)) {
            instance = new ChannelObserver();
        }
        return instance;
    }

    private final List<Entry<Channel, Client<Session>>> observableList = new ArrayList<>();

    public void subscribe(Channel channel, Client<Session> client) {
        Entry<Channel, Client<Session>> observable = this.findObservable(client.getId(), channel);
        if (isNull(observable)) {
            observableList.add(new SimpleEntry<>(channel, client));
        }
    }

    private Entry<Channel, Client<Session>> findObservable(String id, Channel newChannel) {
        for (Entry<Channel, Client<Session>> channelClientEntry : this.getObservableList()) {
            String oldChannelName = getChannelName(channelClientEntry.getKey().getClass());
            String newChannelName = getChannelName(newChannel.getClass());
            boolean isSameChannel = oldChannelName.equals(newChannelName);
            if (channelClientEntry.getValue().getId().equals(id) && isSameChannel) {
                return channelClientEntry;
            }
        }
        return null;
    }

    public void unsubscribe(Channel channel, Client<Session> client) {
        observableList.remove(this.findObservable(client.getId(), channel));
    }

    public void sendMessage(Object message, String senderId, String... channelNames) throws IOException {
        for (Entry<Channel, Client<Session>> observable : observableList) {
            Channel channel = observable.getKey();
            Client<Session> client = observable.getValue();
            boolean isSender = client.getId().equals(senderId);
            ChannelConfig config = channel.getClass().getAnnotation(ChannelConfig.class);
            boolean isCorrectChannel = isCorrectChannel(config, channelNames);
            if (isCorrectChannel && !isSender) {
                sendMessageToClient(message, client);
            }
            boolean isAllChannel = isNull(config) || config.channelName().equals(ALL_CHANNEL_TYPES.toString());
            if (isAllChannel) {
                channel.onMessage(message, client);
            }
        }
    }

    private boolean isCorrectChannel(ChannelConfig config, String[] channelNames) {
        if (isNull(config)) {
            return false;
        }
        for (String channelName : channelNames) {
            if (config.channelName().equals(channelName)) {
                return true;
            }
        }
        return false;
    }

    private void sendMessageToClient(Object message, Client<Session> client) throws IOException {
        if (message instanceof String) {
            client.sendSelf((String) message);
        } else {
            client.sendSelf(message);
        }
    }

    public boolean clientInChannel(Client<Session> client, String channelName) {
        for (Entry<Channel, Client<Session>> channelClientEntry : this.getObservableList()) {
            boolean isSameClient = channelClientEntry.getValue().getId().equals(client.getId());
            if (!isSameClient) {
                continue;
            }
            Channel channel = channelClientEntry.getKey();
            if (getChannelName(channel.getClass()).equals(channelName)) {
                return true;
            }
        }
        return false;
    }
}
