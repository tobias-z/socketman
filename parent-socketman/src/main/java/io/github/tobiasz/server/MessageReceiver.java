package io.github.tobiasz.server;

import static io.github.tobiasz.enums.ChannelType.ALL_CHANNEL_TYPES;
import static io.github.tobiasz.mapping.MessageMapper.getMessage;

import io.github.tobiasz.context.SocketmanContext;
import io.github.tobiasz.dto.MessageDto;
import io.github.tobiasz.util.ChannelObserver;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.function.Consumer;

@ServerEndpoint(value = "/")
public class MessageReceiver {

    private final SocketmanContext context = SocketmanContext.getContext();
    private final ChannelObserver<Channel, ?> channelObserver = new ChannelObserver<>();

    @OnOpen
    public void onOpen(Session session) {
        this.context.getChannel(ALL_CHANNEL_TYPES.toString()).forEach(channelObserver::subscribe);
        this.doAction(channel -> channel.onOpen(session));
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        MessageDto<?> mappedMessage = getMessage(message);
        List<Channel> channels = this.context.getChannel(mappedMessage.getChannelName());
        channels.forEach(this.channelObserver::subscribe);
        System.out.println(mappedMessage);
        channels.forEach(channel -> channel.onMessage(mappedMessage.getMessage(), session));
        return null;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        this.doAction(channel -> channel.onClose(session, closeReason));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        this.doAction(channel -> channel.onError(session, throwable));
    }

    private void doAction(Consumer<Channel> consumer) {
        this.channelObserver.getObservableList().forEach(consumer);
    }

}
