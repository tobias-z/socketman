package io.github.tobiasz.server;

import static io.github.tobiasz.enums.ChannelType.ALL_CHANNEL_TYPES;
import static io.github.tobiasz.util.Console.print;
import static io.github.tobiasz.util.ExceptionUtil.exceptionCatcher;
import static java.util.Objects.nonNull;

import io.github.tobiasz.context.SocketmanContext;
import io.github.tobiasz.exceptions.ClientResponseException;
import io.github.tobiasz.functions.VoidFunc;
import jakarta.websocket.Session;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientBoundary {

    private final SocketmanContext context;

    public void handleException(Session session, VoidFunc fn) {
        exceptionCatcher(fn,
            e -> handleClientResponseException(session, e),
            e -> handleUnknownException(session, e)
        );
    }

    private void handleClientResponseException(Session session, ClientResponseException e) {
        try {
            session.getBasicRemote().sendText(e.getMessage());
        } catch (IOException ex) {
            print("Unable to send message", ex);
        }
    }

    private void handleUnknownException(Session session, RuntimeException e) {
        List<Channel> allChannels = context.getChannel(ALL_CHANNEL_TYPES.toString());
        if (nonNull(allChannels)) {
            allChannels.forEach(channel -> channel.onError(session, e));
        }
    }

}
