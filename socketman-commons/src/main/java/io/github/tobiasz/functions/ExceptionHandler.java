package io.github.tobiasz.functions;

import io.github.tobiasz.exceptions.ClientResponseException;

public interface ExceptionHandler {

    void handleException(ClientResponseException e);

}
