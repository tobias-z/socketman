package io.github.tobiasz.common.exception;

import static io.github.tobiasz.common.util.ExceptionUtil.getMessageFromArgs;

public class ClientResponseException extends RuntimeException {

    public ClientResponseException(String message) {
        super(message);
    }

    public static ClientResponseException create(String message, Object... args) {
        return new ClientResponseException(getMessageFromArgs(message, args));
    }
}
