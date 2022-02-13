package io.github.tobiasz.exceptions;

import static io.github.tobiasz.util.ExceptionUtil.getMessageFromArgs;

public class ClientResponseException extends RuntimeException {

    public ClientResponseException(String message) {
        super(message);
    }

    public static ClientResponseException create(String message, Object... args) {
        return new ClientResponseException(getMessageFromArgs(message, args));
    }
}
