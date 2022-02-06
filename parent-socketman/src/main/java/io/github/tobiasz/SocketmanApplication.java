package io.github.tobiasz;

import io.github.tobiasz.bean.SocketmanBeans;
import io.github.tobiasz.server.SocketmanServer;

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
        SocketmanBeans.getInstance().initBeans();
        SocketmanServer.run(port, path);
    }

}
