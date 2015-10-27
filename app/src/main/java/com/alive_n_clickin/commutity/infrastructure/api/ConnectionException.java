package com.alive_n_clickin.commutity.infrastructure.api;

/**
 * Thrown to indicate that there has occured an error with an HTTP connection.
 */
public class ConnectionException extends Exception {
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
