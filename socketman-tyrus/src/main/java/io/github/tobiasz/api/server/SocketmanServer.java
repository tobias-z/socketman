package io.github.tobiasz.api.server;

import static io.github.tobiasz.common.util.Console.print;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.glassfish.tyrus.server.Server;

@RequiredArgsConstructor
public class SocketmanServer {

    public static void run(int port, String path) {
        Server server = createServer(port, path);
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

    public static Server createServer(int port, String path) {
       return new Server("localhost", port, path, new HashMap<>(), MessageReceiver.class);
    }
}
