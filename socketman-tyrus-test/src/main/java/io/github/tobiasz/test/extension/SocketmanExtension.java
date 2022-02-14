package io.github.tobiasz.test.extension;

import io.github.tobiasz.api.context.SocketmanContext;
import io.github.tobiasz.api.server.SocketmanServer;
import org.glassfish.tyrus.server.Server;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class SocketmanExtension implements BeforeAllCallback, BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private Server server;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        SocketmanContext.getContext();
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        this.server = SocketmanServer.createServer(5050, "/");
        this.server.start();
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        this.server.stop();
    }
}
