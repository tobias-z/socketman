package io.github.tobiasz.util;

import io.github.tobiasz.server.Channel;
import jakarta.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChannelObserver<T extends Channel<K>, K> {

    private final List<T> observableList = new ArrayList<>();

    public void subscribe(T observable) {
        observableList.add(observable);
    }

    public void unsubscribe(T observable) {
        observableList.remove(observable);
    }

    public void sendMessage(K message, Session session) {
        for (T observable : observableList) {
            observable.onMessage(message, session);
        }
    }
}
