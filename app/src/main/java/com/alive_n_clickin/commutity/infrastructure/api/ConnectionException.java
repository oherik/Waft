package com.alive_n_clickin.commutity.infrastructure.api;

import java.io.IOException;

/**
 * Thrown to indicate that there has occured an error with an HTTP connection.
 */
public class ConnectionException extends IOException {
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
