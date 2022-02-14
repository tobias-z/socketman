package io.github.tobiasz.api;

import io.github.tobiasz.api.context.SocketmanContext;
import io.github.tobiasz.api.server.SocketmanServer;

public class SocketmanApplication {

    public static void main(String[] args) {
        SocketmanApplication.run();
    }

    public static void run() {
        run(5050);
    }

    public static void run(int port) {
        run(port, "/");
    }

    public static void run(int port, String path) {
        // Create context
        SocketmanContext.getContext();
        SocketmanServer.run(port, path);
    }

}
