package com.alive_n_clickin.waft.infrastructure.api.response;

import java.io.IOException;

/**
 * An exception that indicates that something has gone wrong when connecting to an API.
 */
public class ConnectionException extends IOException {
    private static final long serialVersionUID = 5620431774372146394L;

    public ConnectionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
