package io.github.tobiasz.server;

import static io.github.tobiasz.util.Console.print;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.glassfish.tyrus.server.Server;

@RequiredArgsConstructor
public class SocketmanServer {

    public static void run(int port, String path) {
        Server server = new Server("localhost", port, path, new HashMap<>(), MessageReceiver.class);
        try {
            server.start();
            print("--- Socketman server started on localhost:{}", port);
            print("--- Press any key to stop the server");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            bufferRead.readLine();
        } catch (Exception e) {
            print("Socketman server has been stopped", e);
        } finally {
            server.stop();
        }
    }
}
