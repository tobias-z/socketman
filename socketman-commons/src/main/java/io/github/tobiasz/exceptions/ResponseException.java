package io.github.tobiasz.exceptions;

import static io.github.tobiasz.util.Console.getFormattedMessage;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ResponseException extends RuntimeException {

    public ResponseException(String message) {
        super(message);
    }

    public static ResponseException create(String message, Object... args) {
        Queue<Object> queue = new LinkedList<>(List.of(args));
        message = getFormattedMessage(message, queue);
        return new ResponseException(message);
    }

}
