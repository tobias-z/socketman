package io.github.tobiasz.server;

import static io.github.tobiasz.util.Console.print;

import jakarta.websocket.CloseReason;
import jakarta.websocket.CloseReason.CloseCodes;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/")
public class MessageReceiver {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected, sessionID = " + session.getId());
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        checkForQuit(message, session);
        try {
            session.getBasicRemote().sendText("hello world");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        return null;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session " + session.getId() +
            " closed because " + closeReason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        print("error at client '{}'", session.getId(), throwable);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkForQuit(String message, Session session) {
        if (message.equals("quit")) {
            try {
                session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Bye!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
