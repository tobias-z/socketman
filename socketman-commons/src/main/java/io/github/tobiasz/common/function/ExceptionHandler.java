package io.github.tobiasz.common.function;

import io.github.tobiasz.common.exception.ClientResponseException;

public interface ExceptionHandler {

    void handleException(ClientResponseException e);

}
