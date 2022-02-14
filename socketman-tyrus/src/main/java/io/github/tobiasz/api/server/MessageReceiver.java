package io.github.tobiasz.api.server;

import static io.github.tobiasz.api.enums.ChannelType.ALL_CHANNEL_TYPES;
import static io.github.tobiasz.api.mapping.MessageMapper.getMessage;
import static java.util.Objects.checkFromIndexSize;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import io.github.tobiasz.common.client.Client;
import io.github.tobiasz.api.context.SocketmanContext;
import io.github.tobiasz.common.dto.MessageDto;
import io.github.tobiasz.common.exception.ClientResponseException;
import io.github.tobiasz.api.util.ChannelObserver;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

@ServerEndpoint(value = "/")
public class MessageReceiver {

    private final SocketmanContext context = SocketmanContext.getContext();
    private final ChannelObserver channelObserver = ChannelObserver.getInstance();
    private final ClientBoundary clientBoundary = new ClientBoundary(context);

    @OnOpen
    public void onOpen(Session session) {
        Client<Session> client = this.createClient(session);
        clientBoundary.handleException(client, () -> {
            for (Channel channel : this.context.getChannel(ALL_CHANNEL_TYPES.toString())) {
                channelObserver.subscribe(channel, client);
            }
            this.doAction(channel -> channel.getKey().onOpen(channel.getValue()));
        });
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        Client<Session> client = this.createClient(session);
        clientBoundary.handleException(client, () -> {
            MessageDto<?> mappedMessage = getMessage(message);
            List<Channel> channels = this.context.getChannel(mappedMessage.getChannelName());
            List<Channel> allChannels = this.context.getChannel(ALL_CHANNEL_TYPES.toString());
            if (isNull(channels)) {
                throw ClientResponseException.create("No channel found with name: {}", mappedMessage.getChannelName());
            }
            if (nonNull(allChannels)) {
                channels.addAll(allChannels);
            }
            if (!channelObserver.clientInChannel(client, mappedMessage.getChannelName())) {
                channels.forEach(channel -> channel.onOpen(client));
            }
            channels.forEach(channel -> this.channelObserver.subscribe(channel, client));
            channels.forEach(channel -> channel.onMessage(mappedMessage.getMessage(), client));
        });
        return null;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        clientBoundary.handleException(
            this.createClient(session),
            () -> this.doAction(channel -> channel.getKey().onClose(channel.getValue(), closeReason))
        );
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        clientBoundary.handleException(
            this.createClient(session),
            () -> this.doAction(channel -> channel.getKey().onError(channel.getValue(), throwable))
        );
    }

    private void doAction(Consumer<Entry<Channel, Client<Session>>> consumer) {
        this.channelObserver.getObservableList().forEach(consumer);
    }

    private Client<Session> createClient(Session session) {
        return new ClientImpl(session, this.channelObserver);
    }

}
