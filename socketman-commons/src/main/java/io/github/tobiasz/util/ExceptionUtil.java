package io.github.tobiasz.util;

import static io.github.tobiasz.util.Console.getFormattedMessage;
import static io.github.tobiasz.util.Console.print;

import io.github.tobiasz.exceptions.ClientResponseException;
import io.github.tobiasz.functions.ExceptionHandler;
import io.github.tobiasz.functions.VoidFunc;
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
