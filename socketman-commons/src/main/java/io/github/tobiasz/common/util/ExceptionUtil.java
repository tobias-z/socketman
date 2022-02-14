package io.github.tobiasz.common.util;

import static io.github.tobiasz.common.util.Console.getFormattedMessage;

import io.github.tobiasz.common.exception.ClientResponseException;
import io.github.tobiasz.common.function.ExceptionHandler;
import io.github.tobiasz.common.function.VoidFunc;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class ExceptionUtil {

    public static String getMessageFromArgs(String message, Object[] args) {
        Queue<Object> queue = new LinkedList<>(List.of(args));
        return getFormattedMessage(message, queue);
    }

    public static void exceptionCatcher(VoidFunc fn, ExceptionHandler exceptionHandler, Consumer<RuntimeException> unknownErrorConsumer) {
        try {
            fn.apply();
        } catch (RuntimeException e) {
            if (e instanceof ClientResponseException) {
                exceptionHandler.handleException((ClientResponseException) e);
                return;
            }
            unknownErrorConsumer.accept(e);
        }
    }

}
